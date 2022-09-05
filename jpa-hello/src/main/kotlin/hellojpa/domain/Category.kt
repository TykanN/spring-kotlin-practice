package hellojpa.domain

import javax.persistence.*

@Entity
class Category {
    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String = ""

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf()

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")],
    )
    var items: MutableList<Item> = mutableListOf()
}