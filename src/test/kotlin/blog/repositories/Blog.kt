package blog.repositories

import blog.entities.Article
import blog.entities.User
import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class BlogTest(
    val entityManager:     TestEntityManager,
    val userRepository:    UserRepository,
    val articleRepository: ArticleRepository,
) : DescribeSpec({
    describe("findByIdOrNull") {
        it("works") {
            val juergen = User("springjuergen", "Juergen", "Hoeller")
            val article = Article(
                "Spring Framework 5.0 goes GA",
                "Dear Spring community ...",
                "Lorem ipsum",
                juergen
            )

            entityManager.persist(juergen)
            entityManager.persist(article)
            entityManager.flush()

            val found = articleRepository.findByIdOrNull(article.id!!)

            assertThat(found).isEqualTo(article)
        }
    }

    describe("findByLogin") {
        it("works") {
            val juergen = User("springjuergen", "Juergen", "Hoeller")

            entityManager.persist(juergen)
            entityManager.flush()

            val user = userRepository.findByLogin(juergen.login)

            assertThat(user).isEqualTo(juergen)
        }
    }
})
