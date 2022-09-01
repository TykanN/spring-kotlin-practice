package hellojpa.domain

import hellojpa.RoleType
import hellojpa.converter.RoleTypeConverter
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY

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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "locker_id")
    var locker: Locker? = null

    @Embedded
    var homeAddress: Address = Address()
        protected set

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "city", column = Column(name = "work_city")),
        AttributeOverride(name = "street", column = Column(name = "work_street")),
        AttributeOverride(name = "zipcode", column = Column(name = "work_zipcode")),
    )
    var workAddress: Address = Address()
        protected set


    fun updateAddress(address: Address) {
        homeAddress = address
    }

    override fun toString(): String {
        return "Member(name='$name', age=$age)"
    }


}