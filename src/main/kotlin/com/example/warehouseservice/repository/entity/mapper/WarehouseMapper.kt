package com.example.warehouseservice.repository.entity.mapper

import com.example.warehouseservice.dto.WarehouseDto
import com.example.warehouseservice.repository.entity.WarehouseEntity
import org.mapstruct.Mapper

@Mapper
interface WarehouseMapper {
    fun toModel(entity: WarehouseEntity): WarehouseDto

    fun toEntity(dto: WarehouseDto): WarehouseEntity
}