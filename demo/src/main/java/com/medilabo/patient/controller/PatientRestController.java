package com.medilabo.patient.controller;

import com.medilabo.patient.dto.NoteDto;
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
    public ResponseEntity<PatientModel> getPatientById(@PathVariable String id) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    // ===========
    //trouver un patient gràce à son nom
    //=========
    @GetMapping("/search")
    public ResponseEntity<PatientModel> findByNames(@RequestParam String lastName,
                                    @RequestParam String firstName) {
        PatientModel foundedPatient= patientService.findByNames(lastName, firstName);
        if (foundedPatient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundedPatient);
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
    public ResponseEntity<PatientModel> updatePatient(@PathVariable String id,
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
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
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
    public ResponseEntity<List<NoteDto>> getNotes(@PathVariable String id) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        List<NoteDto> notes = noteClient.getNotesForPatient(id);
        return ResponseEntity.ok(notes);
    }

    // =======================
    // AJOUTER UNE NOTE
    // =======================
    @PostMapping("/{id}/notes")
    public ResponseEntity<NoteDto> addNote(@PathVariable String id,
                                        @RequestBody NoteRequest dto) {

        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        NoteDto savedNote = noteClient.addNote(id, dto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

}
