package dev.tykan.jpashop.domain.item

import dev.tykan.jpashop.domain.Category
import dev.tykan.jpashop.exception.NotEnoughStockException
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

    stockQuantity: Int = 0,

    @ManyToMany(mappedBy = "items")
    var categories: MutableList<Category> = mutableListOf()

) {

    var stockQuantity = stockQuantity
        protected set

    //== 비즈니스 로직 ==//
    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }

    fun removeStock(quantity: Int) {
        val restStock: Int = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity = restStock
    }
}
