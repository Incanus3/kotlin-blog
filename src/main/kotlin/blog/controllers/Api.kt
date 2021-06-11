package blog.controllers

import blog.entities.Article
import blog.repositories.ArticleRepository
import blog.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/blog/articles")
class ArticlesController(private val repository: ArticleRepository) {
    @GetMapping
    fun all(): Iterable<Article> = repository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findBySlug(@PathVariable slug: String) = repository.findBySlug(slug)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
}

@RestController
@RequestMapping("/api/blog/users")
class UsersController(private val repository: UserRepository) {
    @GetMapping
    fun all() = repository.findAll()

    @GetMapping("/{login}")
    fun findByLogin(@PathVariable login: String) = repository.findByLogin(login)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}