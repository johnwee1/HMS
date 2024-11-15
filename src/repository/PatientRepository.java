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
            return "Patient not found.";
        }
        return patient.name;
    }

    public Patient getPatient(String id) {
        return defaultReadItem(id);
    }

    // Updated to include new attributes
    public boolean createNewPatient(String id, String name, String email, int phoneNumber, String role,
                                    int age, Boolean gender, String bloodType) {
        if (defaultViewOnlyDatabase().containsKey(id)) return false;
        Patient newPatient = new Patient(id, name, email, phoneNumber, role, age, gender, bloodType);
        defaultCreateItem(newPatient);
        return true;
    }


    // Updated to include new attributes
    public void updatePatient(String username, String name, String role, Integer age, Boolean gender,
                              String email, Integer phoneNumber, String bloodType, String pastDiagnoses, String treatmentPlan) {
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
        if (email != null) {
            curPatient.email = email;
        }
        if (phoneNumber != null) {
            curPatient.phoneNumber = phoneNumber;
        }
        if (bloodType != null) {
            curPatient.bloodType = bloodType;
        }
        if (pastDiagnoses != null) {
            curPatient.pastDiagnoses = pastDiagnoses;
        }
        if (treatmentPlan != null) {
            curPatient.currentTreatmentPlan = treatmentPlan;
        }
        defaultUpdateItem(curPatient);
    }

    // Update Past Diagnoses
    public void updatePastDiagnoses(String patientId, String newDiagnoses) {
        Patient patient = defaultReadItem(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
            return;
        }
        patient.pastDiagnoses = newDiagnoses;
        defaultUpdateItem(patient);
        System.out.println("Past diagnoses updated successfully.");
    }

    // Update Current Treatment Plan
    public void updateCurrentTreatmentPlan(String patientId, String newTreatmentPlan) {
        Patient patient = defaultReadItem(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
            return;
        }
        patient.currentTreatmentPlan = newTreatmentPlan;
        defaultUpdateItem(patient);
        System.out.println("Current treatment plan updated successfully.");
    }

    // Update Email
    public void updateEmail(String patientId, String newEmail) {
        Patient patient = defaultReadItem(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
            return;
        }
        if (newEmail != null && newEmail.contains("@")) {
            patient.email = newEmail;
            defaultUpdateItem(patient);
            System.out.println("Email updated successfully.");
        } else {
            System.out.println("Invalid email address. Please provide a valid email.");
        }
    }

    // Update Phone Number
    public void updatePhoneNumber(String patientId, int newPhoneNumber) {
        Patient patient = defaultReadItem(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
            return;
        }
        if (newPhoneNumber > 0) {
            patient.phoneNumber = newPhoneNumber;
            defaultUpdateItem(patient);
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Invalid phone number. Please provide a valid phone number.");
        }
    }


    public void deletePatient(String username) {
        defaultDeleteItem(username);
    }

    // Updated filter method to include new attributes
    public List<Patient> filterPatients(String username, String name, String role, Integer age, Boolean gender,
                                        String email, Integer phoneNumber, String bloodType, String pastDiagnoses, String treatmentPlan) {
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
            if (email != null) {
                matches = matches && Objects.equals(patient.email, email);
            }
            if (phoneNumber != null) {
                matches = matches && Objects.equals(patient.phoneNumber, phoneNumber);
            }
            if (bloodType != null) {
                matches = matches && Objects.equals(patient.bloodType, bloodType);
            }
            if (pastDiagnoses != null) {
                matches = matches && Objects.equals(patient.pastDiagnoses, pastDiagnoses);
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


