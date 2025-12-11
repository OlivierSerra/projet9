package com.medilabo.RiskService.client;

import com.medilabo.RiskService.dto.PatientDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientClient {
    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://patientservice:8081/patients";

    public PatientClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PatientDto getPatientById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, PatientDto.class);
    }
}
