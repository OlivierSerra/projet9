package com.medilabo.patient.repository;

import com.medilabo.patient.model.PatientModel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<PatientModel, Integer> {
    //List<PatientModel> findAll();
    //Optional<PatientModel> findById(Integer Id);
    //PatientModel save(@Valid PatientModel patient);
    //PatientModel update(Integer Id, PatientModel patient);
    //void deleteById(Integer id);
}

