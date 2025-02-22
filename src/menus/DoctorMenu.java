package menus;

import managers.DoctorAppointmentManager;
import models.Appointment;
import models.Patient;
import repository.AppointmentRepository;
import repository.PatientRepository;
import repository.StaffRepository;
import repository.UserRepository;
import utils.InputValidater;
import utils.DateTimeHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class DoctorMenu extends Menu{
    StaffRepository staffRepo;
    PatientRepository patientRepo;
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyy HH");

    public DoctorMenu(AppointmentRepository apptRepo, StaffRepository staffRepo, String id, UserRepository userRepo, PatientRepository patientRepo){
        super(apptRepo,userRepo, id);
        this.staffRepo = staffRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public void userInterface() {
        DoctorAppointmentManager apptManager = new DoctorAppointmentManager();
        while (true) {
            flushTerminal();
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");

            System.out.print("Enter your choice (1-9): ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    flushTerminal();
                    System.out.println("Viewing Patient Medical Records...");
                    viewPatientMedicalRecords(apptManager,patientRepo,id);
                    break;

                case 2:
                    flushTerminal();
                    System.out.println("Updating Patient Medical Records...");
                    // Show the list of patients under the doctor's care first
                    viewPatientMedicalRecords(apptManager,patientRepo,id);
                    updatePatientMedicalRecord(apptManager,patientRepo,id);
                    break;

                case 3:
                    flushTerminal();
                    System.out.println("Viewing Personal Schedule...");
                    viewPersonalSchedule(apptManager, apptRepo, id);

                    System.out.println("Would you like to update your personal schedule? (y/n)");
                    String updateChoice = InputValidater.getValidString().toLowerCase();
                    if (updateChoice.equals("y")){
                        updatePersonalSchedule(apptManager, id);
                    }
                    break;

                case 4:
                    flushTerminal();
                    System.out.println("Setting Availability for Appointments...");
                    createAppointments(apptManager,id);
                    break;

                case 5:
                    flushTerminal();
                    System.out.println("Accepting or Declining Appointment Requests...");
                    handlePendingAppointments(apptManager,id);
                    break;

                case 6:
                    flushTerminal();
                    System.out.println("Viewing Upcoming Appointments...");
                    viewBookedAppointments(apptManager,id);
                    break;

                case 7:
                    flushTerminal();
                    System.out.println("Recording Appointment Outcome...");
                    recordAppointmentOutcome(apptManager,id);
                    break;

                case 8:
                    flushTerminal();
                    changePassword();
                    break;

                case 9:
                    System.out.println("Logging out...");
                    // Exit the loop and log out
                    return;

                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 9.");
                    break;
            }
        }
    }

    /**
     * CLI to view patient medical records under a particular doctor
     * @param apptManager apptmanager obj
     * @param patientRepo patientrepo obj
     * @param doctorId doctor id
     */
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
                System.out.println(patient);
                patientNumber++; // Increment the patient number
            } else {
                System.out.println("No medical record found for patient ID: " + patientId);
            }
        }
    }

    /**
     * CLI Menu (and submenus) to update patient medical record
     * @param apptManager apptmanager obj
     * @param patientRepo patientrepo obj
     * @param doctorId doctor id
     */
    private void updatePatientMedicalRecord(DoctorAppointmentManager apptManager, PatientRepository patientRepo, String doctorId) {

        // Get the set of patient IDs under the doctor's care
        Set<String> patientIds = apptManager.patientsUnderCare(apptRepo, doctorId);

        if (patientIds.isEmpty()) {
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
            System.out.println(selectedPatientRecord);
            break;
        }
    }

    /**
     * CLI to view personal schedule of doctor, specifically leave days
     * @param apptManager apptmanager obj
     * @param apptRepo apptrepo obj
     * @param doctorId doctor id
     */
    private void viewPersonalSchedule(DoctorAppointmentManager apptManager, AppointmentRepository apptRepo, String doctorId){
        System.out.println("Viewing Personal Leave Schedule...");

        //fetch all appts with status 4
        List<Appointment> leaveDays = apptManager.viewLeaves(apptRepo, doctorId);

        //Scenario if there are no leave days
        if (leaveDays.isEmpty()){
            System.out.println("You have no leave days scheduled.");
            return;
        }

        //Sorting leave days chronologically and printing with time
        leaveDays.sort(Comparator.comparing(a -> a.startTime));
        System.out.println("Scheduled Leave Days: ");
        for (Appointment leaveDay : leaveDays){
            System.out.println("Leave on: " + leaveDay.startTime);
        }

    }

    /**
     * CLI to add personal leave days by doctor
     * @param apptManager apptmanager obj
     * @param doctorId doctor id
     */
    private void updatePersonalSchedule(DoctorAppointmentManager apptManager, String doctorId){
        System.out.println("Setting Personal Leave Days...");

        //Get date of leave from doctor
        String datePrefix;
        while (true){
             System.out.println("Enter the leave day (1-31): ");
             int day = InputValidater.getValidInteger();

             System.out.println("Enter the leave month (1-12): ");
             int month = InputValidater.getValidInteger();

             System.out.println("Enter the leave year (e.g., 2024");
             int year = InputValidater.getValidInteger();

             datePrefix = String.format("%02d%02d%02d", day, month, year % 100);

             String testDateTime = datePrefix + " 00";
             if(DateTimeHandler.isValid(testDateTime)){
                 break;
             } else {
                 System.out.println("invalid date entered. Please try again");
             }
        }

        //Get time of leave slot from doctor
        int startHour, endHour;
        while (true){
            System.out.println("Enter the start hour of leave (0-23): ");
            startHour = InputValidater.getValidInteger();

            System.out.print("Enter the end hour of leave (0-23): ");
            endHour = InputValidater.getValidInteger();

            if (startHour < 0 || endHour < 0 || startHour >= endHour || endHour > 23){
                System.out.println("Invalid time range. Please ensure start time is before end time.");
            } else {
                break;
            }
        }

        for (int hour = startHour; hour < endHour; hour++){
            String startTime = datePrefix + " " + String.format("%02d", hour);
            String endTime = datePrefix + " " + String.format("%02d", hour + 1);

            if (!DateTimeHandler.isValid(startTime) || !DateTimeHandler.isValid(endTime)){
                System.out.println("Invalid date or time format for: " + startTime + " - " + endTime);
                continue;
            }
            //Create appointment with status 4 to block appts
            boolean created = apptManager.createAppointment(apptRepo, startTime, endTime, doctorId, 4);
            if (created){
                System.out.println("Leave scheduled: " + startTime + " - " + endTime);
            } else {
                System.out.println("Failed to schedule leave for: " + startTime + " - " + endTime);
            }
        }
        System.out.println("Leave scheduling complete.");
    }

    /**
     * CLI Menu (and submenus) to create appointments
     * @param apptManager apptmanager obj
     * @param doctorId doctor id
     */
    public void createAppointments(DoctorAppointmentManager apptManager, String doctorId) {
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
            if (startHour < 0  || endHour < 0 || endHour > 23 || startHour >= endHour) {
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
            boolean created = apptManager.createAppointment(apptRepo, startTime, endTime, doctorId, 1);

            if (created) {
                System.out.println("Appointment created: " + startTime + " - " + endTime);
            } else {
                System.out.println("Failed to create appointment for: " + startTime + " - " + endTime);
            }
        }

        System.out.println("Appointment creation process completed.");
    }

    /**
     * Fetch the appointments then allow the user (doctor) to accept or reject appointments that are pending
     * @param apptManager apptManager
     * @param doctorId id
     */
    private void handlePendingAppointments(DoctorAppointmentManager apptManager, String doctorId) {
        List<Appointment> pendingAppointments = apptManager.checkPending(apptRepo, doctorId);

        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments.");
            return;
        }
        pendingAppointments.sort((a1, a2) -> {
            LocalDateTime time1 = LocalDateTime.parse(a1.startTime, inputFormatter);
            LocalDateTime time2 = LocalDateTime.parse(a2.startTime, inputFormatter);
            return time1.compareTo(time2);
        });

        System.out.println("Pending Appointments:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment appointment = pendingAppointments.get(i);
            System.out.println((i + 1) + ". Start Time: " + appointment.startTime + ", Patient ID: " + appointment.patient_id);
        }

        int choice;
        while (true) {
            System.out.print("Select an appointment to accept or decline (1-" + pendingAppointments.size() + "): ");
            choice = InputValidater.getValidInteger();

            if (choice >= 1 && choice <= pendingAppointments.size()) {
                break;
            } else {
                System.out.println("Invalid selection. Please choose a valid appointment number.");
            }
        }

        Appointment selectedAppointment = pendingAppointments.get(choice - 1);

        int action;
        while (true) {
            System.out.print("Would you like to (1) Accept or (2) Decline this appointment? Enter 1 or 2: ");
            action = InputValidater.getValidInteger();

            if (action == 1) {
                apptManager.acceptAppointment(apptRepo, selectedAppointment.getID());
                System.out.println("Appointment accepted.");
                break;
            } else if (action == 2) {
                apptManager.declineAppointment(apptRepo, selectedAppointment.getID());
                System.out.println("Appointment declined.");
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1 to accept or 2 to decline.");
            }
        }
    }

    /**
     * CLI to display booked appointments to the doctor
     * @param apptManager
     * @param doctorId
     */
    private void viewBookedAppointments(DoctorAppointmentManager apptManager, String doctorId) {
        List<Appointment> bookedAppointments = apptManager.checkBooked(apptRepo, doctorId);

        if (bookedAppointments.isEmpty()) {
            System.out.println("No upcoming booked appointments.");
            return;
        }

        bookedAppointments.sort((a1, a2) -> {
            LocalDateTime time1 = LocalDateTime.parse(a1.startTime, inputFormatter);
            LocalDateTime time2 = LocalDateTime.parse(a2.startTime, inputFormatter);
            return time1.compareTo(time2);
        });

        System.out.println("Upcoming Booked Appointments:");
        for (int i = 0; i < bookedAppointments.size(); i++) {
            Appointment appointment = bookedAppointments.get(i);
            System.out.println((i + 1) + ". Start Time: " + appointment.startTime + ", Patient ID: " + appointment.patient_id);
        }
    }

    /**
     * Record the outcome (diagnosis, prescription, appointment type)
     * @param apptManager
     * @param doctorId
     */
    private void recordAppointmentOutcome(DoctorAppointmentManager apptManager, String doctorId) {
        List<Appointment> bookedAppointments = apptManager.checkBooked(apptRepo, doctorId);

        if (bookedAppointments.isEmpty()) {
            System.out.println("No booked appointments to complete.");
            return;
        }

        bookedAppointments.sort((a1, a2) -> {
            LocalDateTime time1 = LocalDateTime.parse(a1.startTime, inputFormatter);
            LocalDateTime time2 = LocalDateTime.parse(a2.startTime, inputFormatter);
            return time1.compareTo(time2);
        });

        System.out.println("Booked Appointments:");
        for (int i = 0; i < bookedAppointments.size(); i++) {
            Appointment appointment = bookedAppointments.get(i);
            System.out.println((i + 1) + ". Start Time: " + appointment.startTime + ", Patient ID: " + appointment.patient_id);
        }

        int choice;
        while (true) {
            System.out.print("Select an appointment to complete (1-" + bookedAppointments.size() + "): ");
            choice = InputValidater.getValidInteger();

            if (choice >= 1 && choice <= bookedAppointments.size()) {
                break;
            } else {
                System.out.println("Invalid selection. Please choose a valid appointment number.");
            }
        }

        Appointment selectedAppointment = bookedAppointments.get(choice - 1);

        // Get diagnosis and prescription from the doctor
        System.out.print("Enter the diagnosis: ");
        String diagnosis = InputValidater.getValidString();

        System.out.print("Enter the prescription (or 'none' if no prescription): ");
        String prescription = InputValidater.getValidString();
        if (prescription.equalsIgnoreCase("none")) {
            prescription = null;
        }

        // Prompt for the type of service provided
        int serviceType;
        while (true) {
            System.out.print("Enter the type of service provided (0: Consultation, 1: X-Ray, 2: Blood Test): ");
            serviceType = InputValidater.getValidInteger();

            if (serviceType >= 0 && serviceType <= 2) {
                break;
            } else {
                System.out.println("Invalid service type. Please enter 0, 1, or 2.");
            }
        }

        // Complete the appointment using DoctorAppointmentManager
        apptManager.completeAppointment(apptRepo, selectedAppointment.getID(), prescription, diagnosis, serviceType);

        // Update the patient's medical record using PatientRepository
        String patientId = selectedAppointment.patient_id;
        if (diagnosis != null) {
            patientRepo.updatePastDiagnoses(patientId, diagnosis);
        }
        if (prescription != null) {
            patientRepo.updateCurrentTreatmentPlan(patientId, prescription);
        }

        System.out.println("Appointment completed and patient medical record updated.");
    }
}
