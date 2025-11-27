package com.medilabo.patient.service;

import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.repository.PatientRepository;
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

    public PatientModel findById(String id) {

        return patientRepository.findById(id).orElse(null);
    }

    public PatientModel findByNames(String lastName, String firstName) {
        return patientRepository
                .findByLastNameAndFirstName(lastName, firstName)
                .orElse(null);
    }

    public PatientModel save(PatientModel patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(String id) {

        patientRepository.deleteById(id);
    }
}
