package dev.tykan.jpashop.repository

import dev.tykan.jpashop.domain.Order
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderRepository(private val em: EntityManager) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(orderId: Long): Order? = em.find(Order::class.java, orderId)

//    fun findAll(orderSearch: OrderSearch): List<Order> {
//        return listOf()
//    }
}