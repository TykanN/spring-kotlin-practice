package hellojpa

import hellojpa.domain.Member
import hellojpa.domain.Team
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

                val team = Team("teamA")

                em.persist(team)
                val member = Member().apply {
                    name = "teamA"
                    age = 10
                }

                team.addMember(member)


                em.persist(member)

                em.flush()
                em.clear()


                val query = "select m, case when m.age<10 then '핏덩이' else '일반' end from Member m"
                val a = em.createQuery(query).apply {
                    firstResult = 0
                    maxResults = 10
                }.resultList

                print(a)

                tx.commit()
            } catch (e: Exception) {
                tx.rollback()
                e.printStackTrace()
            } finally {
                em.close()
            }
            emf.close()
        }
    }
}


