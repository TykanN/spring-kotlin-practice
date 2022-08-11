package dev.tykan.jpashop.api

import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.repository.OrderRepository
import dev.tykan.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(private val orderRepository: OrderRepository) {

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
}