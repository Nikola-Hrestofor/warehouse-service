package com.example.warehouseservice.dto

import com.example.warehouseservice.api.dto.ComponentDto
import com.example.warehouseservice.dto.enums.UnitType
import java.math.BigDecimal
import java.time.LocalDate

data class WarehouseDto(
    var action:Boolean, //          boolean, --true - add; false - remove
    var amount: BigDecimal?,
    var cost: BigDecimal?,
    var type: UnitType,
    var childId: Long,
    var orderNumber: String?,
    var inDateTime: LocalDate,
    var componentDto: ComponentDto?,
)
