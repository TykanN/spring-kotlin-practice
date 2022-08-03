package dev.tykan.jpashop.domain

import dev.tykan.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class OrderItem(
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order,

    orderPrice: Int,
    count: Int,
) {
    var orderPrice: Int = orderPrice
        protected set

    var count: Int = count
        protected set

}
