package dev.tykan.jpashop.api

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.domain.OrderItem
import dev.tykan.jpashop.domain.OrderStatus
import dev.tykan.jpashop.repository.OrderRepository
import dev.tykan.jpashop.repository.OrderSearch
import dev.tykan.jpashop.repository.order.query.OrderFlatDto
import dev.tykan.jpashop.repository.order.query.OrderItemQueryDto
import dev.tykan.jpashop.repository.order.query.OrderQueryDto
import dev.tykan.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderApiController(
    private val orderRepository: OrderRepository,
    private val orderQueryRepository: OrderQueryRepository
) {

    companion object {
        class OrderItemDto(
            val name: String?,
            val orderPrice: Int,
            val count: Int,
        ) {
            constructor(orderItem: OrderItem) : this(orderItem.item?.name, orderItem.orderPrice, orderItem.count)
        }


        class OrderDto(
            val orderId: Long,
            val name: String,
            val orderDate: LocalDateTime,
            val orderStatus: OrderStatus,
            val address: Address?,
            val orderItems: List<OrderItemDto> = listOf()
        ) {
            constructor(order: Order) : this(
                order.id!!,
                order.member.name,
                order.orderDate,
                order.status,
                order.delivery.address,
                order.orderItems.map { OrderItemDto(it) }
            )
        }
    }

    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllBySearch(OrderSearch())
        for (order: Order in all) {
            order.member.name;
            order.delivery.address;
            order.orderItems.forEach { it.item?.name }
        }
        return all
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        val orders = orderRepository.findAllBySearch(OrderSearch())
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        val orders = orderRepository.findAllWithItem()
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v3.1/orders")
    fun ordersV3Page(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "limit", defaultValue = "100") limit: Int
    ): List<OrderDto> {
        val orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.map { OrderDto(it) }
    }

    @GetMapping("/api/v4/orders")
    fun ordersV4(): List<OrderQueryDto> {
        return orderQueryRepository.findOrderQueryDtos()
    }

    @GetMapping("/api/v5/orders")
    fun ordersV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDtoOptimization()
    }

    @GetMapping("/api/v6/orders")
    fun ordersV6Flat(): List<OrderFlatDto> {
        return orderQueryRepository.findAllByDtoFlat()
    }

    @GetMapping("/api/v6.1/orders")
    fun ordersV6(): List<OrderQueryDto> {
        val flats = orderQueryRepository.findAllByDtoFlat()
        val result =
            flats.groupBy { OrderQueryDto(it.orderId, it.name, it.orderDate, it.orderStatus, it.address) }.map {
                it.key.apply {
                    this.orderItems =
                        it.value.map { e -> OrderItemQueryDto(e.orderId, e.itemName, e.orderPrice, e.count) }
                }
            }
        return result
    }
}

