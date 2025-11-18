package com.medilabo.patient.controller;

import com.medilabo.patient.model.NoteRequest;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.model.PatientNote;
import com.medilabo.patient.service.PatientNoteService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.medilabo.patient.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("") // pas d'espace ici
public class PatientRestController {

    private final PatientService patientService;
    private final PatientNoteService patientNoteService;

    public PatientRestController(PatientService patientService, PatientNoteService patientNoteService) {
        this.patientService = patientService;
        this.patientNoteService = patientNoteService;
    }

    // Liste des patients
    @GetMapping
    public List<PatientModel> getAll() {
        return patientService.findAll();
    }

    // Détail d'un patient
    @GetMapping("/{id}")
    public PatientModel getById(@PathVariable Integer id) {
        return patientService.findById(id);
    }

    // Historique d’un patient
    @GetMapping("/{id}/notes")
    public List<PatientNote> getNotes(@PathVariable Integer id) {
        PatientModel patient = patientService.findById(id);
        return patientNoteService.getPrewiew(patient);
    }

    // Ajouter une note
    @PostMapping("/{id}/notes")
    public PatientNote addNote(@PathVariable Integer id,
                               @RequestBody NoteRequest dto) {

        PatientModel patient = patientService.findById(id);
        return patientNoteService.addNote(patient, dto.getContent(), "Praticien");
    }

    // =======================
    // FORMULAIRE D'AJOUT
    // =======================
    @GetMapping("/add")
    public String addPatient(Model model) {
        // On affiche juste un formulaire vide
        model.addAttribute("patientModel", new PatientModel());
        return "patientModel/add";
    }

    // =======================
    // VALIDATION FORMULAIRE
    // =======================
    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("patientModel") PatientModel patient,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            // on revient sur le formulaire d'ajout
            return "patientModel/add";
        }
        patientService.save(patient);
        // on revient à la liste
        return "redirect:/list";
    }

    // =======================
    // VUE HISTORIQUE PATIENT
    // =======================
    @GetMapping("/patient/{id}/history")
    public String viewHistory(@PathVariable("id") Integer id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/list";
        }

        List<PatientNote> notes = patientNoteService.getPrewiew(patient);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("newNoteContent", "");

        return "patientModel/history";
    }

    // =======================
    // AJOUTER À L'HISTORIQUE
    // =======================
    @PostMapping("/patient/{id}/history")
    public String addNote(@PathVariable("id") Integer id,
                          @RequestParam("content") String content) {

        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/list";
        }

        if (content == null || content.trim().isEmpty()) {
            // on ignore les notes vides pour l'instant
            return "redirect:/patient/" + id + "/history";
        }

        patientNoteService.addNote(patient, content, "Praticien");

        return "redirect:/patient/" + id + "/history";
    }

    // =======================
    // FORMULAIRE DE MODIF
    // =======================
    @GetMapping("/update/{id}")
    public String modifyForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("patientModel", patientService.findById(id));
        return "patientModel/update";
    }
}