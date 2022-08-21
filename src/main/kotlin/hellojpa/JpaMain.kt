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


                val member1 = Member().apply {
                    this.name = "해리 케인"
//                    this.team = team
                }

                val member2 = Member().apply {
                    this.name = "손흥민"
//                    this.team = team
                }


                em.persist(member1)
                em.persist(member2)

                val team = Team("토트넘")
                team.addMember(member1)
                em.persist(team)

                em.flush()
                em.clear()

                val findMember = em.find(Member::class.java, member1.id)
                val members = findMember.team?.members ?: emptyList()

                for (m in members) {
                    println(m.name)
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


