package hellojpa.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(name: String) {
    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String = name
        protected set

    @OneToMany(mappedBy = "team")
    val members: List<Member> = mutableListOf()
}