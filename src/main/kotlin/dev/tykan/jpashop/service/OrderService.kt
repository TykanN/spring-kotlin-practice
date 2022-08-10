package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.Delivery
import dev.tykan.jpashop.domain.Member
import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.domain.OrderItem
import dev.tykan.jpashop.domain.item.Item
import dev.tykan.jpashop.repository.ItemRepository
import dev.tykan.jpashop.repository.MemberRepository
import dev.tykan.jpashop.repository.OrderRepository
import dev.tykan.jpashop.repository.OrderSearch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository,
) {

    /**
     * 주문
     */

    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        //엔티티 조회
        val member: Member = memberRepository.findOne(memberId) ?: throw IllegalStateException("존재하지 않는 회원입니다.")
        val item: Item = itemRepository.findOne(itemId) ?: throw IllegalStateException("존재하지 않는 상품입니다.")

        //배송정보 생성
        val delivery: Delivery = Delivery(address = member.address)

        //주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        //주문 생성
        val order: Order = Order.createOrder(member, delivery, orderItem)

        //주문 저장
        orderRepository.save(order)

        return order.id ?: throw IllegalStateException("주문 아이디 생성 불가");
    }


    /**
     * 주문 취소
     */
    fun cancelOrder(orderId: Long) {
        //주문 엔티티 조회
        val order: Order = orderRepository.findOne(orderId) ?: throw IllegalStateException("존재하지 않는 주문")
        //주문 취소
        order.cancel()
    }

    //    검색
    fun findOrders(orderSearch: OrderSearch): List<Order> {
        return orderRepository.findAllBySearch(orderSearch)
    }
}