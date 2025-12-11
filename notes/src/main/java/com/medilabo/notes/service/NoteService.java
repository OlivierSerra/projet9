package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

//import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository repo;

    //constructeurs
    public NoteService(NoteRepository repo) {

        this.repo = repo;
    }

    public List<Note> getNotes(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    public Note addNote(Note note) {
        return repo.save(note);
    }

    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    //RuntimeException
    public void deleteNote(String id){
        if (!repo.existsById(id)) {
            throw new RuntimeException("Note not found with id " + id);
        }
        repo.deleteById(id);
    }

    public Note save(Note b){
        return repo.save(b);
    }

    public Note getNoteById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée : " + id));
    }

    public Note updateNote(String id, Note updated) {
        Note existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found: " + id));
        existing.setPatientId(updated.getPatientId());
        existing.setPatient(updated.getPatient());
        existing.setNote(updated.getNote());
        repo.save(existing);
        return existing;
    }

   /* return "redirect:/notes/list";
   */


}