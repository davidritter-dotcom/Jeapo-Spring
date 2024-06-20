package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    // URL that Spring Security uses to connect to Google services
    private static final String OAUTH_URL = "/oauth2/authorization/google";

    public LoginView() {
        Anchor loginLink = new Anchor(OAUTH_URL, "Login with Google");
        loginLink.setId("login-link-lv");

        // Instruct Vaadin Router to ignore doing SPA handling
        loginLink.setRouterIgnore(true);

        H1 logo = new H1("Savvy");
        logo.setId("logo-lv");

        H2 slogan = new H2("Manage your tasks and cherish your memories with Savvy");
        slogan.setId("slogan-lv");

        Div div = new Div();
        div.setId("content-lv");
        div.add(logo, slogan, loginLink);

        Div container = new Div();
        container.setId("container-lv");
        container.add(div);

        add(container);
    }
}
