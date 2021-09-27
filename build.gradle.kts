import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")           version "1.5.30"
    kotlin("kapt")          version "1.5.30"
    kotlin("plugin.spring") version "1.5.30"

    id("org.flywaydb.flyway")             version "7.14.1"
    id("org.springframework.boot")        version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-mustache:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:2.5.4")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.5.4")
    implementation("com.querydsl:querydsl-jpa:4.4.0")
    // implementation("com.toedter:spring-hateoas-jsonapi:1.0.2")

    implementation("io.crnk:crnk-bom:3.4.20210509072026")
    implementation("io.crnk:crnk-setup-spring-boot2:3.4.20210509072026")
    implementation("io.crnk:crnk-data-jpa:3.4.20210509072026")
    implementation("io.crnk:crnk-home:3.4.20210509072026")
    implementation("io.crnk:crnk-ui:3.4.20210509072026")

    // implementation("io.crnk:crnk-data-facet:3.4.20210509072026")
    // implementation("io.crnk:crnk-format-plain-json:3.4.20210509072026")
    // implementation("io.crnk:crnk-validation:3.4.20210509072026")
    // implementation("io.crnk:crnk-operations:3.4.20210509072026")
    // implementation("io.crnk:crnk-security:3.4.20210509072026")

    kapt("com.querydsl:querydsl-apt:4.4.0:general")

    runtimeOnly("com.h2database:h2:1.4.200")

    developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.4") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.1")
    testImplementation("io.kotest:kotest-assertions-core:4.6.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.0.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

    kapt("org.springframework.boot:spring-boot-configuration-processor:2.5.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all-compatibility")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
