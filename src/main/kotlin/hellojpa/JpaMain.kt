package hellojpa

import hellojpa.entity.Member
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

class JpaMain {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("hello")

            val em: EntityManager = emf.createEntityManager()

            val tx: EntityTransaction = em.transaction
            tx.begin()
            try {
                val member = Member(username = "HelloB")
                em.persist(member)
//               val member = em.find(Member::class.java, 2L)
//                member.name = "HelloJPA"
//
//                em.remove(member)
                val findMember = em.createQuery("select  m from Member m", Member::class.java).run {
                    firstResult = 5
                    maxResults = 100
                    resultList
                }

                tx.commit()
            } catch (e: Exception) {
                tx.rollback()
            } finally {
                em.close()
            }
            emf.close()
        }
    }
}


