package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.item.Book
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager

@SpringBootTest
class ItemUpdateTest {

    @Autowired
    private lateinit var em: EntityManager;

    @Test
    @DisplayName("updateTest")
    @Throws(Exception::class)
    fun updateTest() {
        //given
        val book = em.find(Book::class.java, 1L)
        //when
        book.name = "adad"
        //then

    }
}