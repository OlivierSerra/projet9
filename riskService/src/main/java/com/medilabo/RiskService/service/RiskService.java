package com.medilabo.RiskService.service;

import com.medilabo.RiskService.client.NoteClient;
import com.medilabo.RiskService.client.PatientClient;
import com.medilabo.RiskService.dto.*;
import com.medilabo.RiskService.model.RiskLevel;
import com.medilabo.RiskService.dto.RiskReportDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



@Service
public class RiskService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final List<String> TRIGGERS = List.of(
            "HEMOGLOBINE A1C",
            "MICROALBUMINE",
            "TAILLE",
            "POIDS",
            "FUMEUR",
            "FUMEUSE",
            "ANORMAL",
            "CHOLESTEROL",
            "VERTIGES",
            "RECHUTE",
            "REACTION",
            "ANTICORPS"
    );

    public RiskService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    public RiskReportDto assessRisk(Long patientId) {

        PatientDto patient = patientClient.getPatientById(patientId);
        if (patient == null) {
            throw new RuntimeException("Patient not found with id " + patientId);
        }

        List<NoteDto> notes = noteClient.getNotesForPatient(patientId);
        if (notes == null) {
            notes = List.of();
        }

        int triggers = countTriggers(notes);
        RiskLevel level = calculateRiskLevel(patient, triggers);

        //Construction du DTO de réponse
        RiskReportDto dto = new RiskReportDto();
        dto.setPatientId(patient.getId());
        dto.setLastName(patient.getLastName());
        dto.setFirstName(patient.getFirstName());
        dto.setBirthDate(patient.getBirthDate());
        dto.setGender(patient.getGender());
        dto.setTriggerCount(triggers);
        dto.setRiskLevel(level);

        dto.setNotesExtracts(
                notes.stream()
                        .map(NoteDto::getNote)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    //definition de countTriggers
    private int countTriggers(List<NoteDto> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0;
        }
        String allNotes = notes.stream()
                .map(NoteDto::getNote)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));

        String normalizedNotes= normalisedText(allNotes);

        int count = 0;
        for (String trigger : TRIGGERS) {
            if (normalizedNotes.contains(trigger)) {
                count++;
            }
        }
        return count;
    }

    //normaliser l'enr des entrées afin de les comparer à la liste
    private String normalisedText( String text) {
        if (text == null){
            return" ";
        }
        return text.toUpperCase();
    }

    //calcul du niveau de risque en fonction de l'âge+genre+declencheurs
    private RiskLevel calculateRiskLevel(PatientDto patient, int triggerCount) {
        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }
        int age = calculateAge(patient.getBirthDate());
        String patientGender = patient.getGender();

        if (age > 30) {
            if (triggerCount >= 8) {
                return RiskLevel.EARLYONSET;
            } else if (triggerCount >= 6) {
                return RiskLevel.INDANGER;
            } else if (triggerCount >= 2 && triggerCount <= 5) {
                return RiskLevel.BORDERLINE;
            } else {
                return RiskLevel.NONE;
            }
        }

        if (age <30) {
            if ("M".equals(patientGender)) {
                if (triggerCount >= 5) {
                    return RiskLevel.EARLYONSET;
                } else if (triggerCount >= 3) {
                    return RiskLevel.INDANGER;
                } else {
                    return RiskLevel.NONE;
                }
            } else if ("F".equals(patientGender)){
                if (triggerCount >= 7) {
                    return RiskLevel.EARLYONSET;
                } else if (triggerCount >= 4) {
                    return RiskLevel.INDANGER;
                } else {
                    return RiskLevel.NONE;
                }
            } else {
                return RiskLevel.NONE;
            }
        }
        return RiskLevel.NONE;

    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

        /*Compter le nombre de déclencheurs trouvés.
        //Si count == 0 → None
        if (count == 0) {
            risk == null;
        }

        if (age > 30) {
            if (count > 2 && count < 5) {
                risk == Borderline
            } ;
            if (count > 6 && count < 7) {
                riske == In Danger
            } ;
            if (count > 8) {
                risk == Early onset
            } ;
        }

        if ((age < 30) && (gender == M)) {
            if (count >= 5) {
                risk == Early onset
            } ;
            if (count >= 3) {
                risk == In Danger
            } ;
            if (count > 6 && count < 7) {
                risk == In Danger
            } ;
            if (count > 8) {
                risk == Early onset
            } ;
        }

        if ((age < 30) && (gender == F)) {
            if (count >= 7) {
                risk == Early onset
            } ;
            if (count >= 4) {
                risk == In Danger
            } ;
        }
        elseIf risk = null;
    }
*/
