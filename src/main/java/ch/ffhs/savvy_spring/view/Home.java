package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class Home extends Div {
    public Home(){
        Button login = new Button("Login");
        add(new Div("This is gonna be the home page where the user can log in"), login);
    }
}
