package ch.ffhs.savvy_spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Route(value = "diary", layout = MainLayout.class)
public class DiaryView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private TextField titleField = new TextField("Title");
    private TextField contentField = new TextField("Content");
    private Button addButton = new Button("Add Entry");

    public DiaryView() {
        addButton.addClickListener(e -> {
            String title = titleField.getValue();
            String content = contentField.getValue();
            try {
                createDiaryEntry(title, content);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        });

        HorizontalLayout addEntryLayout = new HorizontalLayout();
        addEntryLayout.setPadding(true);
        addEntryLayout.setAlignItems(Alignment.BASELINE);
        addEntryLayout.getStyle().set("display", "flex");
        addEntryLayout.getStyle().set("justify-content", "center");
        addEntryLayout.getStyle().set("flex-grow", "1");
        addEntryLayout.add(titleField, contentField, addButton);

        H2 pageTitle = new H2("Create a new Diary Entry");
        pageTitle.getStyle().set("margin", "auto");

        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("width", "80vw");
        container.getStyle().set("margin", "auto");

        add(container);
    }

    private void createDiaryEntry(String title, String content) throws JSONException {
        JSONObject diaryEntryJson = new JSONObject();
        diaryEntryJson.put("title", title);
        diaryEntryJson.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(diaryEntryJson.toString(), headers);

        String url = "http://localhost:8081/diary/create";
        restTemplate.postForEntity(url, request, String.class);
    }
}
