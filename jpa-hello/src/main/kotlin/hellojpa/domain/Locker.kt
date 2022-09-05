package hellojpa.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Locker {
    @Id
    @GeneratedValue
    val id: Long? = null
}