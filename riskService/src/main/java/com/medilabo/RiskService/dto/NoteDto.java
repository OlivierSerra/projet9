package com.medilabo.RiskService.dto;

public class NoteDto {
    private String id;
    private int patientId;     // comme dans notes-service
    private String patient;
    private String note;

    //getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
