package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.DiaryEntry;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

@Route(value = "diary", layout = MainLayout.class)
public class DiaryView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private String diaryApiUrl;

    private Grid<DiaryEntry> grid = new Grid<>(DiaryEntry.class);
    private TextField titleField = new TextField("Title");
    private TextField contentField = new TextField("Content");
    private Button addButton = new Button("Add Entry");


    public DiaryView() {
        addButton.addClickListener(e -> {
            String title = titleField.getValue();
            String content = contentField.getValue();
            createDiaryEntry(title, content);
        });

        HorizontalLayout addEntryLayout = new HorizontalLayout();
        addEntryLayout.setPadding(true);
        addEntryLayout.setAlignItems(Alignment.BASELINE);
        addEntryLayout.getStyle().set("display", "flex");
        addEntryLayout.getStyle().set("justify-content", "center");
        addEntryLayout.getStyle().set("flex-grow", "1");
        addEntryLayout.add(titleField, contentField, addButton);

        H2 pageTitle = new H2("Diary Entries");
        pageTitle.getStyle().set("margin", "auto");

        grid.addColumn(DiaryEntry::getTitle).setHeader("Title");
        grid.addColumn(DiaryEntry::getContent).setHeader("Content");

        // Edit column
        grid.addComponentColumn(diaryEntry -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(event -> editDiaryEntry(diaryEntry));
            return editButton;
        }).setHeader("Edit");

        // Delete column
        grid.addComponentColumn(diaryEntry -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(event -> deleteDiaryEntry(diaryEntry.getEntryId()));
            return deleteButton;
        }).setHeader("Delete");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width", "80vw");
        container.getStyle().set("margin", "auto");
        container.add(pageTitle, addEntryLayout, grid);

        add(container);

        // Initialize the grid with diary entries
        updateGrid();
    }

    private void updateGrid() {
        // Get OAuth2 user principal
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null) {
            String userId = principal.getName(); // Assuming userId is in string format
            String url = diaryApiUrl + "/diary/" + userId; // Assuming your endpoint structure
            DiaryEntry[] diaryEntries = restTemplate.getForObject(url, DiaryEntry[].class);
            if (diaryEntries != null) {
                grid.setItems(diaryEntries);
            }
        }
    }

    private void createDiaryEntry(String title, String content) {
        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setTitle(title);
        newEntry.setContent(content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DiaryEntry> request = new HttpEntity<>(newEntry, headers);

        String url = diaryApiUrl + "/diary/create"; // Assuming your endpoint structure
        restTemplate.postForEntity(url, request, DiaryEntry.class);

        // Update the grid after adding a new entry
        updateGrid();
    }

    private void editDiaryEntry(DiaryEntry diaryEntry) {
        Dialog editDialog = new Dialog();
        editDialog.setCloseOnOutsideClick(false);

        TextField editTitleField = new TextField("Edit Title");
        editTitleField.setValue(diaryEntry.getTitle());

        TextArea editContentField = new TextArea("Edit Content");
        editContentField.setValue(diaryEntry.getContent());

        Button saveButton = new Button("Save", event -> {
            diaryEntry.setTitle(editTitleField.getValue());
            diaryEntry.setContent(editContentField.getValue());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);

            String url = diaryApiUrl + "/diary/" + diaryEntry.getEntryId(); // Assuming your update endpoint
            restTemplate.put(url, request);

            updateGrid(); // Refresh grid after update

            editDialog.close();
        });

        Button cancelButton = new Button("Cancel", event -> editDialog.close());

        VerticalLayout editLayout = new VerticalLayout(editTitleField, editContentField);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        editLayout.add(buttonLayout);

        editDialog.add(editLayout);
        editDialog.open();
    }

    private void deleteDiaryEntry(Long id) {
        String url = diaryApiUrl + "/diary/" + id;
        restTemplate.delete(url);

        // Update the grid after deleting an entry
        updateGrid();
    }
}



/*
package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.DiaryEntry;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.client.RestTemplate;

@Route(value = "diary", layout = MainLayout.class)
public class DiaryView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private Grid<DiaryEntry> grid = new Grid<>(DiaryEntry.class);
    private TextField titleField = new TextField("Title");
    private TextField contentField = new TextField("Content");
    private Button addButton = new Button("Add Entry");

    public DiaryView() {
        addButton.addClickListener(e -> {
            String title = titleField.getValue();
            String content = contentField.getValue();
            createDiaryEntry(title, content);
        });

        HorizontalLayout addEntryLayout = new HorizontalLayout();
        addEntryLayout.setPadding(true);
        addEntryLayout.setAlignItems(Alignment.BASELINE);
        addEntryLayout.getStyle().set("display", "flex");
        addEntryLayout.getStyle().set("justify-content", "center");
        addEntryLayout.getStyle().set("flex-grow", "1");
        addEntryLayout.add(titleField, contentField, addButton);

        H2 pageTitle = new H2("Diary Entries");
        pageTitle.getStyle().set("margin", "auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width", "80vw");
        container.getStyle().set("margin", "auto");
        container.add(pageTitle, addEntryLayout, grid);

        add(container);
    }

    @PostConstruct
    private void init() {
        // Initialize the grid with diary entries
        updateGrid();
    }

    private void updateGrid() {
        Long user_id = 2L;
        String url = "http://localhost:8081/diary/nsa"; // Replace with your actual endpoint
        DiaryEntry[] diaryEntries = restTemplate.getForObject(url, DiaryEntry[].class);
        if (diaryEntries != null) {
            grid.setItems(diaryEntries);
            grid.removeAllColumns(); // Clear all existing columns
            // Add columns for title and content
            grid.addColumn(DiaryEntry::getTitle).setHeader("Title");
            grid.addColumn(DiaryEntry::getContent).setHeader("Content");

        }
    }

    private void createDiaryEntry(String title, String content) {
        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setTitle(title);
        newEntry.setContent(content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DiaryEntry> request = new HttpEntity<>(newEntry, headers);

        String url = "http://localhost:8081/diary/create"; // Replace with your actual endpoint
        restTemplate.postForEntity(url, request, DiaryEntry.class);

        // Update the grid after adding a new entry
        updateGrid();
    }
}

 */