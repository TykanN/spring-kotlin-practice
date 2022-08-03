package dev.tykan.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue
    @Column(name = "order_id")
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    var orderDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus, // 주문상태 [ORDER, CANCEL]


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
}