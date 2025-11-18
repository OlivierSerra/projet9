package com.medilabo.patient.repository;

import com.medilabo.patient.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientModel, String> {

}

