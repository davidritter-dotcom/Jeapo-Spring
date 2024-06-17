package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todo;
import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todolist;
import ch.ffhs.savvy_spring.service.TodoListService;
import ch.ffhs.savvy_spring.service.TodoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "todolist", layout = MainLayout.class)
@PermitAll
public class TodoListView extends VerticalLayout implements HasUrlParameter<Integer> {

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoListService todoListService;

    private Todolist todolist;
    private Grid<Todo> grid = new Grid<>(Todo.class);
    private TextField contentField = new TextField("Content");
    private Button addButton = new Button("Add Todo");
    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        this.todolist = todoListService.getById(parameter);
        addButton.addClickListener(e -> {
            Todo todo = new Todo();
            todo.setContent(contentField.getValue());
            todo.setTodolistId(todolist.getId());
            todoService.create(todo);
            updateGrid();
        });

        grid.setColumns("content");
        grid.addComponentColumn(todo -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(
                e -> openEditDialog(todo)
            );
            return editButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Edit");

        grid.addComponentColumn(todo -> {
            Button deleteButton = new Button("Done");
            deleteButton.addClickListener(e -> {
                todoService.delete(todo.getId());
                updateGrid();
            });
            return deleteButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Delete");

        //add(new HorizontalLayout(contentField, addButton), grid);

        HorizontalLayout addTodoLayout = new HorizontalLayout();
        addTodoLayout.setPadding(true);
        addTodoLayout.setAlignItems(Alignment.BASELINE);
        addTodoLayout.getStyle().set("display","flex");
        addTodoLayout.getStyle().set("justify-content","center");
        addTodoLayout.getStyle().set("flex-grow","1");
        addTodoLayout.add(contentField, addButton);

        Button backButton = new Button("Back");
        backButton.addClickListener(
                e -> getUI().ifPresent(ui -> ui.navigate(TodoListOverview.class))
        );
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout backbuttonContainer = new HorizontalLayout(backButton, addTodoLayout);
        backbuttonContainer.getStyle().set("display","flex");
        backbuttonContainer.getStyle().set("width","100%");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        H2 pageTitle = new H2(getTodoListTitle());
        pageTitle.getStyle().set("margin","auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width","80vw");
        container.getStyle().set("margin","auto");
        container.add(pageTitle, backbuttonContainer, grid);

        add(container);
        updateGrid();
    }

    private String getTodoListTitle() {
        return todolist.getName();
    }

    private void updateGrid() {
        grid.setItems(todoService.getByTodolistId(todolist.getId()));
    }

    private void openEditDialog(Todo todo) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Name");
        editField.setValue(todo.getContent());
        Button saveButton = new Button("Save", event -> {
            todo.setContent(editField.getValue());
            todoService.update(todo.getId(), todo);
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
