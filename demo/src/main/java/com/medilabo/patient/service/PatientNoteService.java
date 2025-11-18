package com.medilabo.patient.service;

import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.model.PatientNote;
import com.medilabo.patient.repository.PatientNoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientNoteService {

    private final PatientNoteRepository patientNoteRepository;

    public PatientNoteService(PatientNoteRepository patientNoteRepository) {
        this.patientNoteRepository = patientNoteRepository;
    }

    public List<PatientNote> getPrewiew(PatientModel patient) {
        return patientNoteRepository.findByPatient(patient);
    }

    public PatientNote addNote(PatientModel patient, String content, String author) {
        PatientNote note = new PatientNote();
        note.setPatient(patient);
        note.setContent(content);
        note.setAuthor(author);
        note.setCreatedAt(LocalDateTime.now());
        return patientNoteRepository.save(note);
    }

}
