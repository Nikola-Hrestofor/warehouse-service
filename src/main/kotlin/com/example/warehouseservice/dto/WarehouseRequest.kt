package com.example.warehouseservice.dto

import com.example.warehouseservice.dto.enums.UnitType
import java.math.BigDecimal
import java.time.LocalDate

data class WarehouseRequest(
    var amount: BigDecimal?,
    var cost: BigDecimal?,
    var type: UnitType,
    var childId: Long,
    var orderNumber: String?
)
