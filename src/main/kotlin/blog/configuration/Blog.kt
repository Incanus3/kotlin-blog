package blog.configuration

import blog.entities.Article
import blog.entities.User
import blog.repositories.ArticleRepository
import blog.repositories.UserRepository
// import com.fasterxml.jackson.databind.ObjectMapper
// import com.fasterxml.jackson.databind.SerializationFeature
// import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.CorsRegistry
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@ConstructorBinding
@ConfigurationProperties("blog")
data class BlogProperties(val title: String = "Blog", val banner: Banner = Banner()) {
    data class Banner(val title: String = "", val content: String = "")
}

@Component
class CustomizedRestMvcConfiguration : RepositoryRestConfigurer {
    override fun configureRepositoryRestConfiguration(
        config: RepositoryRestConfiguration,
        cors: CorsRegistry,
    ) {
        config.setBasePath("/data")
        config.repositoryDetectionStrategy =
            RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED
    }
}

// @Configuration
// class CustomizedWebMvcConfiguration : WebMvcConfigurer {
//     @Bean
//     fun jsonApiConfiguration(): JsonApiConfiguration {
//         println("jsonApiConfiguration called")
//         return JsonApiConfiguration()
//             .withJsonApiVersionRendered(true)
//             .withObjectMapperCustomizer { objectMapper: ObjectMapper ->
//                 // put your additional object mapper config here
//                 objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
//             }
//     }
// }

@Configuration
class BlogConfiguration {
    @Bean
    fun databaseInitializer(
        userRepository:    UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {
        val smaldini = userRepository.save(
            User("smaldini", "Stéphane", "Maldini")
        )

        articleRepository.save(
            Article(
                title    = "Reactor Bismuth is out",
                headline = "Lorem ipsum",
                content  = "dolor sit amet",
                author   = smaldini,
            )
        )
        articleRepository.save(
            Article(
                title    = "Reactor Aluminium has landed",
                headline = "Lorem ipsum",
                content  = "dolor sit amet",
                author   = smaldini,
            )
        )
    }
}
