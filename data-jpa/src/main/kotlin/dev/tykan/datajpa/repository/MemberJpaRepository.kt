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

    fun delete(member: Member){
        em.remove(member)
    }

    fun findAll(): List<Member>{
        return em.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findById(id: Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun count():Long{
        return em.createQuery("select count (m) from Member m", Long::class.javaObjectType).singleResult
    }

    fun find(id: Long): Member? = em.find(Member::class.java, id)

    fun findByUsernameAndAgeGreaterThan(username: String, age:Int): List<Member> {
        return em.createQuery(
            "select m from Member m where m.username = :username and m.age > :age",
            Member::class.java)
            .setParameter("username", username)
            .setParameter("age", age)
            .resultList
    }
}