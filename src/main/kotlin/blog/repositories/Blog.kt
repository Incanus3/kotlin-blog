package blog.repositories

import blog.entities.Article
import blog.entities.ArticleWithAuthor
import blog.entities.QArticle
import blog.entities.User
import io.crnk.data.jpa.JpaEntityRepositoryBase
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.querydsl.binding.QuerydslBindings
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(excerptProjection = ArticleWithAuthor::class)
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
    QuerydslPredicateExecutor<User> {
    fun findByLogin(login: String): User?
}

class JsonApiArticleRepository : JpaEntityRepositoryBase<Article, Long>(Article::class.java) {
    override fun <S : Article> save(entity: S): S {
        // add your save logic here
        return super.save(entity)
    }

    override fun <S : Article> create(entity: S): S {
        // add your create logic here
        return super.create(entity)
    }

    override fun delete(id: Long) {
        // add your save logic here
        super.delete(id)
    }
}

class JsonApiUserRepository : JpaEntityRepositoryBase<User, Long>(User::class.java)
