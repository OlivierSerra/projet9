package com.medilabo.patient.service;


import com.medilabo.patient.dto.RiskReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiskClient {

    private final RestTemplate restTemplate;
    private final String riskServiceBaseUrl;

    public RiskClient(RestTemplate restTemplate,
                      @Value("${risk.service.url:http://localhost:8083}") String riskServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.riskServiceBaseUrl = riskServiceBaseUrl;
    }

    public RiskReportDto getRiskForPatient(Long patientId){
        String url = riskServiceBaseUrl + "/risk/" + patientId;
        return restTemplate.getForObject(url, RiskReportDto.class);
    }
}
