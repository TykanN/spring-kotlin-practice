package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("testMember")
    @Throws(Exception::class)
    fun testMember() {
        //given
        val member = Member("memberA")
        val savedMember = memberRepository.save(member)
        //when
        val findMember = memberRepository.findByIdOrNull(savedMember.id!!)
        //then
        assertThat(findMember?.id).isEqualTo(member.id)
        assertThat(findMember?.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }
}