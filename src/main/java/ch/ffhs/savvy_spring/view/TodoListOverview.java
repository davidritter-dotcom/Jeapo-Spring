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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

@Route(value = "todolists", layout = MainLayout.class)
@PageTitle("Todo Lists")
@PermitAll
public class TodoListOverview extends VerticalLayout {

    @Autowired
    private TodoListService todoListService;
    private final Grid<Todolist> grid = new Grid<>(Todolist.class);
    private final TextField nameField = new TextField("Name");
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

    public TodoListOverview(TodoListService todoListService) {
        this.todoListService = todoListService;

        nameField.setId("add-todo-list-input");
        Button addButton = new Button("Add Todo List");
        addButton.setId("add-todo-list-button");
        addButton.addClickListener(e -> {
            Todolist todolist = new Todolist();
            todolist.setName(nameField.getValue());
            todolist.setUserId(principal.getName());
            todoListService.create(todolist);
            updateGrid();
            nameField.clear();
        });

        grid.addClassName("grid");
        grid.setColumns("name");
        grid.addComponentColumn(todolist -> {
            Button editButton = new Button("Change Name");
            editButton.addClickListener(e -> openEditDialog(todolist));
            return editButton;
        }).setWidth("13rem").setFlexGrow(0).setHeader("Change Name");

        grid.addComponentColumn(todolist -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                todoListService.delete(todolist.getId());
                updateGrid();
            });
            return deleteButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Delete");

        grid.addItemClickListener(event -> getUI().ifPresent(ui -> ui.navigate(TodoListView.class, event.getItem().getId())));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        HorizontalLayout addTodoListLayout = new HorizontalLayout();
        addTodoListLayout.setPadding(true);
        addTodoListLayout.addClassName("add-bar");
        addTodoListLayout.add(nameField, addButton);

        H2 pageTitle = new H2("Todo Lists");
        pageTitle.getStyle().set("margin","auto");

        VerticalLayout container = new VerticalLayout();
        container.setClassName("main-content-container");
        Div gridContainer = new Div(grid);
        gridContainer.addClassName("grid-container");
        container.add(pageTitle, addTodoListLayout, gridContainer);

        add(container);
        updateGrid();
    }

    private void updateGrid() {
        grid.setItems(todoListService.getByUserId(principal.getName()));
    }

    private void openEditDialog(Todolist todolist) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Name");
        editField.setId("edit-input-field");
        editField.setValue(todolist.getName());
        Button saveButton = new Button("Save", event -> {
            todolist.setName(editField.getValue());
            todoListService.update(todolist.getId(), todolist);
            updateGrid();
            dialog.close();
        });
        saveButton.setId("edit-save-button");
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(editField));
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.open();
    }
}
