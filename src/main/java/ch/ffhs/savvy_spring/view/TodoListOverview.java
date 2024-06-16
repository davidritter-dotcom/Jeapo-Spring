package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todolist;
import ch.ffhs.savvy_spring.service.TodoListService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "todolists", layout = MainLayout.class)
public class TodoListOverview extends VerticalLayout {

    @Autowired
    private TodoListService todoListService;

    private Grid<Todolist> grid = new Grid<>(Todolist.class);
    private TextField nameField = new TextField("Name");
    private Button addButton = new Button("Add Todo List");

    public TodoListOverview(TodoListService todoListService) {
        this.todoListService = todoListService;

        addButton.addClickListener(e -> {
            Todolist todolist = new Todolist();
            todolist.setName(nameField.getValue());
            //todo add real user id
            todolist.setUserId(1);
            todoListService.create(todolist);
            updateGrid();
            nameField.clear();
        });

        grid.setColumns("name");
        grid.addComponentColumn(todolist -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> openEditDialog(todolist));
            return editButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Change Name");

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
        addTodoListLayout.setAlignItems(Alignment.BASELINE);
        addTodoListLayout.getStyle().set("display","flex");
        addTodoListLayout.getStyle().set("justify-content","center");
        addTodoListLayout.getStyle().set("width","100%");
        addTodoListLayout.add(nameField, addButton);

        H2 pageTitle = new H2("Todo Lists");
        pageTitle.getStyle().set("margin","auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width","80vw");
        container.getStyle().set("margin","auto");
        container.add(pageTitle, addTodoListLayout, grid);

        add(container);
        updateGrid();
    }

    private void updateGrid() {
        grid.setItems(todoListService.getAll());
    }

    private void openEditDialog(Todolist todolist) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Name");
        editField.setValue(todolist.getName());
        Button saveButton = new Button("Save", event -> {
            todolist.setName(editField.getValue());
            todoListService.update(todolist.getId(), todolist);
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
