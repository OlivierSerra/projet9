package com.medilabo.patient.controller;

import com.medilabo.patient.service.RiskClient;
import com.medilabo.patient.dto.RiskReportDto;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients/ui")
public class PatientViewController {

    private final PatientService patientService;
    private final RiskClient riskClient;

    public PatientViewController(PatientService patientService, RiskClient riskClient) {
        this.patientService = patientService;
        this.riskClient = riskClient;
    }

    // =========================
    // LISTE
    // =========================
    @GetMapping("/liste")
    public String afficherListePatients(Model model) {
        List<PatientModel> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "patients-liste";
    }
    // =========================
    // AJOUTER
    // =========================
    @GetMapping("/ajouter")
    public String afficherFormAjout(Model model) {
        model.addAttribute("patient", new PatientModel());
        return "patient-ajouter";
    }

    @PostMapping("/ajouter")
    public String traiterAjout(@ModelAttribute("patient") PatientModel patient) {
        PatientModel saved = patientService.save(patient);
        return "redirect:/patients/ui/liste";
    }

    // =========================
    // MODIFIER
    // =========================
    @GetMapping("/modifier/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }
        model.addAttribute("patient", patient);
        return "patient-modifier";
    }

    @PostMapping("/modifier/{id}")
    public String traiterModification(@PathVariable Long id,
                                      @ModelAttribute("patient") PatientModel patient) {
        patient.setId(id);
        patientService.save(patient);
        return "redirect:/patients/ui/liste";
    }

    // =========================
    // SUPPRIMER
    // =========================
    @GetMapping("/supprimer/{id}")
    public String confirmerSuppression(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }
        model.addAttribute("patient", patient);
        return "patient-supprimer";
    }

    @PostMapping("/supprimer/{id}")
    public String traiterSuppression(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients/ui/liste";
    }

    //Afficher le détail des patients

    @GetMapping("/detail/{id}")
    public String afficherDetailPatient(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }

        RiskReportDto risk = riskClient.getRiskForPatient(id);

        model.addAttribute("patient", patient);
        model.addAttribute("risk", risk);

        return "patient-detail";
    }

}
