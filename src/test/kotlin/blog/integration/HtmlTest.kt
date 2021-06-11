package blog.integration

import blog.entities.toSlug
import blog.repositories.ArticleRepository
import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HtmlTest(
    client: WebTestClient, articleRepository: ArticleRepository
): DescribeSpec({
    describe("article list page") {
        it("works") {
            val title    = "Reactor Bismuth is out"
            val response = client.get().uri("/blog/articles").exchange()

            response.expectStatus().isOk
            response.expectBody<String>().consumeWith {
                assertThat(it.responseBody).contains("<h1>Blog</h1>", title)
            }
        }
    }

    describe("article page") {
        it("works") {
            val title    = "Reactor Aluminium has landed"
            val response = client.get().uri("/blog/articles/${title.toSlug()}").exchange()

            response.expectStatus().isOk
            response.expectBody<String>().consumeWith {
                assertThat(it.responseBody).contains(title, "Lorem ipsum", "dolor sit amet")
            }
        }
    }
})