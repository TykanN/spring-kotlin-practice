package dev.tykan.jpashop.repository

import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.domain.OrderStatus
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Repository
class OrderRepository(private val em: EntityManager) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(orderId: Long): Order? = em.find(Order::class.java, orderId)

    fun findAll(): List<Order> = em.createQuery("select o from Order o", Order::class.java).resultList

    fun findAllBySearch(orderSearch: OrderSearch): List<Order> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(Order::class.java)

        val o: Root<Order> = cq.from(Order::class.java)
        val m = o.join<Any?, Any?>("member", JoinType.INNER)

        val criteria: MutableList<Predicate> = mutableListOf()


        //주문 상태 검색
        orderSearch.orderStatus?.let {
            val status: Predicate = cb.equal(o.get<OrderStatus>("status"), it)
            criteria.add(status)
        }

        //회원 이름 검색
        orderSearch.memberName?.let {
            val name: Predicate = cb.like(m.get<String>("name"), "%${it}%")
            criteria.add(name)
        }

        cq.where(cb.and(*criteria.toTypedArray()))
        val query: TypedQuery<Order> = em.createQuery(cq).setMaxResults(1000)


        return query.resultList
    }

    fun findAllWithMemberDelivery(): List<Order> {
        return em.createQuery(
            "select o from Order o" +
                    " left join fetch o.member m" +
                    " left join fetch o.delivery d",
            Order::class.java
        ).resultList
    }

    fun findOrderDtos(): List<OrderSimpleQueryDto> {
        return em.createQuery(
            "select new dev.tykan.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderSimpleQueryDto::class.java
        ).resultList
    }
}