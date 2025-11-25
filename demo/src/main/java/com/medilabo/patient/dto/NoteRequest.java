package com.medilabo.patient.dto;

import org.springframework.stereotype.Component;

//sert à importer (ou lier) le contenu de la note dans le microservice patient

public class NoteRequest {
    private Integer patientId;
    private String content;


    public NoteRequest() {}

    //
    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
