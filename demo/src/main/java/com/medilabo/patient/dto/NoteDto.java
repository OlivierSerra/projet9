package com.medilabo.patient.dto;

import java.time.LocalDateTime;

//envoi de data vers le user
public class NoteDto {

    private String id;
    private String patientId;
    private String patient;
    private String note;          // le texte de la note


    public NoteDto() {}

    //getters & setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

}
