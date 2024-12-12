package nl.hu.inno.hulp.grading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class GradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(GradingApplication.class, args);
    }
}