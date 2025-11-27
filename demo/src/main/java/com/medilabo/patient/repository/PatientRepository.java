package com.medilabo.patient.repository;

import com.medilabo.patient.model.PatientModel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<PatientModel, String> {
    Optional<PatientModel> findByLastNameAndFirstName(String lastName, String firstName);
}

