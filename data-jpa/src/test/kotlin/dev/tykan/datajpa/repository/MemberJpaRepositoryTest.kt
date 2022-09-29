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
    
    @Test
    @DisplayName("BasicCRUD")
    @Throws(Exception::class)
    fun basicCrud() {
        //given
        val member1 = Member("member1", 10)
        val member2 = Member("member2", 10)
        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)
        //when
        val findMember1 = memberJpaRepository.findById(member1.id!!)
        val findMember2 = memberJpaRepository.findById(member2.id!!)
        //then
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        // 리스트 조회 검증
        val all = memberJpaRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        // 카운트 검증
        val count = memberJpaRepository.count()
        assertThat(count).isEqualTo(2)

        // 삭제 검증
        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

        val deletedCount = memberJpaRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }
    
    @Test
    @DisplayName("greaterThan")
    @Throws(Exception::class)
    fun greaterThan() {
        //given
        val m1 = Member("aaa", 10)
        val m2 = Member("aaa", 20)
        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)
        //when
        val result = memberJpaRepository.findByUsernameAndAgeGreaterThan("aaa", 15)
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
        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)
        //when
        val result = memberJpaRepository.findByUsername("aaa")
        //then
        assertThat(result.first()).isEqualTo(m1)
    }
}