package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.model.DiaryEntry;
import ch.ffhs.savvy_spring.service.DiaryService;
import ch.ffhs.savvy_spring.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "diaryentry", layout = MainLayout.class)
@PermitAll
public class DiaryEntryView extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    private DiaryService diaryService;

    private DiaryEntry diaryEntry;
    private Long parameter;

    private Div displayedContent;

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        this.parameter = parameter;
        this.diaryEntry = diaryService.getDiaryEntry(parameter);

        if (diaryEntry == null) {
            // Handle case where diary entry with given ID doesn't exist
            H2 error = new H2("is Null");
            add(error);
            return;
        }

        displayedContent = new Div(diaryEntry.getContent());

        Button editButton = new Button("Edit");
        editButton.addClickListener(e -> openEdit(diaryEntry));

        HorizontalLayout actionButtonsLayout = new HorizontalLayout(editButton);
        actionButtonsLayout.setPadding(true);
        actionButtonsLayout.setAlignItems(Alignment.BASELINE);
        actionButtonsLayout.getStyle().set("display", "flex");
        actionButtonsLayout.getStyle().set("justify-content", "center");
        actionButtonsLayout.getStyle().set("flex-grow", "1");

        Button backButton = new Button("Back");
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(DiaryView.class)));
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout backbuttonContainer = new HorizontalLayout(backButton, actionButtonsLayout);
        backbuttonContainer.getStyle().set("display", "flex");
        backbuttonContainer.getStyle().set("width", "100%");

        H2 pageTitle = new H2(getDiaryEntryTitle());
        pageTitle.getStyle().set("margin", "auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width", "80vw");
        container.getStyle().set("margin", "auto");
        container.add(pageTitle, backbuttonContainer, displayedContent);

        add(container);
    }

    private String getDiaryEntryTitle() {
        return diaryEntry.getTitle();
    }

    private void openEditDialog(DiaryEntry diaryEntry) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Content");
        //editField.setValue(diaryEntry.getContent());
        Button saveButton = new Button("Save", event -> {
            diaryEntry.setContent(editField.getValue());
            diaryService.updateDiaryEntry(diaryEntry);
            // Optionally update any grids or UI components that display diary entries
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(editField));
        dialog.getFooter().add(cancelButton, saveButton);
        dialog.open();
    }

    private void openEdit(DiaryEntry entry){
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Content");
        //editField.setValue(diaryEntry.getContent());
        Button saveButton = new Button("Save", event -> {
            entry.setContent(editField.getValue());
            diaryService.updateDiaryEntry(entry);
            // Optionally update any grids or UI components that display diary entries
            updateContent();
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(editField));
        dialog.getFooter().add(cancelButton, saveButton);
        dialog.open();
    }

    private void updateContent(){
        this.diaryEntry = diaryService.getDiaryEntry(parameter);
        displayedContent.setText(diaryEntry.getContent());
    }
}
