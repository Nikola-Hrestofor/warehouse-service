package com.example.warehouseservice.api.dto

import java.math.BigDecimal

data class ComponentDto(
    val id: Long?,
    val name: String,
    val unit: String,
    val category: CategoryDto,
    val code: String,
//    var stock: BigDecimal?
) {
    override fun toString(): String {
        return "ComponentDto(id=$id, name='$name', unit='$unit', category=$category, code='$code')"
    }
}
