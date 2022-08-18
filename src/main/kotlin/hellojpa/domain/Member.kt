package hellojpa.domain

import hellojpa.RoleType
import hellojpa.converter.RoleTypeConverter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(name = "username", length = 10, nullable = false)
    var name: String = "",

    var age: Int? = null,

//    @Enumerated(EnumType.STRING)
    @Convert(converter = RoleTypeConverter::class)
    var roleType: RoleType = RoleType.USER,

    var createdDate: LocalDateTime? = LocalDateTime.now(),

    var lastModifedDate: LocalDateTime? = LocalDateTime.now(),

    @Lob
    var description: String = "",

    var isStrong: Boolean = false,

    @Transient
    var temp: Int = 0,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    var city: String = ""
        protected set

    var street: String = ""
        protected set

    var zipCode: String = ""
        protected set

    fun updateAddress(city: String?, street: String?, zipCode: String?) {

        this.city = city ?: this.city
        this.street = street ?: this.street
        this.zipCode = zipCode ?: this.zipCode


    }
}