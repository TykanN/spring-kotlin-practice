package dev.tykan.jpashop.repository

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address? = null,
)