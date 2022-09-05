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
                    " join o.delivery d",
            OrderSimpleQueryDto::class.java
        ).resultList
    }

    /**
     * 조인을 하면서 데이터가 뻥튀기. 주문 1개 * 주문아이템 2개 조인 -> 주문 2개
     * distinct 추가 -> DB 조인에서는 변경 없음
     * 페이징을 메모리에 올려서 하기 때문에 한계가 있음
     * 컬렉션 페치 조인을 2개 이상 할 경우 데이터가 심각하게 뻥튀기되고 정합성에 문제가 생길 수 있음
     */

    fun findAllWithItem(): List<Order> {
        return em.createQuery(
            "select distinct o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d" +
                    " join fetch o.orderItems oi" +
                    " join fetch oi.item i",
            Order::class.java
        ).run {
            firstResult = 1
            maxResults = 100
            resultList
        }
    }

    fun findAllWithMemberDelivery(offset: Int, limit: Int): List<Order> {
        return em.createQuery(
            "select o from Order o" +
                    " left join fetch o.member m" +
                    " left join fetch o.delivery d",
            Order::class.java
        ).run {
            firstResult = offset
            maxResults = limit
            resultList
        }
    }
}