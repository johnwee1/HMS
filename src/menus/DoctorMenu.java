package menus;

import managers.DoctorAppointmentManager;
import models.Patient;
import repository.AppointmentRepository;
import repository.PatientRepository;
import repository.StaffRepository;
import repository.UserRepository;
import utils.InputValidater;
import utils.DateTimeHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DoctorMenu extends Menu{
    StaffRepository staffRepo;
    PatientRepository patientRepo;
    String id;

    public DoctorMenu(AppointmentRepository apptRepo, StaffRepository staffRepo, String id, UserRepository userRepo, PatientRepository patientRepo){
        super(apptRepo,userRepo, id);
        this.staffRepo = staffRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public void userInterface() {
        DoctorAppointmentManager apptManager = new DoctorAppointmentManager();
        while (true) {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");

            System.out.print("Enter your choice (1-8): ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    System.out.println("Viewing Patient Medical Records...");
                    viewPatientMedicalRecords(apptManager,patientRepo,id);
                    break;

                case 2:
                    System.out.println("Updating Patient Medical Records...");
                    // Show the list of patients under the doctor's care first
                    viewPatientMedicalRecords(apptManager,patientRepo,id);
                    updatePatientMedicalRecord(apptManager,patientRepo,id);
                    break;

                case 3:
                    System.out.println("Viewing Personal Schedule...");
                    // Call method to view the doctor's personal schedule

                    break;

                case 4:
                    System.out.println("Setting Availability for Appointments...");
                    createAppointments(apptManager,apptRepo,id);
                    break;

                case 5:
                    System.out.println("Accepting or Declining Appointment Requests...");

                    break;

                case 6:
                    System.out.println("Viewing Upcoming Appointments...");
                    // Call method to view upcoming appointments

                    break;

                case 7:
                    System.out.println("Recording Appointment Outcome...");
                    // Call method to record the outcome of an appointment

                    break;

                case 8:
                    System.out.println("Logging out...");
                    // Exit the loop and log out
                    return;

                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 8.");
                    break;
            }
        }

    }

    private void viewPatientMedicalRecords(DoctorAppointmentManager apptManager, PatientRepository patientRepo, String doctorId) {
        // Get the set of patient IDs under this doctor's care
        Set<String> patientIds = apptManager.patientsUnderCare(apptRepo, doctorId);

        if (patientIds.isEmpty()) {
            System.out.println("You have no patients under your care.");
            return;
        }

        int patientNumber = 1;

        // Iterate through each patient ID and display the medical record with numbering
        for (String patientId : patientIds) {
            Patient patient = patientRepo.getPatient(patientId);
            if (patient != null) {
                System.out.println("\nPatient " + patientNumber + ":");
                System.out.println(patient.toString());
                patientNumber++; // Increment the patient number
            } else {
                System.out.println("No medical record found for patient ID: " + patientId);
            }
        }
    }

    private void updatePatientMedicalRecord(DoctorAppointmentManager apptManager, PatientRepository patientRepo, String doctorId) {
        System.out.println("Updating Patient Medical Record...");

        // Get the set of patient IDs under the doctor's care
        Set<String> patientIds = apptManager.patientsUnderCare(apptRepo, doctorId);

        if (patientIds.isEmpty()) {
            System.out.println("You have no patients under your care.");
            return;
        }

        // Convert the set of patient IDs to a list for indexed selection
        List<String> patientIdList = new ArrayList<>(patientIds);
        int selectedPatient;

        // Prompt the doctor to select a patient
        while (true) {
            System.out.print("\nSelect a patient (1 to " + patientIdList.size() + ") to update, or 0 to cancel: ");
            selectedPatient = InputValidater.getValidInteger();

            if (selectedPatient == 0) {
                System.out.println("Update canceled.");
                return;
            } else if (selectedPatient >= 1 && selectedPatient <= patientIdList.size()) {
                break; // Valid selection
            } else {
                System.out.println("Invalid selection. Please choose a number between 1 and " + patientIdList.size() + ".");
            }
        }

        // Get the selected patient's ID and record
        String selectedPatientId = patientIdList.get(selectedPatient - 1);
        Patient selectedPatientRecord = patientRepo.getPatient(selectedPatientId);

        if (selectedPatientRecord == null) {
            System.out.println("No medical record found for patient ID: " + selectedPatientId);
            return;
        }

        // Menu for updating treatment plan or past diagnoses
        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Update Past Diagnoses");
            System.out.println("2. Update Current Treatment Plan");
            System.out.println("3. Cancel");

            System.out.print("Enter your choice (1-3): ");
            int updateChoice = InputValidater.getValidInteger();

            switch (updateChoice) {
                case 1:
                    // Update Past Diagnoses
                    System.out.print("Enter the new past diagnoses: ");
                    String newDiagnoses = InputValidater.getValidString();
                    patientRepo.updatePastDiagnoses(selectedPatientId,newDiagnoses);
                    System.out.println("Past diagnoses updated successfully.");
                    break;

                case 2:
                    // Update Current Treatment Plan
                    System.out.print("Enter the new current treatment plan: ");
                    String newTreatmentPlan = InputValidater.getValidString();
                    patientRepo.updateCurrentTreatmentPlan(selectedPatientId,newTreatmentPlan);
                    System.out.println("Current treatment plan updated successfully.");
                    break;

                case 3:
                    // Cancel Update
                    System.out.println("Update canceled.");
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option (1-3).");
                    continue;
            }

            // Confirm the update and display the updated medical record
            System.out.println("\nUpdated Medical Record:");
            System.out.println(selectedPatientRecord.toString());
            break;
        }
    }

    public void createAppointments(DoctorAppointmentManager apptManager, AppointmentRepository apptRepo, String doctorId) {
        System.out.println("Creating new appointments...");

        String datePrefix;
        while (true) {
            // Prompt for the day, month, and year
            System.out.print("Enter the day (1-31): ");
            int day = InputValidater.getValidInteger();

            System.out.print("Enter the month (1-12): ");
            int month = InputValidater.getValidInteger();

            System.out.print("Enter the year (e.g., 2024): ");
            int year = InputValidater.getValidInteger();

            // Construct the date prefix
            datePrefix = String.format("%02d%02d%02d", day, month, year % 100);

            // Validate the constructed date-time string using "00" hour as a test
            String testDateTime = datePrefix + " 00";
            if (DateTimeHandler.isValid(testDateTime)) {
                break;
            } else {
                System.out.println("Invalid date entered. Please try again.");
            }
        }

        int startHour, endHour;
        while (true) {
            // Prompt for the start time and end time in 24-hour format
            System.out.print("Enter the start time (0-23, in 24-hour format): ");
            startHour = InputValidater.getValidInteger();

            System.out.print("Enter the end time (0-23, in 24-hour format): ");
            endHour = InputValidater.getValidInteger();

            // Validate the time range
            if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23 || startHour >= endHour) {
                System.out.println("Invalid time range. Please ensure the start time is before the end time and within 0-23.");
            } else {
                break;
            }
        }

        // Iterate through the time range in 1-hour blocks and create appointments using DoctorAppointmentManager
        for (int hour = startHour; hour < endHour; hour++) {
            String startTime = datePrefix + " " + String.format("%02d", hour);
            String endTime = datePrefix + " " + String.format("%02d", hour + 1);

            // Validate the constructed startTime and endTime using DateTimeHandler.isValid()
            if (!DateTimeHandler.isValid(startTime) || !DateTimeHandler.isValid(endTime)) {
                System.out.println("Invalid date or time format for: " + startTime + " - " + endTime);
                continue; // Skip this time slot if the date-time format is invalid
            }

            // Use DoctorAppointmentManager to create the appointment
            boolean created = apptManager.createAppointment(apptRepo, startTime, endTime, doctorId);

            if (created) {
                System.out.println("Appointment created: " + startTime + " - " + endTime);
            } else {
                System.out.println("Failed to create appointment for: " + startTime + " - " + endTime);
            }
        }

        System.out.println("Appointment creation process completed.");
    }



}
