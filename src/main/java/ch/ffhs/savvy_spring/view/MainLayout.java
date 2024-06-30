package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

public class MainLayout extends AppLayout {

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    public MainLayout() {
        Header header = new Header();
        header.getStyle().set("display", "flex");
        header.getStyle().set("align-items", "center");
        header.getStyle().set("margin", "1rem");
        header.getStyle().set("width", "100vw");
        header.getStyle().set("box-sizing", "border");

        Div layout = new Div();
        layout.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.Horizontal.MEDIUM);

        H1 title = new H1("Savvy");
        title.getStyle().set("margin-left","1rem");
        title.addClassNames(LumoUtility.Margin.Vertical.MEDIUM, LumoUtility.Margin.End.AUTO, LumoUtility.FontSize.LARGE);
        title.addClickListener(
                e -> getUI().ifPresent(ui -> ui.navigate("/todolists"))
        );
        layout.add(title);

        Nav nav = new Nav();
        nav.getStyle().set("flex-grow", "1");
        nav.getStyle().set("display", "flex");
        nav.getStyle().set("justify-content", "center");
        nav.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Overflow.AUTO, LumoUtility.Padding.Horizontal.MEDIUM);

        UnorderedList list = new UnorderedList();
        list.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE);
        list.getStyle().set("display", "flex");
        list.getStyle().set("padding-left", "0");
        list.getStyle().set("gap", "6rem");
        nav.add(list);

        RouterLink todoLink = new RouterLink("Todo", TodoListOverview.class);
        todoLink.addClassNames(LumoUtility.Margin.Horizontal.LARGE);
        todoLink.getStyle().set("color","black");
        todoLink.getStyle().set("text-decoration","none");
        todoLink.getStyle().set("font-size","1.2rem");
        RouterLink diaryLink = new RouterLink("Diary", DiaryView.class);
        diaryLink.addClassNames(LumoUtility.Margin.Horizontal.LARGE);
        diaryLink.getStyle().set("color","black");
        diaryLink.getStyle().set("text-decoration","none");
        diaryLink.getStyle().set("font-size","1.2rem");
        list.add(todoLink, diaryLink);

        //Possible Optimization: Create a separate user Session Bean to handle logout
        Button logoutButton = getLogoutButton();
        logoutButton.getStyle().set("margin-right","1rem");

        header.add(layout, nav, logoutButton);
        addToNavbar(header);
    }

    private Button getLogoutButton() {
        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(
                e -> {
                    // Construct the base URL for redirection
                    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                    String redirectUrl = issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl;

                    // Perform logout using SecurityContextLogoutHandler
                    HttpServletRequest request = VaadinServletRequest.getCurrent().getHttpServletRequest();
                    HttpServletResponse response = VaadinServletResponse.getCurrent().getHttpServletResponse();

                    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
                    logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

                    getUI().ifPresent(ui -> ui.getPage().setLocation(redirectUrl));
                }
        );
        return logoutButton;
    }
}
