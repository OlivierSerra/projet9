package com.medilabo.RiskService.service;

import com.medilabo.RiskService.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


@Service
public class RiskService {

        private final RestTemplate restTemplate;
        private final String patientServiceUrl;
        private final String noteServiceUrl;

        // liste des termes déclencheurs en dur, pas de BDD
        //cherche mots dans chaîne de caractères => besoin d’une liste de chaînes non constantes.
        private static final List<String> TRIGGERS = List.of(
                "hémoglobine a1c", "microalbumine", "taille", "poids",
                "fumeur", "fumeuse", "anormal", "cholestérol",
                "vertiges", "rechute", "réaction", "anticorps"
        );

        public RiskService(RestTemplate restTemplate,
                                     @Value("${patients.service.url}") String patientServiceUrl,
                                     @Value("${notes.service.url}") String noteServiceUrl) {
            this.restTemplate = restTemplate;
            this.patientServiceUrl = patientServiceUrl;
            this.noteServiceUrl = noteServiceUrl;
        }

        public RiskResult assess(String patientId) {

            // 1) récupérer le patient
            String patientUrl = patientServiceUrl + "/patients/" + patientId;
            PatientDto patient = restTemplate.getForObject(patientUrl, PatientDto.class);
            if (patient == null) {
                return null;
            }

            // 2) récupérer les notes du patient
            String notesUrl = noteServiceUrl + "/notes/" + patientId;
            NoteDto[] notesArray = restTemplate.getForObject(notesUrl, NoteDto[].class);
            List<NoteDto> notes = notesArray != null ? Arrays.asList(notesArray) : List.of();

            // 3) compter les déclencheurs
            int triggersCount = countTriggers(notes);

            // 4) calculer l’âge
            int age = Period.between(patient.getBirthDate(), LocalDate.now()).getYears();

            // 5) calculer le niveau de risque
            RiskLevel level = computeRiskLevel(age, patient.getGender(), triggersCount);

            // 6) construire la réponse JSON
            RiskResult result = new RiskResult();
            result.setPatientId(patientId);
            result.setFirstName(patient.getFirstName());
            result.setLastName(patient.getLastName());
            result.setAge(age);
            result.setRisk(level);
            result.setTriggersCount(triggersCount);

            return result;
        }

       public RiskResult assessRiskByNames(String lastName, String firstName) {

        // 1) Appeler le microservice patient pour récupérer le patient par nom/prénom
        String url = patientServiceUrl + "/patients/search?lastName=" + lastName + "&firstName=" + firstName;
        PatientDto patient = restTemplate.getForObject(url, PatientDto.class);

        if (patient == null || patient.getId() == null) {
            return null;
        }

        // 2) Réutiliser la logique existante basée sur l'id
        return assess(patient.getId());
    }

        private int countTriggers(List<NoteDto> notes) {
            int count = 0;

            for (NoteDto note : notes) {
                if (note.getContent() == null) continue;
                String lower = note.getContent().toLowerCase(Locale.ROOT);
                for (String trigger : TRIGGERS) {
                    if (lower.contains(trigger.toLowerCase(Locale.ROOT))) {
                        count++;
                    }
                }
            }
            return count;
        }

        private RiskLevel computeRiskLevel(int age, String gender, int triggers) {
            boolean isMale = gender != null && gender.equalsIgnoreCase("M");
            boolean isFemale = gender != null && gender.equalsIgnoreCase("F");

            if (triggers == 0) {
                return RiskLevel.NONE;
            }

            if (age > 30) {
                if (triggers >= 8) return RiskLevel.EARLYONSET;
                if (triggers >= 6) return RiskLevel.INDANGER;
                if (triggers >= 2 && triggers <= 5) return RiskLevel.BORDERLINE;
                return RiskLevel.NONE;
            } else {
                // moins de 30 ans
                if (isMale) {
                    if (triggers >= 5) return RiskLevel.EARLYONSET;
                    if (triggers >= 3) return RiskLevel.INDANGER;
                } else if (isFemale) {
                    if (triggers >= 7) return RiskLevel.EARLYONSET;
                    if (triggers >= 4) return RiskLevel.INDANGER;
                }
                return RiskLevel.NONE;
            }
        }
    }

