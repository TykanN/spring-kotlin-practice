package dev.tykan.jpashop.domain

import dev.tykan.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class Category(
    @Id @GeneratedValue
    @Column(name = "category")
    var id: Long?,

    var name: String,


    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")],
    )
    var items: MutableList<Item> = mutableListOf(),

    parent: Category,

    child: MutableList<Category>
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category = parent
        protected set

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL])
    var child: MutableList<Category> = child
        protected set

    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}