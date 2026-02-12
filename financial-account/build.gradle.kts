plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("jacoco")
}

group = "br.com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring MVC (bloqueante)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring JDBC (bloqueante)
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    // Flyway para migrations
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    // MySQL JDBC driver
    runtimeOnly("com.mysql:mysql-connector-j")

    // Validação
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DevTools for hot reload in dev container
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Testes
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = true
        html.required = true
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco/html")
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

springBoot {
    mainClass = "br.com.mindcash.financial.FinancialApplication"
}

