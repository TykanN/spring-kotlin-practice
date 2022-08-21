package hellojpa.domain

import javax.persistence.*

@Entity
class OrderItem(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,
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