package dev.tykan.springtest.service

import dev.tykan.springtest.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    private lateinit var memberService: MemberService


    @Test
    fun join() {
        //given
        val member = Member()
        member.name = "hello"
        //when
        val saveId: Long = memberService.join(member)
        //then
        val findMember = memberService.findOne(saveId)
        assertThat(member.name).isEqualTo(findMember?.name)

    }

    @Test
    fun joinDuplicate() {
        //given
        val member1 = Member()
        member1.name = "spring"

        val member2 = Member()
        member2.name = "spring"
        //when
        memberService.join(member1)
        val error = assertThrows<IllegalStateException> { memberService.join(member2) }

        assertThat(error.message).isEqualTo("이미 존재하는 회원")
        //then
    }


}