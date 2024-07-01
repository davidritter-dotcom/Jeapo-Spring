package ch.ffhs.savvy_spring.view;

import ch.ffhs.savvy_spring.model.DiaryEntry;
import ch.ffhs.savvy_spring.service.DiaryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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

        displayedContent = new Div(diaryEntry.getContent());
        displayedContent.addClassName("diary-content");

        Button editButton = new Button("Edit");
        editButton.addClickListener(e -> openEdit(diaryEntry));

        HorizontalLayout actionButtonsLayout = new HorizontalLayout(editButton);
        actionButtonsLayout.setId("action-buttons-layout");

        Button backButton = new Button("Back");
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(DiaryView.class)));
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout backbuttonContainer = new HorizontalLayout(backButton, actionButtonsLayout);
        backbuttonContainer.addClassName("backbutton-container");

        H2 pageTitle = new H2(getDiaryEntryTitle());
        pageTitle.getStyle().set("margin", "auto");

        VerticalLayout container = new VerticalLayout();
        container.setClassName("main-content-container");
        container.add(pageTitle, backbuttonContainer,actionButtonsLayout, displayedContent);

        add(container);
    }

    private String getDiaryEntryTitle() {
        return diaryEntry.getTitle();
    }

    private void openEdit(DiaryEntry entry){
        Dialog dialog = new Dialog();
        TextArea editField = new TextArea("Content");
        editField.addClassName("text-field");
        editField.setValue(entry.getContent());
        editField.setMinHeight("40vh");
        editField.setWidth("40vw");
        Button saveButton = new Button("Save", event -> {
            entry.setContent(editField.getValue());
            diaryService.updateDiaryEntry(entry);
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
