package blog.repositories

import blog.entities.Article
import blog.entities.ArticleWithAddress
import blog.entities.QArticle
import blog.entities.User
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.querydsl.binding.QuerydslBindings
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(excerptProjection = ArticleWithAddress::class)
interface ArticleRepository :
    PagingAndSortingRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    QuerydslBinderCustomizer<QArticle> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>

    override fun customize(bindings: QuerydslBindings, article: QArticle) {
        bindings.bind(article.title).first { path, value -> path.contains(value) }
    }
}

@RepositoryRestResource
interface UserRepository :
    PagingAndSortingRepository<User, Long>,
    QuerydslPredicateExecutor<Article> {
    fun findByLogin(login: String): User?
}
