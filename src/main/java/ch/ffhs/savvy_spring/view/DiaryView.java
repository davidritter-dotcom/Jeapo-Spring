package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "diary", layout = MainLayout.class)
public class DiaryView extends VerticalLayout {

    public DiaryView() {
        add(new Div("Diary content goes here..."));
    }
}
