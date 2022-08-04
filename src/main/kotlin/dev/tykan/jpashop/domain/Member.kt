package dev.tykan.jpashop.domain

import javax.persistence.*

@Entity
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,

    var name: String,

    @Embedded
    var address: Address,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
    var orders: MutableList<Order> = mutableListOf()

) {


}