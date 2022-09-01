package hellojpa

import hellojpa.domain.Member
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

                for (i in 1..100 step 2) {
                    val member = Member().apply {
                        name = "member$i"
                        age = i
                    }
                    em.persist(member)
                }

                em.flush()
                em.clear()


                val a = em.createQuery("select m from Member m order by m.age desc", Member::class.java).apply {
                    firstResult = 10
                    maxResults = 10
                }.resultList

                println(a.toString())


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


