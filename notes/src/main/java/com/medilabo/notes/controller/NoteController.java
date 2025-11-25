package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import com.medilabo.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService service;
    private final NoteRepository repo;

    //==========
    //construsteur
    //================
    public NoteController(NoteService service, NoteRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    //======
    //endpoints
    //==========
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = service.getAllNotes();
        return ResponseEntity.ok(notes);
    }


    @GetMapping("/{patientId}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Integer patientId) {
        List<Note> notes = service.getNotes(patientId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note created = service.addNote(note);
        // URI de la ressource créée : /notes/id/{id}
        URI location = URI.create("/notes/id/" + created.getId());
        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        Note updated = service.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable String id) {
        service.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}

    /*
    @PostMapping("/{id}")
    public String updateNotes(@PathVariable("id") String id, @Valid Note note,
                            BindingResult result, Model model) {
        if (result.hasErrors()) return "note/update";
        Note existing = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found: " + id));
        existing.setPatientId(note.getPatientId());
        existing.setPatient(note.getPatient());
        existing.setNote(note.getNote());
        service.save(existing);
        return "redirect:/notes/list";
    }
*/


/*
//Juste pour tester
    @GetMapping
    public List<Note> getAllNotes() {
        try {
            return repo.findAll();
        } catch (Exception e) {

            return List.of();
        }
    }

    @GetMapping("/{patientId}")
    public List<Note> getNotes(Integer patientId) {
        try {
            return repo.findByPatientId(patientId);
        } catch (Exception e) {
            return List.of();
        }
    }

    @GetMapping
    public Note addNote(Note note) {
        try {
            return repo.save(note);
        } catch (Exception e) {
            return note;
        }
    }
*/
