package ch.ffhs.savvy_spring.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

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
    }
}
