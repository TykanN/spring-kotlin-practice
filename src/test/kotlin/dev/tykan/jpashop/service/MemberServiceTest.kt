package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.Address
import dev.tykan.jpashop.domain.Member
import dev.tykan.jpashop.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository


    @Test
    @DisplayName("회원가입")
    @Throws(Exception::class)
    fun 회원가입() {
        //given
        val member: Member = Member(name = "kim", address = Address("", "", ""), orders = mutableListOf())
        //when
        val saveId: Long = memberService.join(member)
        //then
        assertThat(memberRepository.findOne(saveId)).isEqualTo(member)

    }

    @Test()
    @DisplayName("중복_회원_예외")
    @Throws(Exception::class)
    fun 중복_회원_예외() {
        //given
        val member1: Member = Member(name = "kim", address = Address("", "", ""), orders = mutableListOf())
        val member2: Member = Member(name = "kim", address = Address("", "", ""), orders = mutableListOf())
        //when
        memberService.join(member1)
        //then
        assertThatThrownBy { memberService.join(member2) }.isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("존재")
    }
}