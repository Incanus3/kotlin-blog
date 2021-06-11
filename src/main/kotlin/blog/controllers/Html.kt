package blog.controllers

import blog.BlogProperties
import blog.entities.Article
import blog.entities.User
import blog.entities.format
import blog.repositories.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/blog")
class HtmlController(
	private val repository: ArticleRepository,
	private val properties: BlogProperties,
) {
	@GetMapping("/articles")
	fun blog(model: Model): String {
		model["title"   ] = properties.title
		model["banner"  ] = properties.banner
		model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }

		return "blog"
	}

	@GetMapping("/articles/{slug}")
	fun article(@PathVariable slug: String, model: Model): String {
		val article = repository.findBySlug(slug)?.render()
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

		model["title"  ] = article.title
		model["article"] = article

		return "article"
	}

	fun Article.render() = RenderedArticle(slug, title, headline, content, author, addedAt.format())

	data class RenderedArticle(
		val slug:     String,
		val title:    String,
		val headline: String,
		val content:  String,
		val author:   User,
		val addedAt:  String,
	)
}