package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "diary", layout = MainLayout.class)
@PermitAll
public class DiaryView extends VerticalLayout {

    public DiaryView() {
        add(new Div("Diary content goes here..."));
    }
}
