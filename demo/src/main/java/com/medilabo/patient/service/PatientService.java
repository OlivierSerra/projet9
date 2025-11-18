package com.medilabo.patient.service;

import com.medilabo.patient.controller.PatientController;
import com.medilabo.patient.model.PatientModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    //injection d'un répository
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository =patientRepository;
    }

    public List<PatientModel> findAll() {
        return patientRepository.findAll();
    }
}
