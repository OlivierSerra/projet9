package com.medilabo.patient.repository;

import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.model.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Integer> {

    List<PatientNote> findByPatient(PatientModel patient);
}



