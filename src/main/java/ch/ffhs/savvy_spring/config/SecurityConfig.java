package ch.ffhs.savvy_spring.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SuppressWarnings("removal")
@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.oauth2Login().loginPage(LOGIN_URL).defaultSuccessUrl("/todolists", true).permitAll();
        http.logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

}
