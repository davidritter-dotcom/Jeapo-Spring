package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.model.DiaryEntry;
import ch.ffhs.savvy_spring.view.DiaryEntryView;
import ch.ffhs.savvy_spring.service.DiaryService;
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
    private DiaryService diaryService;
    private final Grid<DiaryEntry> grid = new Grid<>(DiaryEntry.class);

    private final TextField titleField = new TextField("Title");
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

    public DiaryView(DiaryService diaryService) {
        this.diaryService = diaryService;

        Button addButton = new Button("Add Diary Entry");
        addButton.addClickListener(e -> {
            DiaryEntry diary = new DiaryEntry();
            diary.setTitle(titleField.getValue());
            diary.setUser_id(principal.getName());
            System.out.println(principal.getName());
            diary.setContent("");
            diaryService.createDiaryEntry(diary);
            updateGrid();
            titleField.clear();
        });

        grid.addClassName("grid");
        grid.setColumns("title");
        grid.addComponentColumn(diary -> {
            Button editButton = new Button("Change Title");
            editButton.addClickListener(e -> openEditDialog(diary));
            return editButton;
        }).setWidth("13rem").setFlexGrow(0).setHeader("Change Title");

        grid.addComponentColumn(diaryEntry -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                diaryService.deleteDiaryEntry(diaryEntry.getEntryId());
                updateGrid();
            });
            return deleteButton;
        }).setWidth("10rem").setFlexGrow(0).setHeader("Delete");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        HorizontalLayout addDiaryEntryLayout = new HorizontalLayout();
        addDiaryEntryLayout.setPadding(true);
        addDiaryEntryLayout.setClassName("add-bar");
        addDiaryEntryLayout.add(titleField, addButton);

        H2 pageTitle = new H2("Diary");
        pageTitle.getStyle().set("margin", "auto");

        VerticalLayout container = new VerticalLayout();
        container.setClassName("main-content-container");
        container.add(pageTitle, addDiaryEntryLayout, grid);

        grid.addItemClickListener(event -> getUI().ifPresent(ui -> ui.navigate(DiaryEntryView.class, event.getItem().getEntryId())));

        add(container);
        updateGrid();
    }

    private void updateGrid() {
        DiaryEntry[] diaryEntries = diaryService.getDiaryEntries(principal.getName());
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
            diaryService.updateDiaryEntry(diaryEntry);
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
