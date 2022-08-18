package hellojpa.domain

import javax.persistence.*

@Entity
class Item(
    var name: String = "",

    var price: Int = 0,

    @Column(name = "stock")
    var stockQuantity: Int = 0,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    var id: Long? = null
}