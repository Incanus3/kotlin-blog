package blog.repositories

import blog.entities.Article
import blog.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ArticleRepository : PagingAndSortingRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

@RepositoryRestResource
interface UserRepository : PagingAndSortingRepository<User, Long> {
    fun findByLogin(login: String): User?
}
