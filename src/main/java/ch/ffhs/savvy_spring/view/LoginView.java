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

    private static final String AUTH0_OAUTH_URL = "/oauth2/authorization/okta";

    public LoginView() {

        Anchor auth0LoginLink = new Anchor(AUTH0_OAUTH_URL, "Login with Auth0");
        auth0LoginLink.setId("auth0-login-link-lv");

        // Instruct Vaadin Router to ignore doing SPA handling
        auth0LoginLink.setRouterIgnore(true);

        H1 logo = new H1("Savvy");
        logo.setId("logo-lv");

        H2 slogan = new H2("Manage your tasks and cherish your memories with Savvy");
        slogan.setId("slogan-lv");

        Div div = new Div();
        div.setId("content-lv");
        div.add(logo, slogan, auth0LoginLink);

        Div container = new Div();
        container.setId("container-lv");
        container.add(div);

        add(container);
    }
}
