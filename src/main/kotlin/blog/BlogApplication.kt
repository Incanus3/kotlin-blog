package blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy as RDS

@ConstructorBinding
@ConfigurationProperties("blog")
data class BlogProperties(val title: String = "Blog", val banner: Banner = Banner()) {
    data class Banner(val title: String = "", val content: String = "")
}

@Component
class CustomizedRestMvcConfiguration : RepositoryRestConfigurer {
    override fun configureRepositoryRestConfiguration(
        config: RepositoryRestConfiguration,
        cors:   CorsRegistry,
    ) {
        config.setBasePath("/data")
        config.repositoryDetectionStrategy = RDS.RepositoryDetectionStrategies.ANNOTATED
    }
}

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
