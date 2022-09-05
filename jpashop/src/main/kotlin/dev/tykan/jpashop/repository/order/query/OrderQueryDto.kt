package dev.tykan.jpashop.repository.order.query

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address? = null,
    var orderItems: List<OrderItemQueryDto> = listOf()
) {

    constructor(
        orderId: Long,
        name: String,
        orderDate: LocalDateTime,
        orderStatus: OrderStatus,
        address: Address?
    ) : this(orderId, name, orderDate, orderStatus, address, listOf())

}

