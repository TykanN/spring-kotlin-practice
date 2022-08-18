package hellojpa.domain

import javax.persistence.Entity
import javax.persistence.JoinColumn

@Entity
class UMember : Member() {
    @JoinColumn(name = "member_id")
    var member: Member? = null
        protected set
}