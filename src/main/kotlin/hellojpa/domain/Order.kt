package hellojpa.domain

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long? = null
        protected set

    @Column(name = "member_id")
    var memberId: Long? = null
        protected set

}