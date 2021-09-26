package blog.entities

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*
import javax.persistence.*

fun LocalDateTime.format() = this.format(englishDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

private val englishDateFormatter = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd")
    .appendLiteral(" ")
    .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
    .appendLiteral(" ")
    .appendPattern("yyyy")
    .toFormatter(Locale.ENGLISH)

private fun getOrdinal(n: Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else        -> "${n}th"
}

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
