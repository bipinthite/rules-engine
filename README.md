# rules-engine
Database-driven dynamic rules engine based on Drools

This service is a rule engine that uses [Drools](https://www.drools.org/) and loads the rules stored in the database. Normally, users of Drools include rule files in the code base. This has a downside of service to go through CI-CD pipelines and re-deployment when rules are updated.

## Requirements
- Java 17 or higher
- Docker

## Frameworks/libraries used
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org/)
- [Drools](https://www.drools.org/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Flyway](https://flywaydb.org/)
- [Retrofit 2](https://square.github.io/retrofit/)
- [PMD](https://pmd.github.io/)
- [Checkstyle](https://checkstyle.sourceforge.io/)
- [Spotbugs](https://spotbugs.github.io/)
- [JaCoCo](https://www.jacoco.org/index.html)
- [Logbook](https://github.com/zalando/logbook)
- [Testcontainers](https://java.testcontainers.org/)
