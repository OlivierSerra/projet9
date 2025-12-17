package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    // GET /notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(service.getAllNotes());
    }

    // GET /notes/patient/{patientId}  (ou GET /notes/{patientId} si tu veux garder tel quel)
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getNotes(patientId));
    }

    // POST /notes
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note created = service.addNote(note);
        URI location = URI.create("/notes/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // PUT /notes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        Note updated = service.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }

    // DELETE /notes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        service.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
