package com.example.warehouseservice.repository.entity

import com.example.warehouseservice.dto.enums.UnitType
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.data.relational.core.mapping.Table
import jakarta.persistence.*
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

@Entity
@Table(name = "warehouse")
@NoArgsConstructor
@AllArgsConstructor
class WarehouseEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0,
    var action:Boolean = true, //          boolean, --true - add; false - remove
    var amount: BigDecimal = BigDecimal.ZERO,
    var cost: BigDecimal = BigDecimal.ZERO,
    var type: UnitType = UnitType.CARD,
    var childId: Long = 0,
    var orderNumber: String = "",
    var inDateTime: LocalDate = LocalDate.now()
)
