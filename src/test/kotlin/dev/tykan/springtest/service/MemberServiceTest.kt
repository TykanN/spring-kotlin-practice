package dev.tykan.springtest.service

import dev.tykan.springtest.domain.Member
import dev.tykan.springtest.repository.MemoryMemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MemberServiceTest {

    private lateinit var memberService: MemberService

    private lateinit var memberRepository: MemoryMemberRepository

    @BeforeEach
    fun beforeEach() {
        memberRepository = MemoryMemberRepository()
        memberService = MemberService(memberRepository)
    }

    @AfterEach
    fun afterEach() {
        memberRepository.clearStore()
    }


    @Test
    fun join() {
        //given
        val member = Member()
        member.name = "spring"
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
        val assertThrows = assertThrows<IllegalStateException> { memberService.join(member2) }
        assertThat(assertThrows.message).isEqualTo("이미 존재하는 회원")
        //then
    }

    @Test
    fun findMembers() {
    }

    @Test
    fun findOne() {
    }
}