package dev.tykan.datajpa.entity

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberTest(@Autowired private var em: EntityManager){
@Test
@DisplayName("testEntity")
@Throws(Exception::class)
fun testEntity() {
    //given
    val teamA = Team("teamA")
    val teamB = Team("teamB")
    em.persist(teamA)
    em.persist(teamB)

    val member1 = Member("member1", 13, team = teamA)
    val member2 = Member("member2", 23, team = teamA)
    val member3 = Member("member3", 33, team = teamB)
    val member4 = Member("member4", 43, team = teamB)

    em.persist(member1)
    em.persist(member2)
    em.persist(member3)
    em.persist(member4)

    em.flush()
    em.clear()
    //when
    val members :List<Member>  = em.createQuery("select m from Member m", Member::class.java).resultList
    for(member:Member in members){
        println("member = $member")
        println("-> member.team = ${member.team}")
    }
    //then
    
}

}