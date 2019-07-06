package pl.sda.eventorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;


@SpringBootApplication()
@EnableJpaAuditing
public class EventOrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventOrganizerApplication.class, args);
	}



}
