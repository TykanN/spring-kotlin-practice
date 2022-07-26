package dev.tykan.springtest.repository.h2

import dev.tykan.springtest.domain.Member
import dev.tykan.springtest.repository.MemberRepository
import javax.persistence.EntityManager

class JpaMemberRepository(private val em : EntityManager) : MemberRepository {

    override fun save(member: Member): Member{
        em.persist(member)
        return member;
    }

    override fun findById(id: Long): Member? = em.find(Member::class.java, id)

    override fun findByName(name: String): Member? =
        em.createQuery("select m from Member m where m.name = :name", Member::class.java).setParameter("name", name).resultList.firstOrNull()

    override fun findAll(): List<Member> = em.createQuery("select m from Member m", Member::class.java).resultList
}