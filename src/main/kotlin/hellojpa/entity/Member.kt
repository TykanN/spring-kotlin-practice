package hellojpa.entity

import hellojpa.RoleType
import hellojpa.converter.RoleTypeConverter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", length = 10, nullable = false)
    var username: String = "",

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
}