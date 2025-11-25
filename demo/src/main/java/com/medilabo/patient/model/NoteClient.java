package com.medilabo.patient.model;

import com.medilabo.notes.model.Note;
import com.medilabo.patient.dto.NoteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class NoteClient {

    private final RestTemplate restTemplate;
    private final String noteServiceBaseUrl;

    public NoteClient(RestTemplate restTemplate,
                      @Value("${notes.service.url:http://localhost:8082}") String noteServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.noteServiceBaseUrl = noteServiceBaseUrl;
    }

    public List<Note> getNotesForPatient(Integer patientId) {
        String url = noteServiceBaseUrl + "/notes/patient/" + patientId;
        Note[] response = restTemplate.getForObject(url, Note[].class);
        return response != null ? Arrays.asList(response) : List.of();
    }

    public Note addNote(Integer patientId, String content) {
        NoteRequest request = new NoteRequest();
        request.setPatientId(patientId);
        request.setContent(content);

        String url = noteServiceBaseUrl + "/notes";
        return restTemplate.postForObject(url, request, Note.class);
    }
}