package hellojpa.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Team(name: String) {
    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String = name
        protected set
}