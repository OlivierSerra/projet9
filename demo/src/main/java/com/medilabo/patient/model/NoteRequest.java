package com.medilabo.patient.model;

import org.springframework.web.bind.annotation.RequestBody;


public class NoteRequest {

    private String content;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
