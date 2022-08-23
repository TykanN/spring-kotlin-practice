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
                team.addMember(member2)
                em.persist(team)

                em.flush()
                em.clear()

//                val m1 = em.find(Member::class.java, member1.id)
//                val m2 = em.find(Member::class.java, member2.id)

                val members = em.createQuery("select m from Member m", Member::class.java).resultList

//                val findMember = em.find(Member::class.java, member1.id)
//                val members = findMember.team?.members ?: emptyList()
//
//                for (m in members) {
//                    println(m.name)
//                }

//                val book = Book().apply {
//                    name = "내 책"
//                    author = "gg"
//                }
//                val album = Album().apply {
//                    name = "내 앨범"
//                    artist = "GD"
//                }
//                em.persist(book)
//                em.persist(album)

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


