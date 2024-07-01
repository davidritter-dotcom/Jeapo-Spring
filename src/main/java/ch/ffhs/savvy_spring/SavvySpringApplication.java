package ch.ffhs.savvy_spring;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme(value = "custom-theme")
@SpringBootApplication
public class SavvySpringApplication implements AppShellConfigurator {
	public static void main(String[] args) {
		SpringApplication.run(SavvySpringApplication.class, args);
	}
}
