package hellojpa.domain

import javax.persistence.*

@Entity
class OrderItem(
    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "item_id")
    val itemId: Long,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "order_price")
    var orderPrice: Int = 0
        protected set

    var count: Int = 0
        protected set
}