package dev.tykan.datajpa.repository

import dev.tykan.datajpa.entity.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class MemberJpaRepository(private val em: EntityManager) {

    fun save(member: Member): Member {
        em.persist(member)
        return member
    }

    fun find(id: Long): Member? = em.find(Member::class.java, id)
}