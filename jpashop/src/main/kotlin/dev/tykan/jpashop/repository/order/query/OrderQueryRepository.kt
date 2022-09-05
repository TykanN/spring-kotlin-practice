package dev.tykan.jpashop.repository.order.query

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderQueryRepository(private val em: EntityManager) {
    fun findOrderQueryDtos(): List<OrderQueryDto> {
        val result: List<OrderQueryDto> = findOrders()
        result.forEach {
            val orderItems: List<OrderItemQueryDto> = findOrderItems(it.orderId)
            it.orderItems = orderItems
        }
        return result
    }

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "select new dev.tykan.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id ,i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id = :orderId", OrderItemQueryDto::class.java

        ).run {
            setParameter("orderId", orderId)
            resultList
        }

    }

    private fun findOrders(): List<OrderQueryDto> {
        return em.createQuery(
            "select new dev.tykan.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d",
            OrderQueryDto::class.java
        ).resultList
    }

    fun findAllByDtoOptimization(): List<OrderQueryDto> {
        val result: List<OrderQueryDto> = findOrders()

        val orderItemMap: Map<Long, List<OrderItemQueryDto>> = findOrderItemMap(toOrderIds(result))

        result.forEach {
            it.orderItems = orderItemMap[it.orderId] ?: listOf()
        }

        return result
    }

    private fun findOrderItemMap(orderIds: List<Long>): Map<Long, List<OrderItemQueryDto>> {
        val orderItems: List<OrderItemQueryDto> = em.createQuery(
            "select new dev.tykan.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id ,i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id in :orderIds",
            OrderItemQueryDto::class.java
        ).run {
            setParameter("orderIds", orderIds)
            resultList
        }

        return orderItems.groupBy { it.orderId }
    }

    private fun toOrderIds(result: List<OrderQueryDto>) = result.map { it.orderId }

    fun findAllByDtoFlat(): List<OrderFlatDto> {
        return em.createQuery(
            "select new dev.tykan.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d" +
                    " join o.orderItems oi" +
                    " join oi.item i",
            OrderFlatDto::class.java
        ).resultList
    }

}