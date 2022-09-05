package dev.tykan.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,

    var name: String = "",

    @Embedded
    var address: Address = Address(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
    @JsonIgnore
    var orders: MutableList<Order> = mutableListOf()

) {


}