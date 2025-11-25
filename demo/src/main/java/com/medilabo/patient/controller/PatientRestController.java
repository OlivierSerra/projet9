package com.medilabo.patient.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import com.medilabo.patient.model.NoteClient;
import com.medilabo.patient.dto.NoteRequest;
import com.medilabo.patient.model.PatientModel;

import com.medilabo.patient.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientRestController {

    private final PatientService patientService;
    private final NoteClient noteClient;

    public PatientRestController(PatientService patientService, NoteClient noteClient) {
        this.patientService = patientService;
        this.noteClient = noteClient;
    }

    // =======================
    // LISTE DES PATIENTS
    // =====================
    @GetMapping
    public List<PatientModel> getAllPatients() {
        return patientService.findAll();
    }

    // =======================
    // DÉTAIL D'UN PATIENT
    // =======================
    @GetMapping("/{id}")
    public ResponseEntity<PatientModel> getPatientById(@PathVariable Integer id) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    // =======================
    // CRÉER UN PATIENT
    // =======================
    @PostMapping
    public ResponseEntity<PatientModel> createPatient(@RequestBody PatientModel patient) {
        PatientModel saved = patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // =======================
    // METTRE À JOUR UN PATIENT
    // =======================
    @PutMapping("/{id}")
    public ResponseEntity<PatientModel> updatePatient(@PathVariable Integer id,
                                                      @RequestBody PatientModel updated) {

        PatientModel existing = patientService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        updated.setId(id);
        PatientModel saved = patientService.save(updated);
        return ResponseEntity.ok(saved);
    }

    // =======================
    // SUPPRIMER UN PATIENT
    // =======================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
        PatientModel existing = patientService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =======================
    // NOTES D'UN PATIENT
    // =======================
    @GetMapping("/{id}/notes")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Integer id) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        List<Note> notes = noteClient.getNotesForPatient(id);
        return ResponseEntity.ok(notes);
    }

    // =======================
    // AJOUTER UNE NOTE
    // =======================
    @PostMapping("/{id}/notes")
    public ResponseEntity<Note> addNote(@PathVariable Integer id,
                                               @RequestBody NoteRequest dto) {

        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        Note savedNote = noteClient.addNote(id, dto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }
}
