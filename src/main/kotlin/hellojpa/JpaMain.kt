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

                val team = Team("토트넘")
                em.persist(team)

                val member = Member().apply {
                    this.name = "해리 케인"
                    this.team = team
                }
                

                em.persist(member)

                em.flush()
                em.clear()

                val findMember = em.find(Member::class.java, member.id)
                val findTeam = findMember.team!!
                println("=====${findTeam.name}")

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


