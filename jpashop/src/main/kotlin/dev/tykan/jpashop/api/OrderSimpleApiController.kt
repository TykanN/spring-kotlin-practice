package dev.tykan.jpashop.api

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.domain.OrderStatus
import dev.tykan.jpashop.repository.OrderRepository
import dev.tykan.jpashop.repository.OrderSearch
import dev.tykan.jpashop.repository.OrderSimpleQueryDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

/**
 * Order
 * Order -> Member
 * Order -> Delivery
 * xToOne 관계
 */
@RestController
class OrderSimpleApiController(private val orderRepository: OrderRepository) {

    companion object {
        data class OrderSimpleDto(
            val orderId: Long,
            val name: String,
            val orderDate: LocalDateTime,
            val orderStatus: OrderStatus,
            val address: Address? = null,
        ) {
            constructor(order: Order) : this(
                order.id!!,
                order.member.name, // LAZY 초기화
                order.orderDate,
                order.status,
                order.delivery.address // LAZY 초기화
            )
        }
    }

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllBySearch(OrderSearch())
        // 객체 필드에 접근하면서 Lazy Loading 강제
        for (order in all) {
            order.member.name
            order.delivery.address
        }
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<OrderSimpleDto> {
        val orders = orderRepository.findAllBySearch(OrderSearch())
        // ORDER 2개
        // 1 + 회원 N + 배송 N
        return orders.map { OrderSimpleDto(it) }
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<OrderSimpleDto> {
        val orders = orderRepository.findAllWithMemberDelivery()
        return orders.map { OrderSimpleDto(it) }
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDto> {
        return orderRepository.findOrderDtos()
    }
}