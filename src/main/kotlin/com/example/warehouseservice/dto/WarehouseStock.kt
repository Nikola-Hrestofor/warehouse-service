package com.example.warehouseservice.dto

import com.example.warehouseservice.api.dto.ComponentDto
import com.example.warehouseservice.dto.enums.UnitType
import java.math.BigDecimal

data class WarehouseStock(
    var amount: BigDecimal,
    var cost: BigDecimal,
    var type: UnitType,
    var childId: Long,
    var componentDto: ComponentDto?,
) {
    fun rank(): Int {
        return when (type) {
            UnitType.PRODUCT -> 1
            UnitType.CARD -> 2
            else -> 3
        }
    }
}
