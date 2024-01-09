package com.example.warehouseservice.dto

import com.example.warehouseservice.api.dto.ComponentDto
import java.math.BigDecimal

data class WarehouseStock(
    var amount: BigDecimal,
    var cost: BigDecimal,
    var type: String,
    var childId: Long,
    var componentDto: ComponentDto?,
)
