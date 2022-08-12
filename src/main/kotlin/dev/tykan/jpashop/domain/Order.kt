package dev.tykan.jpashop.domain

import org.hibernate.annotations.BatchSize
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order protected constructor(
    @Id @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    var orderDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER, // 주문상태 [ORDER, CANCEL]


) {

    //==연관관계 메서드==//
    fun updateMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun addOrderItem(orderItem: OrderItem) {
        this.orderItems.add(orderItem)
        orderItem.order = this
    }

    fun updateDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }


    //== 생성 메서드 ==//
    companion object {
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order(member = member, delivery = delivery)
            for (orderItem: OrderItem in orderItems) {
                order.addOrderItem(orderItem)
            }
            return order
        }
    }

    //== 비지니스 로직 ==//

    /**
     * 주문 취소
     */

    fun cancel() {
        if (delivery.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        for (orderItem: OrderItem in orderItems) {
            orderItem.cancel()
        }
    }

    //== 비지니스 로직 ==/
    /**
     * 전체 주문 가격 조회
     */

    val totalPrice: Int
        get() = orderItems.sumOf { it.totalPrice }

}