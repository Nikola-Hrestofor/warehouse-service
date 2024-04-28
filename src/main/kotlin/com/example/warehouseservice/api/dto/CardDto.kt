package com.example.warehouseservice.api.dto

import java.math.BigDecimal

data class CardDto(
    val id: Long,
    val name: String,
    val code: String,
    val stock: BigDecimal = BigDecimal(15)
)

