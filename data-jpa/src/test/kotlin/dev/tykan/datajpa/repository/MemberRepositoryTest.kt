package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Member
import dev.tykan.datajpa.entity.Team
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
    @Autowired
    lateinit var teamRepository: TeamRepository

    @Test
    @DisplayName("testMember")
    @Throws(Exception::class)
    fun testMember() {
        //given
        val member = Member("memberA", 10)
        val savedMember = memberRepository.save(member)
        //when
        val findMember = memberRepository.findByIdOrNull(savedMember.id!!)
        //then
        assertThat(findMember?.id).isEqualTo(member.id)
        assertThat(findMember?.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }

    @Test
    @DisplayName("BasicCRUD")
    @Throws(Exception::class)
    fun basicCrud() {
        //given
        val member1 = Member("member1", 10)
        val member2 = Member("member2", 10)
        memberRepository.save(member1)
        memberRepository.save(member2)
        //when
        val findMember1 = memberRepository.findByIdOrNull(member1.id!!)
        val findMember2 = memberRepository.findByIdOrNull(member2.id!!)
        //then
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        // 리스트 조회 검증
        val all = memberRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        // 카운트 검증
        val count = memberRepository.count()
        assertThat(count).isEqualTo(2)

        // 삭제 검증
        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    @DisplayName("greaterThan")
    @Throws(Exception::class)
    fun greaterThan() {
        //given
        val m1 = Member("aaa", 10)
        val m2 = Member("aaa", 20)
        memberRepository.save(m1)
        memberRepository.save(m2)
        //when
        val result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15)
        //then

        assertThat(result.first().username).isEqualTo("aaa")
        assertThat(result.first().age).isEqualTo(20)

    }

    @Test
    @DisplayName("test named query")
    @Throws(Exception::class)
    fun testNamedQuery() {
        //given
        val m1 = Member("aaa", 10)
        val m2 = Member("bbb", 20)
        memberRepository.save(m1)
        memberRepository.save(m2)
        //when
        val result = memberRepository.findByUsername("aaa")
        //then
        assertThat(result.first()).isEqualTo(m1)
    }

    @Test
    @DisplayName("Dto test")
    @Throws(Exception::class)
    fun dtoTest() {
        //given

        val team = Team("teamA")
        teamRepository.save(team)


        val m1 = Member("aaa", 10)
        m1.changeTeam(team)
        memberRepository.save(m1)


        val memberDto = memberRepository.findMemberDto()
        println(memberDto)

    }

    @Test
    @DisplayName("test Optional")
    @Throws(Exception::class)
    fun testOptional() {
        //given
        val member = memberRepository.findOptionalByUsername("asd")
        println(member)
    }
}