package menus;

import managers.PatientAppointmentManager;
import models.Appointment;
import models.Patient;
import repository.AppointmentRepository;
import repository.PatientRepository;
import repository.StaffRepository;
import repository.UserRepository;
import utils.InputValidater;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;



public class PatientMenu extends Menu {
    StaffRepository staffRepo;
    PatientRepository patientRepo;
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyy HH");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:00");

    public PatientMenu(String id, AppointmentRepository apptRepo, StaffRepository staffRepo, UserRepository userRepo, PatientRepository patientRepo){
        super(apptRepo,userRepo, id);
        this.staffRepo = staffRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public void userInterface() {
        PatientAppointmentManager manager = new PatientAppointmentManager();
        Patient current = patientRepo.getPatient(id);

        while (true) {
            flushTerminal();
            System.out.println("Select an option:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");

            System.out.print("Enter your choice (1-10): ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    flushTerminal();
                    viewMedicalRecord(current);
                    break;
                case 2:
                    flushTerminal();
                    updatePersonalInformation(current);
                    break;
                case 3:
                    flushTerminal();
                    viewAvailableAppointments(manager);
                    break;
                case 4:
                    flushTerminal();
                    scheduleAppointment(manager);
                    break;
                case 5:
                    flushTerminal();
                    rescheduleAppointment(manager);
                    break;
                case 6:
                    flushTerminal();
                    cancelAppointment(manager);
                    break;
                case 7:
                    flushTerminal();
                    viewScheduledAppointments(manager);
                    break;
                case 8:
                    flushTerminal();
                    viewPastAppointmentRecords(manager);
                    break;
                case 9:
                    flushTerminal();
                    changePassword();
                    break;
                case 10:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 10.");
            }
        }
    }

    /**
     * Prints the patient's medical record to output stream
     * @param current patient ID
     */
    private void viewMedicalRecord(Patient current) {
        System.out.println("Viewing Medical Record...");
        System.out.println(current.toString());
    }

    /**
     * CLI to update the patient's information, only being able to edit phone number and email
     * @param current curr patient id
     */
    private void updatePersonalInformation(Patient current) {
        System.out.println("Updating Personal Information...");
        System.out.println(current.toString());

        while (true) {
            System.out.println("\nWould you like to edit any contact information?");
            System.out.println("1. Edit Email");
            System.out.println("2. Edit Phone Number");
            System.out.println("3. Exit");

            int profileChoice = InputValidater.getValidInteger();

            switch (profileChoice) {
                case 1:
                    System.out.print("Enter the new email: ");
                    String newEmail = InputValidater.getValidString();
                    patientRepo.updateEmail(current.id, newEmail);
                    break;
                case 2:
                    System.out.print("Enter the new phone number: ");
                    int newPhoneNumber;
                    while (true) {
                        newPhoneNumber = InputValidater.getValidInteger();
                        if (newPhoneNumber > 0) {
                            patientRepo.updatePhoneNumber(current.id, newPhoneNumber);
                            System.out.println("Phone number updated successfully.");
                            break;
                        } else {
                            System.out.println("Phone number must be positive. Please try again.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("No changes made. Exiting.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option (1-3).");
            }
        }
    }

    /**
     * View all available appointments created by doctors
     * @param manager
     */
    private void viewAvailableAppointments(PatientAppointmentManager manager) {
        System.out.println("Viewing Available Appointment Slots...");
        List<Appointment> avail = manager.checkAvailability(apptRepo);

        if (avail.isEmpty()) {
            System.out.println("No available appointments.");
        } else {
            avail.sort((a1, a2) -> {
                LocalDateTime time1 = LocalDateTime.parse(a1.startTime, inputFormatter);
                LocalDateTime time2 = LocalDateTime.parse(a2.startTime, inputFormatter);
                return time1.compareTo(time2);
            });

            for (int i = 0; i < avail.size(); i++) {
                Appointment appointment = avail.get(i);
                System.out.println((i + 1) + ". Start Time: " + LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter));
                System.out.println("Staff: " + staffRepo.getName(appointment.doctor_id));
            }
        }
    }

    /**
     * Schedule an appointment, first showing all available appointments, before getting
     * the patient to input which appointment they would like to book
     * @param manager Patient Appointment Manager
     */
    private void scheduleAppointment(PatientAppointmentManager manager) {
        System.out.println("Scheduling an Appointment...");
        List<Appointment> schedAvail = manager.checkAvailability(apptRepo);

        if (schedAvail.isEmpty()) {
            System.out.println("No available appointments.");
            return;
        }

//        for (int i = 0; i < schedAvail.size(); i++) {
//            Appointment appointment = schedAvail.get(i);
//            System.out.println((i + 1) + ". Start Time: " + LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter) +
//                    ", Staff: " + staffRepo.getName(appointment.doctor_id));
//        }

        int apptChoice = selectAppointment(schedAvail);
//        while (true) {
//            System.out.print("Select an appointment (1 to " + schedAvail.size() + "): ");
//            apptChoice = InputValidater.getValidInteger();
//            if (apptChoice >= 1 && apptChoice <= schedAvail.size()) {
//                break;
//            } else {
//                System.out.println("Invalid selection. Please choose a number between 1 and " + schedAvail.size() + ".");
//            }
//        }

        Appointment selectedAppointment = schedAvail.get(apptChoice - 1);
        manager.bookAppointment(apptRepo, selectedAppointment.getID(), id);
        System.out.println("Appointment scheduled successfully.");
    }

    /**
     * Reschedule an appointment, first prompting the patient to select an appointment to
     * cancel before prompting the patient to select a new appointment to book
     * @param manager Patient Appointment Manager
     */
    private void rescheduleAppointment(PatientAppointmentManager manager) {
        List<Appointment> reschedBooked = manager.checkBooked(apptRepo, id);

        if (reschedBooked.isEmpty()) {
            System.out.println("No booked appointments.");
            return;
        }
        System.out.println("Listing current booked appointments.");
        int apptChoice = selectAppointment(reschedBooked);
        List<Appointment> reschedAvail = manager.checkAvailability(apptRepo);

        if (reschedAvail.isEmpty()) {
            System.out.println("No available appointments to change to.");
            return;
        }

        int newApptChoice = selectAppointment(reschedAvail);
        Appointment selectedBooked = reschedBooked.get(apptChoice - 1);
        Appointment selectedNew = reschedAvail.get(newApptChoice - 1);

        manager.bookAppointment(apptRepo, selectedNew.getID(), id);
        manager.cancelAppointment(apptRepo, selectedBooked.getID());
        System.out.println("Successfully rescheduled the appointment.");
    }

    /**
     * Cancel an existing booked appointment
     * @param manager Patient Appointment Manager
     */
    private void cancelAppointment(PatientAppointmentManager manager) {
        List<Appointment> curBooked = manager.checkBooked(apptRepo, id);

        if (curBooked.isEmpty()) {
            System.out.println("No available appointments.");
            return;
        }
        System.out.println("Listing appointments for cancellation.");
        int cancelChoice = selectAppointment(curBooked);
        Appointment cancelled = curBooked.get(cancelChoice - 1);
        manager.cancelAppointment(apptRepo, cancelled.getID());
        System.out.println("Appointment canceled successfully.");
    }

    /**
     * View all currently scheduled appointments, showing appointments pending doctor approval,
     * followed by approved appointments
     * @param manager Patient Appointment Manager
     */

    private void viewScheduledAppointments(PatientAppointmentManager manager) {
        List<Appointment> apptBooked = manager.checkBooked(apptRepo, id);
        List<Appointment> apptPending = manager.checkPending(apptRepo,id);

        if (apptPending.isEmpty()) {
            System.out.println("No pending appointments.");
        } else {
            System.out.println("Current pending appointments:");
            displayAppointments(apptPending);
        }

        if (apptBooked.isEmpty()) {
            System.out.println("No scheduled appointments.");
        } else {
            System.out.println("Current scheduled appointments:");
            displayAppointments(apptBooked);
        }
    }

    /**
     * View all past completed appointments for this patient
     * @param manager Patient Appointment Manager
     */
    private void viewPastAppointmentRecords(PatientAppointmentManager manager) {
        List<Appointment> apptPast = manager.checkPastAppointments(apptRepo, id);

        if (apptPast.isEmpty()) {
            System.out.println("No past appointments.");
        } else {
            displayAppointments(apptPast);
        }
    }

    private int selectAppointment(List<Appointment> appointments) {
        displayAppointments(appointments);
        int choice;
        while (true) {
            System.out.print("Select an appointment (1 to " + appointments.size() + "): ");
            choice = InputValidater.getValidInteger();
            if (choice >= 1 && choice <= appointments.size()) {
                return choice;
            } else {
                System.out.println("Invalid selection. Please choose a number between 1 and " + appointments.size() + ".");
            }
        }
    }

    /**
     * Helper function to display the start time and doctor's name of a list of appointments
     * @param appointments appointments list
     */
    private void displayAppointments(List<Appointment> appointments) {
        appointments.sort((a1, a2) -> {
            LocalDateTime time1 = LocalDateTime.parse(a1.startTime, inputFormatter);
            LocalDateTime time2 = LocalDateTime.parse(a2.startTime, inputFormatter);
            return time1.compareTo(time2);
        });
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String formattedTime = LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter);
            System.out.println((i + 1) + ". Start Time: " + formattedTime + "\n" +"Doctor: " + staffRepo.getName(appointment.doctor_id));
        }
    }
}

