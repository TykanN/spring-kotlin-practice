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
                val team2 = Team("teamB")

                em.persist(team)
                em.persist(team2)
                val member = Member().apply {
                    name = "1"
                    age = 10
                }
                val member2 = Member().apply {
                    name = "2"
                    age = 10
                }
                val member3 = Member().apply {
                    name = "3"
                    age = 10
                }


                team.addMember(member)
                team.addMember(member2)
                team2.addMember(member3)


                em.persist(member)
                em.persist(member2)
                em.persist(member3)

                em.flush()
                em.clear()


                val query = "update Member m set m.age = 20 where m.name = '2'"
                val a = em.createQuery(query).executeUpdate()

                println(a)


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


