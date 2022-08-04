package dev.tykan.jpashop.domain

import javax.persistence.*

@Entity
class Delivery(

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id: Long? = null,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var order: Order? = null,

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus? = null // READY, COMP
) {

}
