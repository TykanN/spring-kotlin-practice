package dev.tykan.jpashop.repository.order.query

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderFlatDto(
    // Order
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address? = null,

    // OrderItem
    val itemName: String,
    val orderPrice: Int,
    val count: Int,
)

