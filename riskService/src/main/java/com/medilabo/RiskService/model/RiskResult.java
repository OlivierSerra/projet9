package com.medilabo.RiskService.model;

public class RiskResult {

    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private RiskLevel risk;
    private int triggersCount;

    //Constructeur
    public RiskResult() {

    }

    // getters & setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public RiskLevel getRisk() { return risk; }
    public void setRisk(RiskLevel risk) { this.risk = risk; }

    public int getTriggersCount() { return triggersCount; }
    public void setTriggersCount(int triggersCount) { this.triggersCount = triggersCount; }

}
