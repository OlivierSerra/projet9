package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import com.medilabo.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class NoteController {

    private final NoteService service;
    private final NoteRepository repo;

    public NoteController(NoteService service, NoteRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    // ==========================
    //           PARTIE VUES (UI)
    // ==========================

    // Formulaire "Ajouter"
    @GetMapping("/notes/ui/ajouter")
    public String afficherAjouterPage(Model model) {
        model.addAttribute("note", new Note());
        return "note-ajouter";
    }

    @PostMapping("/notes/ui/ajouter")
    public String traiterAjouter(@ModelAttribute("note") Note note, Model model) {
        Note created = service.addNote(note);
        model.addAttribute("noteCreee", created);
        model.addAttribute("message", "Note ajoutée avec succès !");
        return "note-ajouter";
    }

    // Formulaire "Modifier"
    @GetMapping("/notes/ui/modifier")
    public String afficherModifierPage(Model model) {
        model.addAttribute("note", new Note());
        return "note-modifier";
    }

    @PostMapping("/notes/ui/modifier")
    public String traiterModifier(@RequestParam("id") String id,
                                  @ModelAttribute("note") Note note,
                                  Model model) {
        Note updated = service.updateNote(id, note);
        model.addAttribute("noteModifiee", updated);
        model.addAttribute("message", "Note modifiée avec succès !");
        return "note-modifier";
    }

    // Formulaire "Supprimer"
    @GetMapping("/notes/ui/supprimer")
    public String afficherSupprimerPage() {
        return "note-supprimer";
    }

    @PostMapping("notes/ui/supprimer")
    public String traiterSupprimer(@RequestParam("id") String id, Model model) {
        try {
            service.deleteNote(id);
            model.addAttribute("message", "Note supprimée avec succès !");
        } catch (RuntimeException e) {
            model.addAttribute("message", "Erreur : " + e.getMessage());
        }
        model.addAttribute("idSupprime", id);
        return "note-supprimer";
    }

    // =========================
    //  PARTIE REST (API)
    // ==========================

    @GetMapping("/notes")
    @ResponseBody
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = service.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/notes/{patientId}")
    @ResponseBody
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long patientId) {
        List<Note> notes = service.getNotes(patientId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/notes")
    @ResponseBody
    public ResponseEntity<Note> addNoteRest(@RequestBody Note note) {
        Note created = service.addNote(note);
        URI location = URI.create("/notes/id/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
// sert à alimenter la BDD qui sera interroger par les autre services ensites
    @PostMapping("/ui/ajouter")
    public String addNotesForm(
            @RequestParam Long patientId,
            @RequestParam String patient,
            @RequestParam String note,
            Model model) {

        Note n = new Note(patientId, patient, note);
        Note saved = service.addNote(n);

        model.addAttribute("note", saved);
        return "note-ajouter";
    }


    @PutMapping("/notes/{id}")
    @ResponseBody
    public ResponseEntity<Note> updateNoteRest(@PathVariable String id, @RequestBody Note note) {
        Note updated = service.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/notes/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteNoteRest(@PathVariable String id) {
        service.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
