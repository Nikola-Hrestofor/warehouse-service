package com.example.warehouseservice.service

import com.example.warehouseservice.api.ManagementService
import com.example.warehouseservice.api.TechCardService
import com.example.warehouseservice.api.dto.ComponentDto
import com.example.warehouseservice.dto.WarehouseDto
import com.example.warehouseservice.dto.WarehouseRequest
import com.example.warehouseservice.dto.WarehouseStock
import com.example.warehouseservice.dto.enums.UnitType
import com.example.warehouseservice.repository.WarehouseRepository
import com.example.warehouseservice.repository.entity.mapper.WarehouseMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.logging.Logger

@Service
class WarehouseService(
    val warehouseRepository: WarehouseRepository,
    val mapper: WarehouseMapper,
    val jdbcTemplate: JdbcTemplate,
    val techCardService: TechCardService,
    val managementService: ManagementService,
) {

    fun addUnits(warehouseRequest: WarehouseRequest): BigDecimal? {
        val dto = WarehouseDto(
            true,
            warehouseRequest.amount,
            warehouseRequest.cost,
            warehouseRequest.type,
            warehouseRequest.childId,
            warehouseRequest.orderNumber,
            LocalDate.now(),
            null
        )
        logger.info("dto $dto")
        logger.info("mapper.toEntity(dto) ${mapper.toEntity(dto)}")

        warehouseRepository.save(mapper.toEntity(dto))
        return getWarehouse(warehouseRequest.type, warehouseRequest.childId).amount
    }

    fun seizeUnits(warehouseRequest: WarehouseRequest): BigDecimal? {
        val dto = WarehouseDto(
            false,
            warehouseRequest.amount,
            warehouseRequest.cost,
            warehouseRequest.type,
            warehouseRequest.childId,
            warehouseRequest.orderNumber,
            LocalDate.now(),
            null
        )

        warehouseRepository.save(mapper.toEntity(dto))

        return getWarehouse(warehouseRequest.type, warehouseRequest.childId).cost
    }

    fun getWarehouse(unitType: UnitType, childId: Long): WarehouseRequest {
        val entity = warehouseRepository.getByTypeAndChildId(unitType, childId)
        logger.info("entity $entity")
        val dtos = entity.map { warehouseEntity -> mapper.toModel(warehouseEntity) }
        logger.info("dtos $dtos")

        val amount = dtos.stream()
            .map { if (it.action) it.amount else it.amount?.multiply(BigDecimal.valueOf(-1)) }
            .reduce { bigDecimal: BigDecimal?, bigDecimal2: BigDecimal? -> bigDecimal?.add(bigDecimal2) }
            .orElse(BigDecimal.ZERO)
//TODO compute cost
        return WarehouseRequest(
            amount,
            BigDecimal.ZERO,
            unitType,
            childId,
            ""
        )
    }

    fun getWarehouse(pageable: Pageable): Page<WarehouseDto> {
        val warehouse = warehouseRepository.findAll(pageable)
            .map { warehouseEntity -> mapper.toModel(warehouseEntity) }

        warehouse
            .forEach { warehouseDto ->
                when (warehouseDto.type) {
                    UnitType.CARD -> {
                        val card = techCardService.getCardById(warehouseDto.childId)
                        warehouseDto.componentDto =
                            ComponentDto(name = card.name, code = card.code, id = null, unit = null, category = null)
                    }

                    UnitType.COMPONENT -> {
                        warehouseDto.componentDto = techCardService.getComponentById(warehouseDto.childId)
                    }

                    UnitType.PRODUCT -> {
                        val product = managementService.getProductById(warehouseDto.childId)
                        warehouseDto.componentDto = ComponentDto(
                            name = product.name,
                            code = product.uuid.toString(),
                            id = null,
                            unit = null,
                            category = null
                        )
                    }
                }
            }
        return warehouse
    }

    fun getWarehouse(unitType: UnitType?): List<WarehouseStock> {
        //TODO add cost
        val sql =
            "select sum(case when action then amount else amount * (-1) end) as amount, " +
                    "0 as cost, " +
                    "child_id, " +
                    "type " +
                    "from warehouse " +
                    "group by child_id, type "
        val stock = jdbcTemplate.query(sql) { rs, _ ->
            WarehouseStock(
                rs.getBigDecimal("amount"),
                rs.getBigDecimal("cost"),
                UnitType.valueOf(rs.getString("type")),
                rs.getLong("child_id"),
                null
            )
        }

        logger.info("stock $stock")

        val stockFiltered = stock.stream()
            .filter { unitType == null || it.type == unitType }
            .filter {it.amount.compareTo(BigDecimal.ZERO) == 1}
            .sorted { o1, o2 -> o1.rank().compareTo(o2.rank())}
            .toList()

        stockFiltered.forEach { warehouseStock ->
            when (warehouseStock.type) {
                UnitType.CARD -> {
                    val card = techCardService.getCardById(warehouseStock.childId)
                    warehouseStock.componentDto =
                        ComponentDto(name = card.name, code = card.code, id = null, unit = null, category = null)
                }

                UnitType.COMPONENT -> {
                    warehouseStock.componentDto = techCardService.getComponentById(warehouseStock.childId)
                }

                UnitType.PRODUCT -> {
                    val product = managementService.getProductById(warehouseStock.childId)
                    warehouseStock.componentDto = ComponentDto(
                        name = product.name,
                        code = product.uuid.toString(),
                        id = null,
                        unit = null,
                        category = null
                    )
                }
            }
        }

        return stockFiltered
    }

    companion object {
        val logger = Logger.getLogger(WarehouseService::class.java.name)
    }
}