package com.medilabo.patient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medilabo.patient.service.PatientService;

@Controller
@RequestMapping(" ")
public class PatientController {
    //injection d'un service
    private final PatientService patientService;

    //===========
    //constructeur
    //===========
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("models", patientService.findAll());
        return "patientModel/list";
    }

}