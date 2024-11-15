package models;

public class Patient implements IdentifiedObject {
    public String id;
    public String name;
    public String role;
    public int age;
    public boolean gender;
    public String currentTreatmentPlan;

    public Patient(String id, String name, String role, int age, boolean gender, String treatmentplan) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.currentTreatmentPlan = treatmentplan;
    }

    public String getID(){
        return id;
    }
}
