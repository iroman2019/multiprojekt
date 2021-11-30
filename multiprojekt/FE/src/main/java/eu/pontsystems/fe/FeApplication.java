package eu.pontsystems.fe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@SpringBootApplication(scanBasePackages = "eu.pontsystems")
@Slf4j
public class FeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeApplication.class, args);
        log.info("Spring Boot and Kafka application is started successfully.");
    }

}
