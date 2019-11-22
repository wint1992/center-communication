package ru.ithex.center.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"ru.ithex.center.communication", "ru.ithex.center.communication.emailsender", "ru.ithex.baseweb"})
@EntityScan({"ru.ithex.center.communication.emailsender"})
@EnableJpaRepositories({"ru.ithex.center.communication.emailsender"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
