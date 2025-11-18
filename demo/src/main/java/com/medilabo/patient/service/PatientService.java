package com.medilabo.patient.service;

import com.medilabo.patient.controller.PatientController;
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
        this.patientRepository =patientRepository;
    }

    public List<PatientModel> findAll() {
        return patientRepository.findAll();
    }

    public static void save(@Valid PatientModel patient) {
    }

    public Object findById(Integer id) {
        return null;
    }
}
