package com.medilabo.RiskService.client;

import com.medilabo.RiskService.dto.NoteDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class NoteClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://noteservice:8082/notes";

    public NoteClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<NoteDto> getNotesForPatient(Long patientId) {
        String url = BASE_URL + "/" + patientId;

        ResponseEntity<List<NoteDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<NoteDto>>() {}
                );

        return response.getBody();
    }
}
