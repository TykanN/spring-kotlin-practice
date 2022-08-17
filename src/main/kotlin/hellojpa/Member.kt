package hellojpa

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Member(
    @Id
    var id: Long? = null,
    var name: String? = null,
) {
}