package com.example.warehouseservice.service

import com.example.warehouseservice.dto.WarehouseDto
import com.example.warehouseservice.dto.WarehouseRequest
import com.example.warehouseservice.dto.enums.UnitType
import com.example.warehouseservice.repository.WarehouseRepository
import com.example.warehouseservice.repository.entity.mapper.WarehouseMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class WarehouseService(val warehouseRepository: WarehouseRepository,
                       val mapper: WarehouseMapper) {

    fun addUnits(warehouseRequest: WarehouseRequest): Long? {
        val dto = WarehouseDto(true,
            warehouseRequest.amount,
            warehouseRequest.cost,
            warehouseRequest.type,
            warehouseRequest.childId,
            warehouseRequest.orderNumber,
            LocalDate.now())

        val save = warehouseRepository.save(mapper.toEntity(dto))
        return save.id
    }

    fun seizeUnits(warehouseRequest: WarehouseRequest): BigDecimal? {
        val dto = WarehouseDto(false,
            warehouseRequest.amount,
            warehouseRequest.cost,
            warehouseRequest.type,
            warehouseRequest.childId,
            warehouseRequest.orderNumber,
            LocalDate.now())

        val save = warehouseRepository.save(mapper.toEntity(dto))
        return BigDecimal.valueOf(3456L)
    }

    fun getWarehouse(unitType: UnitType, childId: Long): WarehouseRequest {
        val entity = warehouseRepository.getByTypeAndChildId(unitType, childId)

        val dtos = entity.map { warehouseEntity -> mapper.toModel(warehouseEntity) }

        val amount = dtos.stream()
            .map(WarehouseDto::amount)
            .reduce { bigDecimal: BigDecimal?, bigDecimal2: BigDecimal? -> bigDecimal?.multiply(bigDecimal2) }
            .orElse(BigDecimal.ZERO)

        val warehouse = dtos.stream().findFirst()
            .orElse(null)

        return WarehouseRequest(amount,
            BigDecimal.valueOf(55L),
            warehouse.type,
            warehouse.childId,
            "")
    }

    fun getWarehouse(pageable: Pageable) : Page<WarehouseDto> {
        return warehouseRepository.findAll(pageable)
            .map { warehouseEntity -> mapper.toModel(warehouseEntity) }
    }
}