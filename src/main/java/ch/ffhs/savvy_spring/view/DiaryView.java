package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todolist;
import ch.ffhs.savvy_spring.service.TodoListService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

@Route(value = "diary", layout = MainLayout.class)
@PermitAll
public class DiaryView extends VerticalLayout {

    @Autowired
    private DiaryService diaryService;
    private final Grid<DiaryEntry> grid = new Grid<>(Diary.class);

    private final TextField titleField = new TextField("Title");

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
    public DiaryView(DiaryService diaryService) {
        this.diaryService = diaryService;

        //Todo: put this in separate class
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

        Button addButton = new Button("Add Todo List");
        addButton.addClickListener(e -> {
            Todolist todolist = new Todolist();
            todolist.setName(titleField.getValue());
            todolist.setUserId(principal.getName());
            diaryService.create(todolist);
            updateGrid();
            titleField.clear();
        });

        grid.setColumns("title");
        grid.addComponentColumn(diary -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> openEditDialog(diary));
            return editButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Change Name");

        grid.addComponentColumn(todolist -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                diaryService.delete(todolist.getId());
                updateGrid();
            });
            return deleteButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Delete");

        grid.addItemClickListener(event -> getUI().ifPresent(ui -> ui.navigate(DiaryEntryView.class, event.getItem().getId())));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        HorizontalLayout addTodoListLayout = new HorizontalLayout();
        addTodoListLayout.setPadding(true);
        addTodoListLayout.setAlignItems(Alignment.BASELINE);
        addTodoListLayout.getStyle().set("display","flex");
        addTodoListLayout.getStyle().set("justify-content","center");
        addTodoListLayout.getStyle().set("width","100%");
        addTodoListLayout.add(titleField, addButton);

        H2 pageTitle = new H2("Diary");
        pageTitle.getStyle().set("margin","auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width","80vw");
        container.getStyle().set("margin","auto");
        container.add(pageTitle, addTodoListLayout, grid);

        add(container);
        updateGrid();

    }

    private void updateGrid() {
        grid.setItems(diaryService.getByUserId(principal.getName()));
    }
    private void openEditDialog(DiaryEntry diaryEntry) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Title");
        editField.setValue(diaryEntry.getName());
        Button saveButton = new Button("Save", event -> {
            diaryEntry.setName(editField.getValue());
            diaryService.update(diaryEntry.getId(), diaryEntry);
            updateGrid();
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(editField));
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.open();
    }
}
