package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.model.DiaryEntry;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


@Route(value = "diary", layout = MainLayout.class)
@PermitAll
public class DiaryView extends VerticalLayout {

    @Autowired
    private RestTemplate diaryService;
    private final Grid<DiaryEntry> grid = new Grid<>(DiaryEntry.class);

    private final TextField titleField = new TextField("Title");

    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
    public DiaryView(RestTemplate diaryService) {
        this.diaryService = diaryService;

        //Todo: put this in separate class
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

        Button addButton = new Button("Add Diary Entry");
        addButton.addClickListener(e -> {
            DiaryEntry diary = new DiaryEntry();
            diary.setTitle(titleField.getValue());
            diary.setUser_id(principal.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String url = "http://localhost:8081/diary/create";

            HttpEntity<DiaryEntry> request = new HttpEntity<>(diary, headers);

            diaryService.postForEntity(url, request, DiaryEntry.class);
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
                Long id = todolist.getEntryId();
                String url = "http://localhost:8081/diary/" + id;
                diaryService.delete(url);
                updateGrid();
            });
            return deleteButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Delete");

        //grid.addItemClickListener(event -> getUI().ifPresent(ui -> ui.navigate(DiaryEntryView.class, event.getItem().getEntryId())));
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
        //updateGrid();

    }

    private void updateGrid() {
        String url = "http://localhost:8081" + "/diary/" + principal.getName(); // Assuming your endpoint structure
        DiaryEntry[] diaryEntries = diaryService.getForObject(url, DiaryEntry[].class);
        if (diaryEntries != null) {
            grid.setItems(diaryEntries);
        }
    }
    private void openEditDialog(DiaryEntry diaryEntry) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Title");
        editField.setValue(diaryEntry.getTitle());
        Button saveButton = new Button("Save", event -> {
            diaryEntry.setTitle(editField.getValue());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);

            String url = "localhost:8081" + "/diary/" + diaryEntry.getEntryId(); // Assuming your update endpoint
            diaryService.put(url, request);
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
