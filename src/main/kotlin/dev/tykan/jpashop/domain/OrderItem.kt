package dev.tykan.jpashop.domain

import dev.tykan.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class OrderItem protected constructor(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    orderPrice: Int = 0,
    count: Int = 0,
) {

    //== 생성 메서드 ==//
    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem(item = item, count = count, orderPrice = orderPrice)
            item.removeStock(count)
            return orderItem
        }
    }


    var orderPrice: Int = orderPrice
        protected set

    var count: Int = count
        protected set

    //== 비즈니스 로직 ==//
    fun cancel() {
        item?.addStock(count)
    }

    val totalPrice: Int
        get() = orderPrice * count
}
