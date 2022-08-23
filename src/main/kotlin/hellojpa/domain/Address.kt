package hellojpa.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Address(
    var city: String = "",
    var street: String = "",
    @Column(name = "zipcode")
    var zipcode: String = "",
)