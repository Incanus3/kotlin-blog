package blog.entities

import com.querydsl.core.annotations.QueryEntity
import io.crnk.core.queryspec.pagingspec.NumberSizePagingSpec
import io.crnk.core.resource.annotations.JsonApiResource
import org.springframework.data.rest.core.config.Projection
// import org.springframework.hateoas.server.core.Relation
import java.time.LocalDateTime
import javax.persistence.*

fun String.toSlug() = lowercase()
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")

@Entity
@QueryEntity
@JsonApiResource(type = "article", resourcePath = "articles")
// @Relation(collectionRelation = "articles")
@Table(name = "articles")
class Article(
    // these should not have defaults, but if they don't,
    // ArticleRepository.findAllByOrderByAddedAtDesc fails with "no default constructor"
    // look at https://docs.spring.io/spring-data/commons/docs/current/reference/html/#mapping.fundamentals to find out more
    var title:    String = "",
    var headline: String = "",
    var content:  String = "",

    @ManyToOne var author: User = User(),

    @Column(unique = true)
    var slug: String = title.toSlug(),

    var addedAt: LocalDateTime = LocalDateTime.now(),

    @Id @GeneratedValue var id: Long? = null,
)

@Projection(name = "withAuthor", types = [Article::class])
interface ArticleWithAuthor {
    val id: Long
    val title: String
    val headline: String
    val content: String
    val slug: String
    val addedAt: LocalDateTime
    val author: User
}

@Entity
@QueryEntity
@JsonApiResource(type = "user", resourcePath = "users")
// @Relation(collectionRelation = "users")
@Table(name = "users")
class User(
    @Column(unique = true)
    var login:       String = "",
    var firstName:   String = "",
    var lastName:    String = "",
    var description: String = "",

    @Id @GeneratedValue var id: Long? = null,

    @OneToMany(mappedBy = "author") val articles: List<Article> = listOf(),
)
