package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    // URL that Spring Security uses to connect to Google services
    private static final String OAUTH_URL = "/oauth2/authorization/google";

    public LoginView() {
        Anchor loginLink = new Anchor(OAUTH_URL, "Login with Google");
        loginLink.getStyle().set("text-decoration","none");
        loginLink.getStyle().set("font-size","3rem");
        loginLink.getStyle().set("color","black");
        loginLink.getStyle().set("font-weight","500");

        // Instruct Vaadin Router to ignore doing SPA handling
        loginLink.setRouterIgnore(true);

        H1 logo = new H1("Savvy");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(logo, loginLink);

        Div container = new Div();
        container.getStyle().set("width","100vw");
        container.getStyle().set("height","100vh");
        container.getStyle().set("display","flex");
        container.getStyle().set("justify-content","center");
        container.getStyle().set("align-items","center");
        container.add(verticalLayout);

        add(container);
    }
}
