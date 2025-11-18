package com.medilabo.patient.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity

public class PatientModel {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private LocalDate birthDate;

    @NotBlank
    private String gender;

    @NotBlank
    private String adress;

    @NotBlank
    private String phoneNumber;

//=============
// constructeurs
// ============

    public PatientDto(String firstName, String lastName, LocalDate birthDate, String gender, String adress, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }

//=============
// Getters & Setters
// ============

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}