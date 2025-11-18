package com.medilabo.patient.controller;

import com.medilabo.patient.model.PatientModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.medilabo.patient.service.PatientService;

import java.time.LocalDate;

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
        model.addAttribute("PatientModel", patientService.findAll());
        return "patientModel/list";
    }
/*
    @GetMapping("/add")
    public String addPatient(Model model) {
        model.addAttribute("PatientModel", new PatientModel(
                String firstName,
                String lastName,
                LocalDate birthDate,
                String gender,
                String adress,
                String phoneNumber
                ));
        return "patientModel/add";
    }
*/

    @PostMapping("/validate")
    public String validate(@Valid PatientModel patient, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "BindingResult/add";
        }
        PatientService.save(patient);
        return"redirect:/patientModel/list";
    }

    @GetMapping("/update/{id}")
    public String modifyForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("patientModel", patientService.findById(id));
        return "patientModel/update";
    }


}