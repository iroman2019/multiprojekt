package eu.pontsystems.be;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "eu.pontsystems.be")
public class BeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeApplication.class, args);
        log.info("Spring Boot Cassandra modul is started successfully.");
    }

}
