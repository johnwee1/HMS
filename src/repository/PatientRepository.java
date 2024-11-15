package repository;

import models.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatientRepository extends GenericRepository<Patient> {
    public PatientRepository(String csv_filepath) {
        super(Patient.class, csv_filepath);
    }

    public String getName(String username) {
        Patient patient = defaultReadItem(username);
        if (patient == null) {
            return "patientnotfound";
        }
        return patient.name;
    }

    public boolean createNewPatient(String username, String name, String role, int age, Boolean gender, String treatmentPlan) {
        if (defaultViewOnlyDatabase().containsKey(username)) return false;
        Patient newPatient = new Patient(username, name, role, age, gender, treatmentPlan);
        defaultCreateItem(newPatient);
        return true;
    }

    public void updatePatient(String username, String name, String role, Integer age, Boolean gender, String treatmentPlan) {
        Patient curPatient = defaultReadItem(username);
        if (curPatient == null) {
            System.out.println("Patient not found");
            return;
        }
        if (name != null) {
            curPatient.name = name;
        }
        if (role != null) {
            curPatient.role = role;
        }
        if (age != null) {
            curPatient.age = age;
        }
        if (gender != null) {
            curPatient.gender = gender;
        }
        if (treatmentPlan != null) {
            curPatient.currentTreatmentPlan = treatmentPlan;
        }
        defaultUpdateItem(curPatient);
    }

    public void setCurrentTreatmentPlan(String username, String treatmentPlan) {
        Patient curPatient = defaultReadItem(username);
        if (curPatient == null) {
            System.out.println("Patient not found");
            return;
        }
        curPatient.currentTreatmentPlan = treatmentPlan;
        defaultUpdateItem(curPatient);
    }

    public void deletePatient(String username) {
        defaultDeleteItem(username);
    }

    public List<Patient> filterPatients(String username, String name, String role, Integer age, Boolean gender, String treatmentPlan) {
        List<Patient> filteredList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((key, patient) -> {
            boolean matches = true;
            if (username != null) {
                matches = matches && Objects.equals(patient.id, username);
            }
            if (name != null) {
                matches = matches && Objects.equals(patient.name, name);
            }
            if (role != null) {
                matches = matches && Objects.equals(patient.role, role);
            }
            if (age != null) {
                matches = matches && Objects.equals(patient.age, age);
            }
            if (gender != null) {
                matches = matches && Objects.equals(patient.gender, gender);
            }
            if (treatmentPlan != null) {
                matches = matches && Objects.equals(patient.currentTreatmentPlan, treatmentPlan);
            }
            if (matches) {
                filteredList.add(patient);
            }
        });
        return filteredList;
    }
}

