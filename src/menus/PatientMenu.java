package menus;

import managers.PatientAppointmentManager;
import models.Appointment;
import repository.AppointmentRepository;
import repository.StaffRepository;
import utils.InputValidater;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeFormatter;



public class PatientMenu extends Menu {
    AppointmentRepository apptRepo;
    StaffRepository staffRepo;
    String id;

    public PatientMenu(AppointmentRepository repo,StaffRepository staffRepo, String id){
        super(repo,id);
        this.staffRepo = staffRepo;
    }

    public void userInterface(){
        PatientAppointmentManager manager = new PatientAppointmentManager();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyy HH");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");

            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    System.out.println("Viewing Medical Record...");
                    // Implement viewMedicalRecord() method
                    break;

                case 2:
                    System.out.println("Updating Personal Information...");
                    // Implement updatePersonalInformation() method
                    break;

                case 3:
                    System.out.println("Viewing Available Appointment Slots...");
                    List<Appointment> avail = manager.checkAvailability(apptRepo);
                    if (avail.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else
                        // Display available appointments with an index
                        for (int i = 0; i < avail.size(); i++) {
                            Appointment appointment = avail.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime);
                            System.out.println("Staff: " + staffRepo.getName(appointment.doctor_id));
                        }
                    break;

                case 4:
                    System.out.println("Scheduling an appointment...");
                    List<Appointment> schedAvail = manager.checkAvailability(apptRepo);

                    if (schedAvail.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else {
                        // Display available appointments with an index
                        for (int i = 0; i < schedAvail.size(); i++) {
                            Appointment appointment = schedAvail.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                    ", Staff: " + staffRepo.getName(appointment.doctor_id));
                        }

                        // Taking user input to select an appointment
                        int apptChoice;
                        while (true) {
                            System.out.print("Select an appointment (1 to " + schedAvail.size() + "): ");
                            apptChoice = InputValidater.getValidInteger();

                            // Validate the user choice
                            if (apptChoice >= 1 && apptChoice <= schedAvail.size()) {
                                break; // Exit the loop if a valid choice is made
                            } else {
                                System.out.println("Invalid selection. Please choose a number between 1 and " + schedAvail.size() + ".");
                            }
                        }

                        // Access the selected appointment (adjusting for 0-based index)
                        Appointment selectedAppointment = schedAvail.get(apptChoice - 1);
                        manager.bookAppointment(apptRepo, selectedAppointment.getID(), this.id);

                        // Confirm the scheduling
                        System.out.println("You selected appointment with Start Time: " +
                                selectedAppointment.startTime +
                                " and Doctor: " + staffRepo.getName(selectedAppointment.doctor_id) +
                                " (Pending approval)");
                    }


                case 5:
                    System.out.println("Rescheduling an appointment...");
                    System.out.println("Currently booked appointments:");

                    // Check booked appointments
                    List<Appointment> reschedBooked = manager.checkBooked(apptRepo, id);

                    if (reschedBooked.isEmpty()) {
                        System.out.println("No booked appointments.");
                        return;
                    }

                    // Display booked appointments with an index
                    for (int i = 0; i < reschedBooked.size(); i++) {
                        Appointment appointment = reschedBooked.get(i);
                        System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                ", Staff: " + staffRepo.getName(appointment.doctor_id));
                    }

                    // Prompt user to select an appointment to reschedule
                    int apptChoice;
                    while (true) {
                        System.out.print("Select an appointment (1 to " + reschedBooked.size() + ") to reschedule: ");
                        apptChoice = InputValidater.getValidInteger();

                        if (apptChoice >= 1 && apptChoice <= reschedBooked.size()) {
                            break; // Exit the loop if a valid choice is made
                        } else {
                            System.out.println("Invalid selection. Please choose a number between 1 and " + reschedBooked.size() + ".");
                        }
                    }

                    // Check available appointments
                    List<Appointment> reschedAvail = manager.checkAvailability(apptRepo);

                    if (reschedAvail.isEmpty()) {
                        System.out.println("No available appointments to change to.");
                        return;
                    }

                    // Display available appointments with an index
                    System.out.println("Available appointments to change to:");
                    for (int i = 0; i < reschedAvail.size(); i++) {
                        Appointment appointment = reschedAvail.get(i);
                        System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                ", Doctor: " + staffRepo.getName(appointment.doctor_id));
                    }

                    // Prompt user to select a new appointment
                    int newApptChoice;
                    while (true) {
                        System.out.print("Select an appointment (1 to " + reschedAvail.size() + ") to reschedule to: ");
                        newApptChoice = InputValidater.getValidInteger();

                        if (newApptChoice >= 1 && newApptChoice <= reschedAvail.size()) {
                            break; // Exit the loop if a valid choice is made
                        } else {
                            System.out.println("Invalid selection. Please choose a number between 1 and " + reschedAvail.size() + ".");
                        }
                    }

                    // Get the selected appointments
                    Appointment selectedBooked = reschedBooked.get(apptChoice - 1);
                    Appointment selectedNew = reschedAvail.get(newApptChoice - 1);

                    // Book the new appointment
                    manager.bookAppointment(apptRepo, selectedNew.getID(), id);
                    manager.cancelAppointment(apptRepo, selectedBooked.getID());

                    // Confirm the rescheduling
                    System.out.println("Successfully rescheduled to:");
                    System.out.println("Start Time: " + selectedNew.startTime + "\n" + "Doctor: " + staffRepo.getName(selectedNew.doctor_id) + "\n""(Pending approval)");
                    break;

                case 6:
                    System.out.println("Canceling an Appointment...");
                    List<Appointment> curBooked = manager.checkBooked(apptRepo, this.id);

                    if (curBooked.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else {
                        // Display available appointments with an index
                        for (int i = 0; i < curBooked.size(); i++) {
                            Appointment appointment = curBooked.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                    ", Staff: " + staffRepo.getName(appointment.doctor_id));
                        }

                        // Taking user input to select an appointment

                        int cancelChoice;
                        while (true) {
                            System.out.print("Select an appointment (1 to " + curBooked.size() + ") to cancel: ");
                            cancelChoice = InputValidater.getValidInteger();

                            if (cancelChoice >= 1 && cancelChoice <= curBooked.size()) {
                                break; // Exit the loop if a valid choice is made
                            } else {
                                System.out.println("Invalid selection. Please choose a number between 1 and " + curBooked.size() + ".");
                            }
                        }
                        Appointment cancelled = curBooked.get(cancelChoice - 1);
                        // Perform operations on the selected appointment
                        System.out.println("Appointment with Start Time: " +
                                cancelled.startTime +
                                " and Doctor: " + staffRepo.getName(cancelled.doctor_id) + "has been cancelled");
                        manager.cancelAppointment(apptRepo, cancelled.getID());
                    }
                    break;
                case 7:
                    System.out.println("Viewing Scheduled Appointments...");
                    // Implement viewScheduledAppointments() method
                    break;

                case 8:
                    System.out.println("Viewing Past Appointment Outcome Records...");
                    // Implement viewPastOutcomes() method
                    break;

                case 9:
                    System.out.println("Logging out...");
                    // Implement logout() method or simply exit the program
                    break;

                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 9.");
                    break;
            }
        }
    }
}

