package models;

public class Patient implements IdentifiedObject {
    public String id;
    public String name;
    public String email;
    public int phoneNumber;
    public String role;
    public int age;
    public boolean gender;
    public String bloodType;
    public String pastDiagnoses;
    public String currentTreatmentPlan;

    public Patient(String id, String name, String email, int phoneNumber, String role,
                   int age, boolean gender, String bloodType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.bloodType = bloodType;
        this.pastDiagnoses = "";
        this.currentTreatmentPlan = "";
    }

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Patient Information:\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Role: " + role + "\n" +
                "Age: " + age + "\n" +
                "Gender: " + (gender ? "Male" : "Female") + "\n" +
                "Blood Type: " + bloodType + "\n" +
                "Past Diagnoses: " + pastDiagnoses + "\n" +
                "Current Treatment Plan: " + currentTreatmentPlan;
    }
}


