package nl.hu.inno.hulp.grading;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GradingApplication.class, args);
    }

}
