package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class MainLayout extends AppLayout {

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

    public MainLayout() {
        Header header = new Header();
        header.setId("main-header");

        Div layout = new Div();
        layout.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.Horizontal.MEDIUM);

        H1 title = new H1("Savvy");
        title.setId("main-logo");
        title.getStyle().set("font-size","2rem");
        title.addClassNames(LumoUtility.Margin.Vertical.MEDIUM, LumoUtility.Margin.End.AUTO, LumoUtility.FontSize.LARGE);
        title.addClickListener(
                e -> getUI().ifPresent(ui -> ui.navigate("/todolists"))
        );
        layout.add(title);

        Nav nav = new Nav();
        nav.setId("main-nav");
        nav.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Overflow.AUTO, LumoUtility.Padding.Horizontal.MEDIUM);

        UnorderedList list = new UnorderedList();
        list.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE);
        list.setId("nav-list");
        nav.add(list);

        RouterLink todoLink = new RouterLink("Todo", TodoListOverview.class);
        todoLink.addClassNames(LumoUtility.Margin.Horizontal.LARGE);
        todoLink.addClassName("nav-link");
        RouterLink diaryLink = new RouterLink("Diary", DiaryView.class);
        diaryLink.addClassNames(LumoUtility.Margin.Horizontal.LARGE);
        diaryLink.addClassName("nav-link");
        list.add(todoLink, diaryLink);

        Button logoutButton = getLogoutButton();
        logoutButton.getStyle().set("margin-right","1rem");

        VerticalLayout userProfile = new VerticalLayout();
        userProfile.setId("user-profile");
        Div userInfoTitle = new Div("User Profile");
        userInfoTitle.setId("user-info-title");
        Div userInfo = new Div();
        userInfo.setId("user-info");
        userInfo.setText(getUserInfo());
        userProfile.add(userInfoTitle, userInfo);

        header.add(layout, nav, userProfile, logoutButton);
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
        logoutButton.setId("logout-button");
        return logoutButton;
    }

    private String getUserInfo(){
        return principal.getAttribute("email");
    }
}
