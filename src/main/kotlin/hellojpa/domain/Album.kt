package hellojpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album : Item() {
    var artist: String = ""
}