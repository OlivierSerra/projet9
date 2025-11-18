package com.medilabo.patient.repository;

import com.medilabo.patient.model.PatientModel;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientModel, Integer> {
    //List<PatientModel> findAll();
    //Optional<PatientModel> findById(Integer Id);
    //PatientModel save(@Valid PatientModel patient);
    //PatientModel update(Integer Id, PatientModel patient);
    //void deleteById(Integer id);
}

