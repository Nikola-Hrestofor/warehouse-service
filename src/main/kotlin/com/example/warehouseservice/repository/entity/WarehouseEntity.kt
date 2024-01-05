package com.example.warehouseservice.repository.entity

import com.example.warehouseservice.dto.enums.UnitType
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import jakarta.persistence.*
import java.math.BigDecimal
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
    @Enumerated(EnumType.STRING)
    var type: UnitType = UnitType.CARD,
    var childId: Long = 0,
    var orderNumber: String = "",
    var inDateTime: LocalDate = LocalDate.now()


) {
    override fun toString(): String {
        return "WarehouseEntity(id=$id, action=$action, amount=$amount, cost=$cost, type=$type, childId=$childId, orderNumber='$orderNumber', inDateTime=$inDateTime)"
    }
}
