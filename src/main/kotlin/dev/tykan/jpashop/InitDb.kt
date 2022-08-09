package dev.tykan.jpashop

import dev.tykan.jpashop.domain.*
import dev.tykan.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb(private val initService: InitService) {
    @PostConstruct
    fun callInit() {
        initService.dbInit1()
        initService.dbInit2()
    }

    companion object {
        @Component
        @Transactional
        class InitService(private val em: EntityManager) {
            fun dbInit1() {
                val member = Member(name = "userA", address = Address("서울", "1", "1111"))
                em.persist(member)

                val book1 = createBook("JPA1 BOOK", 10000, 100)
                em.persist(book1)

                val book2 = createBook("JPA2 BOOK", 20000, 100)
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
                val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

                val order = Order.createOrder(member, Delivery(address = member.address), orderItem1, orderItem2)
                em.persist(order)
            }

            fun dbInit2() {
                val member = Member(name = "userB", address = Address("제주", "2", "2222"))
                em.persist(member)

                val book1 = createBook("SPRING1 BOOK", 20000, 200)
                em.persist(book1)

                val book2 = createBook("SPRING2 BOOK", 40000, 300)
                em.persist(book2)

                val orderItem1 = OrderItem.createOrderItem(book1, 20000, 1)
                val orderItem2 = OrderItem.createOrderItem(book2, 40000, 2)

                val order = Order.createOrder(member, Delivery(address = member.address), orderItem1, orderItem2)
                em.persist(order)
            }

            private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
                val book1 = Book()
                book1.name = name
                book1.price = price
                book1.updateStock(stockQuantity)
                return book1
            }
        }
    }
}