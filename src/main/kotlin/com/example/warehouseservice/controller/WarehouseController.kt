package com.example.warehouseservice.controller

import com.example.warehouseservice.dto.WarehouseDto
import com.example.warehouseservice.dto.WarehouseRequest
import com.example.warehouseservice.dto.WarehouseStock
import com.example.warehouseservice.dto.enums.UnitType
import com.example.warehouseservice.service.WarehouseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.logging.Logger

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/v1/warehouse")
class WarehouseController(val warehouseService: WarehouseService) {
    companion object {
        val logger = Logger.getLogger(WarehouseController::class.java.name)
    }

    @PostMapping("/add")
    fun addNewUnit(@RequestBody warehouseRequest: WarehouseRequest): BigDecimal? {
        logger.info("new unit $warehouseRequest")
        return warehouseService.addUnits(warehouseRequest)
    }

    @PostMapping("/seize")
    fun seizeUnit(@RequestBody warehouseRequest: WarehouseRequest): BigDecimal? {
        logger.info("new unit $warehouseRequest")
        return warehouseService.seizeUnits(warehouseRequest)
    }

    @GetMapping("/unit")
    fun getWarehouse(@RequestParam unitType: UnitType, @RequestParam childId: Long) : WarehouseRequest{
        return warehouseService.getWarehouse(unitType, childId)
    }

    @GetMapping
    fun getWarehouse(pageable: Pageable) : Page<WarehouseDto> {
        return warehouseService.getWarehouse(pageable)
    }

    @GetMapping("/stock")
    fun getWarehouse() : List<WarehouseStock>?{
        return warehouseService.getWarehouse()
    }
}