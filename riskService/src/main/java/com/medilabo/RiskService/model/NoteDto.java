package com.medilabo.RiskService.model;


public class NoteDto {

    private String id;
    private Integer patientId;
    private String content;

    //Constructeurs
    public NoteDto() {
        
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}