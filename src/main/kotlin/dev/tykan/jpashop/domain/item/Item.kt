package dev.tykan.jpashop.domain.item

import dev.tykan.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    @Id @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null,

    var name: String = "",

    var price: Int = 0,

    var stockQuantity: Int = 0,

    @ManyToMany(mappedBy = "items")
    var categories: MutableList<Category> = mutableListOf()

)
