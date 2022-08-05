package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.Member
import dev.tykan.jpashop.domain.Order
import dev.tykan.jpashop.domain.OrderStatus
import dev.tykan.jpashop.domain.item.Book
import dev.tykan.jpashop.domain.item.Item
import dev.tykan.jpashop.exception.NotEnoughStockException
import dev.tykan.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
internal class OrderServiceTest {

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    @DisplayName("상품주문")
    @Throws(Exception::class)
    fun 상품주문() {
        //given
        val member: Member = createMember()

        val book: Book = createBook(10000, 10, "안녕 JPA")

        val orderCount: Int = 2

        //when
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)
        //then
        val getOrder: Order = orderRepository.findOne(orderId)!!
        assertThat(getOrder.status).isEqualTo(OrderStatus.ORDER)
        assertThat(getOrder.orderItems.size).isEqualTo(1)
        assertThat(getOrder.totalPrice).isEqualTo(10000 * 2)
        assertThat(book.stockQuantity).isEqualTo(8)
    }


    @Test
    @DisplayName("재고수량초과")
    @Throws(Exception::class)
    fun 재고수량초과() {
        //given
        val member: Member = createMember()

        val book: Item = createBook(10000, 8, "안녕 JPA")

        val orderCount = 9
        //when
        //then
        assertThrows<NotEnoughStockException> { orderService.order(member.id!!, book.id!!, orderCount) }
    }

    @Test
    @DisplayName("주문취소")
    @Throws(Exception::class)
    fun 주문취소() {
        //given
        val member: Member = createMember()
        val book: Item = createBook(10000, 8, "안녕 JPA")

        val orderCount = 2

        val orderId = orderService.order(member.id!!, book.id!!, orderCount)
        //when
        orderService.cancelOrder(orderId)
        //then
        val getOrder: Order = orderRepository.findOne(orderId)!!
        assertThat(getOrder.status).isEqualTo(OrderStatus.CANCEL)
        assertThat(book.stockQuantity).isEqualTo(8)
    }

    private fun createBook(price: Int, stock: Int, name: String): Book {
        val book: Book = Book()
        book.price = price
        book.name = name
        book.addStock(stock)
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member: Member = Member(name = "회원1", address = Address("서울", "강가", "32314"))
        em.persist(member)
        return member
    }


}