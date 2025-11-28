package com.medilabo.patient.model;


import com.medilabo.patient.dto.NoteDto;
import com.medilabo.patient.dto.NoteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class NoteClient {

    private final RestTemplate restTemplate;
    //RestTRamplates pour appeler le microservice "notes"
    // http://localhost:8081 POUR LES PATIENTS
    //notes.service.url = http://localhost:8082 pour les notes
    //utile dans Docker pour perter l'application -/ pas de code den dure
    private final String noteServiceBaseUrl;

    public NoteClient(RestTemplate restTemplate,
                      //intéreêt pour Docker notes.service.url dans application.properties donc facile à cjanger pour le déploiement
                      @Value("${notes.service.url:http://localhost:8082}") String noteServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.noteServiceBaseUrl = noteServiceBaseUrl;
    }

    public List<NoteDto> getNotesForPatient(String patientId) {
        String url = noteServiceBaseUrl + "/notes/patient/" + patientId;
        NoteDto[] response = restTemplate.getForObject(url, NoteDto[].class);
        return response != null ? Arrays.asList(response) : List.of();
    }

    public NoteDto addNote(String patientId, String content) {
        NoteRequest request = new NoteRequest();
        request.setPatientId(patientId);
        request.setContent(content);

        String url = noteServiceBaseUrl + "/notes";
        return restTemplate.postForObject(url, request, NoteDto.class);
    }
}