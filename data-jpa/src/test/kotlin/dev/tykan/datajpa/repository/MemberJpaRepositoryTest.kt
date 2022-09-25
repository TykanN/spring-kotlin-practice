package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberJpaRepositoryTest {
    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Test
    @DisplayName("testMember")
    @Throws(Exception::class)
    fun testMember() {
        //given
        val member = Member("memberA", 10)
        val savedMember = memberJpaRepository.save(member)
        //when
        val findMember = memberJpaRepository.find(savedMember.id!!)
        //then
        assertThat(findMember?.id).isEqualTo(member.id)
        assertThat(findMember?.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }
}