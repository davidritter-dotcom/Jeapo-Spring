package ch.ffhs.savvy_spring.service;

import ch.ffhs.savvy_spring.model.DiaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
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

    public DiaryEntry[] getDiaryEntries(String userId) {
        String url = "http://localhost:8081/diary/user/" + userId;
        return restTemplate.getForObject(url, DiaryEntry[].class);
    }

    public DiaryEntry getDiaryEntry(Long entryid) {
        String url = "http://localhost:8081/diary/" + entryid;
        return restTemplate.getForObject(url, DiaryEntry.class);
    }

    public void createDiaryEntry(DiaryEntry diaryEntry) {
        String url = "http://localhost:8081/diary/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);
        restTemplate.postForEntity(url, request, DiaryEntry.class);
    }

    public void updateDiaryEntry(DiaryEntry diaryEntry) {
        String url = "http://localhost:8081/diary/" + diaryEntry.getEntryId();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DiaryEntry> request = new HttpEntity<>(diaryEntry, headers);
        restTemplate.put(url, request);
    }

    public void deleteDiaryEntry(Long entryId) {
        String url = "http://localhost:8081/diary/" + entryId;
        System.out.println("Deleting diary entry with URL: " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
}
