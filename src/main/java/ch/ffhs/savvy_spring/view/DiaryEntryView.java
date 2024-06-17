
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/*@Route(value = "diaryentry", layout = MainLayout.class)
@PermitAll
public class DiaryEntryView extends VerticalLayout implements HasUrlParameter<Integer> {

    @Autowired
    private DiaryService diaryService;
    private DiaryEntry diaryentry;

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        this.diaryentry = diaryService.getById(parameter);

        Div displayedContent = new Div(diaryentry.getContent());

        Button editButton = new Button("Edit");
        editButton.addClickListener(
                e -> openEditDialog(diaryentry)
        );

        HorizontalLayout addTodoLayout = new HorizontalLayout();
        addTodoLayout.setPadding(true);
        addTodoLayout.setAlignItems(Alignment.BASELINE);
        addTodoLayout.getStyle().set("display","flex");
        addTodoLayout.getStyle().set("justify-content","center");
        addTodoLayout.getStyle().set("flex-grow","1");
        addTodoLayout.add(editButton);

        Button backButton = new Button("Back");
        backButton.addClickListener(
                e -> getUI().ifPresent(ui -> ui.navigate(TodoListOverview.class))
        );
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout backbuttonContainer = new HorizontalLayout(backButton, addTodoLayout);
        backbuttonContainer.getStyle().set("display","flex");
        backbuttonContainer.getStyle().set("width","100%");

        H2 pageTitle = new H2(getDiaryEntryTitle());
        pageTitle.getStyle().set("margin","auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width","80vw");
        container.getStyle().set("margin","auto");
        container.add(pageTitle, backbuttonContainer, displayedContent);

        add(container);
    }

    private String getDiaryEntryTitle() {
        return diaryentry.getTitle();
    }

    private void openEditDialog(DiaryEntry diaryentry) {
        Dialog dialog = new Dialog();
        TextField editField = new TextField("Content");
        editField.setValue(diaryentry.getContent());
        Button saveButton = new Button("Save", event -> {
            diaryentry.setContent(editField.getValue());
            diaryService.update(diaryentry.getId(), diaryentry);
            updateGrid();
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(editField));
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.open();
    }


}*/
