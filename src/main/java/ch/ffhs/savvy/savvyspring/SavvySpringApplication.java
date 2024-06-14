package ch.ffhs.savvy.savvyspring;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin(value = "ch.ffhs.savvy.savvyspring.view")
public class SavvySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavvySpringApplication.class, args);
	}

}
