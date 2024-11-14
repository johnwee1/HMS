package menus;

import managers.PatientAppointmentManager;
import models.Appointment;
import repository.AppointmentRepository;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeFormatter;



public class PatientMenu extends Menu {
    AppointmentRepository repo;
    String id;

    public PatientMenu(AppointmentRepository repo, String id){
        super(repo,id);
    }

    public void userInterface(){
        PatientAppointmentManager manager = new PatientAppointmentManager();
        Scanner scanner = new Scanner(System.in);
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

            int choice = scanner.nextInt();

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
                    List<Appointment> avail = manager.checkAvailability(repo);
                    if (avail.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else
                        // Display available appointments with an index
                        for (int i = 0; i < avail.size(); i++) {
                            Appointment appointment = avail.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime);
                            System.out.println("Doctor: " + appointment.doctor_id);
                        }
                    break;

                case 4:
                    System.out.println("Schedule appointment selected");
                    List<Appointment> schedavail = manager.checkAvailability(repo);

                    if (schedavail.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else {
                        // Display available appointments with an index
                        for (int i = 0; i < schedavail.size(); i++) {
                            Appointment appointment = schedavail.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                    ", Doctor: " + appointment.doctor_id);
                        }

                        // Taking user input to select an appointment
                        System.out.print("Select an appointment (1 to " + schedavail.size() + "): ");

                        int apptchoice = scanner.nextInt();

                        // Check if the apptchoice is within the valid range
                        if (apptchoice >= 1 && apptchoice <= schedavail.size()) {
                            // Access the selected appointment (adjusting for 0-based index)
                            Appointment selectedAppointment = schedavail.get(apptchoice - 1);
                            manager.bookAppointment(repo,selectedAppointment.getID(),id);

                            // Perform operations on the selected appointment
                            System.out.println("You selected appointment with Start Time: " +
                                    selectedAppointment.startTime +
                                    " and Doctor: " + selectedAppointment.doctor_id + "pending approval");

                            // Manipulate the selectedAppointment object as needed here
                        } else {
                            System.out.println("Invalid selection. Please choose a number between 1 and " + schedavail.size());
                        }

                        scanner.close();
                    }


                    break;

                case 5:
                    System.out.println("Rescheduling an appointment...");
                    System.out.println("Currently booked appointments:");
                    List<Appointment> reschedBooked = manager.checkBooked(repo,this.id);
                    if (reschedBooked.isEmpty()) {
                        System.out.println("No booked appointments.");
                    } else {
                        // Display available appointments with an index
                        for (int i = 0; i < reschedBooked.size(); i++) {
                            Appointment appointment = reschedBooked.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                    ", Doctor: " + appointment.doctor_id);
                        }
                        System.out.print("Select an appointment (1 to " + reschedBooked.size() + ") to reschedule: ");
                    }
                    int apptchoice = scanner.nextInt();

                    // Check if the apptchoice is within the valid range
                    if (apptchoice >= 1 && apptchoice <= reschedBooked.size()) {
                        List<Appointment> reschedAvail = manager.checkAvailability(repo);
                        if (reschedAvail.isEmpty()) {
                            System.out.println("No available appointments to change to.");
                        } else {
                            System.out.println("Select appointment to change to:");
                            for (int i = 0; i < reschedAvail.size(); i++) {
                                Appointment appointment = reschedAvail.get(i);
                                System.out.println((i + 1) + ". Start Time: " + appointment.startTime + "\n" + "Doctor: " + appointment.doctor_id);
                            }
                            System.out.print("Select an appointment (1 to " + reschedBooked.size() + ") to reschedule: ");
                        }
                        Appointment selectedAppointment = reschedBooked.get(apptchoice - 1);
                        manager.bookAppointment(repo,selectedAppointment.getID(),id);

                        // Perform operations on the selected appointment
                        System.out.println("You selected appointment with Start Time: " +
                                selectedAppointment.startTime +
                                " and Doctor: " + selectedAppointment.doctor_id + "pending approval");

                        // Manipulate the selectedAppointment object as needed here
                    } else {
                        System.out.println("Invalid selection. Please choose a number between 1 and " + schedavail.size());
                    }

                    scanner.close();
                    
                    break;

                case 6:
                    System.out.println("Canceling an Appointment...");
                    List<Appointment> curBooked = manager.checkBooked(repo, this.id);

                    if (curBooked.isEmpty()) {
                        System.out.println("No available appointments.");
                    } else {
                        // Display available appointments with an index
                        for (int i = 0; i < curBooked.size(); i++) {
                            Appointment appointment = curBooked.get(i);
                            System.out.println((i + 1) + ". Start Time: " + appointment.startTime +
                                    ", Doctor: " + appointment.doctor_id);
                        }

                        // Taking user input to select an appointment
                        System.out.print("Select an appointment (1 to " + curBooked.size() + "): ");

                        int cancelchoice = scanner.nextInt();

                        // Check if the cancelchoice is within the valid range
                        if (cancelchoice >= 1 && cancelchoice <= curBooked.size()) {
                            // Access the selected appointment (adjusting for 0-based index)
                            Appointment selectedAppointment = curBooked.get(cancelchoice - 1);
                            manager.cancelAppointment(repo,selectedAppointment.getID());

                            // Perform operations on the selected appointment
                            System.out.println("appointment with Start Time: " +
                                    selectedAppointment.startTime +
                                    " and Doctor: " + selectedAppointment.doctor_id + "has been cancelled");
                    
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

            scanner.close();



        }
    }
}

