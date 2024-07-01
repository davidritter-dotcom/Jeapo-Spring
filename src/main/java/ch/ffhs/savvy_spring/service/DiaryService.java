package ch.ffhs.savvy_spring.service;

import ch.ffhs.savvy_spring.model.DiaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class DiaryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${BASE_URL_SAVVY_JAKARTA}")
    private String BASE_URL;

    public DiaryEntry[] getDiaryEntries(String userId) {
        String url = BASE_URL + "user/" + userId;
        return restTemplate.getForObject(url, DiaryEntry[].class);
    }

    public DiaryEntry getDiaryEntry(Long entryId) {
        String url = BASE_URL + entryId;
        return restTemplate.getForObject(url, DiaryEntry.class);
    }

    public void createDiaryEntry(DiaryEntry diaryEntry) {
        String url = BASE_URL + "create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);
        restTemplate.postForEntity(url, request, DiaryEntry.class);
    }

    public void updateDiaryEntry(DiaryEntry diaryEntry) {
        String url = BASE_URL + diaryEntry.getEntryId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);
        restTemplate.put(url, request);
    }

    public void deleteDiaryEntry(Long entryId) {
        String url = BASE_URL + entryId;
        System.out.println("Deleting diary entry with URL: " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
}
