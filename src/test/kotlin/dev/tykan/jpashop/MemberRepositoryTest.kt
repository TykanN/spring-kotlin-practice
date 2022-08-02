package dev.tykan.jpashop

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository


    @Test
    @DisplayName("TEST MEMBER")
    @Throws(Exception::class)
    @Transactional
//    @Rollback(false)
    fun testMember() {
        //given
        val member = Member(username = "memberA")
        //when
        val saveId = memberRepository.save(member)
        val findMember = memberRepository.find(saveId)!!
        //then
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.username).isEqualTo(member.username)
//        Assertions.assertThat(findMember).isEqualTo(member)
    }

}