package blog.entities

import java.time.LocalDateTime
import javax.persistence.*

fun String.toSlug() = lowercase()
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")

@Entity
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

@Entity
@Table(name = "users")
class User(
    @Column(unique = true)
    var login:       String = "",
    var firstName:   String = "",
    var lastName:    String = "",
    var description: String = "",

    @Id @GeneratedValue var id: Long? = null,
)
