package com.medilabo.patient.service;

import com.medilabo.patient.controller.PatientRestController;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    //injection d'un répository
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientModel> findAll() {
        return patientRepository.findAll();
    }

    public PatientModel findById(Integer id) {
        return patientRepository.findById(id).orElse(null);
    }

    public PatientModel save(PatientModel patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }
}
