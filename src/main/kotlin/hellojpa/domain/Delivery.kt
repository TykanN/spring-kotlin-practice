package hellojpa.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Delivery {
    @Id
    @GeneratedValue
    var id: Long? = null

    var city: String? = ""
    var street: String? = ""
    var zipcode: String? = ""
}