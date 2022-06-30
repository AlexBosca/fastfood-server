package ro.alexprojects.demo.repository;

import org.testcontainers.containers.PostgreSQLContainer;

public class RepositoryTest {
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("fastfood_database_test")
            .withUsername("springuser")
            .withPassword("SpringAppPass");

    static {
        postgresSQLContainer.start();
    }
}
