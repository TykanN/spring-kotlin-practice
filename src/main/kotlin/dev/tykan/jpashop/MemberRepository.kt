package dev.tykan.jpashop

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository {
    @PersistenceContext
    lateinit var em: EntityManager

    fun save(member: Member): Long {
        em.persist(member)
        return member.id!!
    }

    fun find(id: Long): Member? {
        return em.find(Member::class.java, id)
    }
}
