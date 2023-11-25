package com.example.warehouseservice.repository

import com.example.warehouseservice.dto.enums.UnitType
import com.example.warehouseservice.repository.entity.WarehouseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface WarehouseRepository : PagingAndSortingRepository<WarehouseEntity, Long>,
    JpaRepository<WarehouseEntity, Long> {

    fun getByTypeAndChildId(unitType: UnitType, childId: Long): List<WarehouseEntity>
}