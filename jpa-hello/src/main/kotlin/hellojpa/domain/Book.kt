package hellojpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book : Item() {
    var isbn: String = ""
    var author: String = ""
}