package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes/ui")
public class NoteViewController {

    private final NoteService noteService;

    public NoteViewController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Liste des notes pour un patient
    @GetMapping("/patient/{patientId}")
    public String afficherNotesPatient(@PathVariable Long patientId, Model model) {
        List<Note> notes = noteService.getNotes(patientId);

        model.addAttribute("patientId", patientId);
        model.addAttribute("notes", notes);

        return "note-liste";
    }

    @GetMapping("/ajouter/{patientId}")
    public String afficherFormAjout(@PathVariable Long patientId, Model model) {
        Note note = new Note();
        note.setPatientId(patientId);          // très important

        model.addAttribute("patientId", patientId);
        model.addAttribute("note", note);
        return "note-ajouter";                 // => templates/note-ajouter.html
    }

    @PostMapping("/ajouter/{patientId}")
    public String traiterAjout(@PathVariable Long patientId,
                               @ModelAttribute("note") Note note) {

        note.setPatientId(patientId);
        noteService.save(note);

        return "redirect:/notes/ui/patient/" + patientId;
    }

    //modifier les notes
    @GetMapping("/modifier/{id}")
    public String afficherFormModifier(@PathVariable String id, Model model) {

        Note note = noteService.getNoteById(id); // méthode à avoir dans le service
        Long patientId = note.getPatientId();

        model.addAttribute("patientId", patientId);
        model.addAttribute("note", note);

        return "note-modifier";
    }

    @PostMapping("/modifier/{id}")
    public String traiterModification(@PathVariable String id,
                                      @ModelAttribute("note") Note note) {

        // on garde l’id de la note
        note.setId(id);

        // patientId arrive du formulaire (champ caché)
        Long patientId = note.getPatientId();

        noteService.save(note);

        return "redirect:/notes/ui/patient/" + patientId;
    }
}



