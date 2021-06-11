package blog.integration

import blog.entities.Article
import blog.entities.User
import blog.repositories.ArticleRepository
import blog.repositories.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ContextConfiguration
class ApiTest(
    private val mockMvc: MockMvc,
): DescribeSpec() {
    @MockkBean private lateinit var userRepository:    UserRepository
    @MockkBean private lateinit var articleRepository: ArticleRepository

    init {
        describe("all articles route") {
            it("works") {
                val juergen = User("springjuergen", "Juergen", "Hoeller")

                val spring5Article = Article(
                    "Spring Framework 5.0 goes GA",
                    "Dear Spring community ...",
                    "Lorem ipsum",
                    juergen
                )
                val spring43Article = Article(
                    "Spring Framework 4.3 goes GA",
                    "Dear Spring community ...",
                    "Lorem ipsum",
                    juergen
                )

                every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(
                    spring5Article,
                    spring43Article
                )

                mockMvc.perform(
                    get("/api/blog/articles").accept(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
                    .andExpect(jsonPath("\$.[0].slug").value(spring5Article.slug))
                    .andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
                    .andExpect(jsonPath("\$.[1].slug").value(spring43Article.slug))
            }
        }

        describe("all users route") {
            it("works") {
                val juergen  = User("springjuergen", "Juergen", "Hoeller")
                val smaldini = User("smaldini", "Stéphane", "Maldini")

                every { userRepository.findAll() } returns listOf(juergen, smaldini)

                mockMvc.perform(
                    get("/api/blog/users").accept(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("\$.[0].login").value(juergen.login))
                    .andExpect(jsonPath("\$.[1].login").value(smaldini.login))
            }
        }
    }
}