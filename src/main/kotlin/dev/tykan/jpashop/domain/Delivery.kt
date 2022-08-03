package dev.tykan.jpashop.domain

import javax.persistence.*

@Entity
class Delivery(

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id: Long,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var order: Order,

    @Embedded
    var address: Address,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus // READY, COMP
) {

}
