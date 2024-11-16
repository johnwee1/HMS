package menus;

import managers.AdminAppointmentManager;
import models.Appointment;
import models.Medicine;
import models.Staff;
import repository.*;
import utils.InputValidater;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminMenu extends Menu {
    StaffRepository staffRepo;
    PatientRepository patRepo;
    MedicineRepository medRepo;
    String id;

    public AdminMenu(String id, AppointmentRepository repo, StaffRepository staffRepo, PatientRepository patRepo, MedicineRepository medRepo, UserRepository userRepo) {
        super(repo, userRepo, id);
        this.staffRepo = staffRepo;
        this.patRepo = patRepo;
        this.medRepo = medRepo;
    }

    public void userInterface() {
        boolean exit = false;
        AdminAppointmentManager admapptmngr = new AdminAppointmentManager();


        while (!exit) {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    manageHospitalStaffMenu();
                    break;
                case 2:
                    viewAppointmentsMenu(admapptmngr);
                    break;
                case 3:
                    manageInventoryMenu();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public void manageHospitalStaffMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Manage Hospital Staff ===");
            System.out.println("1. Add Staff Member");
            System.out.println("2. Update Staff Member");
            System.out.println("3. Remove Staff Member");
            System.out.println("4. Display Staff List");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    System.out.println("Add Staff Member functionality");
                    while (true) {
                        System.out.println("\nWho would you like to add");
                        System.out.println("1. Patient");
                        System.out.println("2. Doctor");
                        System.out.println("3. Pharmacist");

                        int staffchoice =  InputValidater.getValidInteger();
                        switch (staffchoice) {
                            case 1:
                                System.out.print("Enter Patient username (UID): ");
                                String id = InputValidater.getValidString();

                                System.out.print("Enter Patient Name: ");
                                String name = InputValidater.getValidString();

                                System.out.print("Enter Patient Email: ");
                                String email = InputValidater.getValidString();

                                System.out.print("Enter Patient Phone Number (digits only): ");
                                int phoneNumber = InputValidater.getValidInteger();

                                System.out.print("Enter Patient Age: ");
                                int age = InputValidater.getValidInteger();


                                System.out.print("Enter Patient Gender (0 for Male, 1 for Female): ");
                                int patintgender = -1;
                                while (patintgender < 0 || patintgender > 1) {
                                    patintgender = InputValidater.getValidInteger();
                                }
                                Boolean patboolgender = true;
                                if (patintgender == 0) {
                                    patboolgender = true;
                                } else if (patintgender == 1) {
                                    patboolgender = false;
                                }

                                System.out.print("Enter Patient Blood Type: ");
                                String bloodType = InputValidater.getValidString();

                                System.out.print("Set a secure password: ");
                                String password = InputValidater.getValidString();

                                boolean reporesult = patRepo.createNewPatient(id, name, email, phoneNumber, "patient", age, patboolgender, bloodType);
                                boolean authresult = userRepo.createNewUser(id, password, "patient");


                                if (reporesult && authresult) {
                                    System.out.println("Patient created successfully!");
                                } else {
                                    System.out.println("A patient with this ID already exists. Please try again with a unique ID.");
                                }
                                break;

                            case 2:
                                System.out.print("Enter Doctor username (UID): ");
                                String docid = InputValidater.getValidString();

                                System.out.print("Enter Doctor name: ");
                                String docname = InputValidater.getValidString();

                                System.out.print("Enter Doctor age: ");
                                int docage = InputValidater.getValidInteger();

                                System.out.print("Enter Doctor Gender (0 for Male, 1 for Female): ");
                                int docintgender = -1;
                                while (docintgender < 0 || docintgender > 1) {
                                    docintgender = InputValidater.getValidInteger();
                                }
                                boolean docboolgender = true;
                                if (docintgender == 0) {
                                    docboolgender = true;
                                } else if (docintgender == 1) {
                                    docboolgender = false;
                                }

                                System.out.println("Enter a secure password:");
                                String docpw = InputValidater.getValidString();

                                boolean docreporesult = staffRepo.createNewStaff(docid, docname, "doctor", docage, docboolgender);
                                boolean docauthresult = userRepo.createNewUser(docid, docpw, "doctor");
                                if (docauthresult && docreporesult) {
                                    System.out.println("Doctor created successfully!");
                                } else {
                                    System.out.println("A Doctor with this ID already exists. Please try again with a unique ID.");
                                }
                                break;

                            case 3:
                                System.out.print("Enter Pharmacist username (UID): ");
                                String pharmid = InputValidater.getValidString();

                                System.out.print("Enter Pharmacist name: ");
                                String pharmname = InputValidater.getValidString();

                                System.out.print("Enter Pharmacist age: ");
                                int pharmage = InputValidater.getValidInteger();

                                System.out.print("Enter Pharmacist Gender (0 for Male, 1 for Female): ");
                                int pharmintgender = -1;
                                while (pharmintgender < 0 || pharmintgender > 1) {
                                    pharmintgender = InputValidater.getValidInteger();
                                }
                                boolean pharmboolgender = true;
                                if (pharmintgender == 0) {
                                    pharmboolgender = true;
                                } else if (pharmintgender == 1) {
                                    pharmboolgender = false;
                                }

                                System.out.println("Enter a secure password:");
                                String pharmpw = InputValidater.getValidString();

                                boolean pharmreporesult = staffRepo.createNewStaff(pharmid, pharmname, "pharmacist", pharmage, pharmboolgender);
                                boolean pharmauthresult = userRepo.createNewUser(pharmid, pharmpw, "pharmacist");
                                if (pharmauthresult && pharmreporesult) {
                                    System.out.println("Pharmacist created successfully!");
                                } else {
                                    System.out.println("A Pharmacist with this ID already exists. Please try again with a unique ID.");
                                }
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option (1-3).");
                                continue;
                            }
                            break;
                        }
                    break; // end case

                case 2:
                    System.out.println("Update Staff Member functionality");
                    // Add code for updating a staff member
                    break;
                case 3:
                    System.out.println("Remove Staff Member functionality");
                    System.out.println("Enter username (UID) of staff to remove:");
                    String removetarg = InputValidater.getValidString();
                    try {
                        staffRepo.deleteStaff(removetarg);
                        userRepo.deleteUser(removetarg);
                        System.out.println("remove successful");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                }

                    break;
                case 4:
                    System.out.println("Display Staff List functionality");
                    while (true) {
                        System.out.println("\nPlease select a filter:");
                        System.out.println("1. Filter by Role");
                        System.out.println("2. Filter by Age");
                        System.out.println("3. Filter by Gender");
                        System.out.println("4. Show Filtered Results and Exit");

                        int filtchoice = InputValidater.getValidInteger();
                        switch (filtchoice) {
                            case 1:
                                System.out.print("Enter role to filter by: ");
                                String filtrole = InputValidater.getValidString();
                                List<Staff> rolefilteredStaff = staffRepo.filterStaff(null, null, filtrole, null, null);
                                if (rolefilteredStaff.isEmpty()) {
                                    System.out.println("No staff members matched your filters.");
                                } else {
                                    System.out.println("Filtered Staff Names:");
                                    rolefilteredStaff.forEach(staff -> System.out.println(staff.name));
                                }
                                break;

                            case 2:
                                System.out.print("Enter age to filter by: ");
                                int filtage = InputValidater.getValidInteger();
                                List<Staff> agefilteredStaff = staffRepo.filterStaff(null, null, null, filtage, null);
                                if (agefilteredStaff.isEmpty()) {
                                    System.out.println("No staff members matched your filters.");
                                } else {
                                    System.out.println("Filtered Staff Names:");
                                    agefilteredStaff.forEach(staff -> System.out.println(staff.name));
                                }
                                break;
                            case 3:
                                System.out.print("Enter gender to filter by (true for male, false for female): ");
                                while (true) {
                                    String genderInput = InputValidater.getValidString().toLowerCase();
                                    if (genderInput.equals("true") || genderInput.equals("false")) {
                                        boolean filtgender = Boolean.parseBoolean(genderInput);
                                        List<Staff> genderfilteredStaff = staffRepo.filterStaff(null, null, null, null, filtgender);
                                        if (genderfilteredStaff.isEmpty()) {
                                            System.out.println("No staff members matched your filters.");
                                        } else {
                                            System.out.println("Filtered Staff Names:");
                                            genderfilteredStaff.forEach(staff -> System.out.println(staff.name));
                                        }
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Please enter 'true' or 'false'.");
                                    }
                                }
                                break;
                            default:
                                System.out.println("Invalid option. Please select a valid choice.");
                        }
                        break;
                    }
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void viewAppointmentsMenu(AdminAppointmentManager admapptmngr) {
        boolean back = false;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyy HH");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH");

        while (!back) {
            System.out.println("\n=== View Appointments ===");
            System.out.println("1. View Scheduled Appointments");
            System.out.println("2. View Completed Appointments");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    System.out.println("View Scheduled Appointments functionality");
                    System.out.println("Viewing Scheduled Appointment Slots...");
                    List<Appointment> allappt = admapptmngr.viewScheduledAppointments(apptRepo);
                    if (allappt.isEmpty()) {
                        System.out.println("No currrent appointments.");
                    } else {
                        for (int i = 0; i < allappt.size(); i++) {
                            Appointment appointment = allappt.get(i);
                            System.out.println((i + 1) + ". Start Time: " + LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter));
                            System.out.println("Doctor: " + staffRepo.getName(appointment.doctor_id));
                            System.out.println("Patient: " + staffRepo.getName(appointment.patient_id));
                        }
                    }

                    break;
                case 2:
                    System.out.println("View Completed Appointments");
                    System.out.println("Viewing Completed Appointment");
                    List<Appointment> allcompappt = admapptmngr.viewCompletedAppointments(apptRepo);
                    if (allcompappt.isEmpty()) {
                        System.out.println("No currrent appointments.");
                    } else {
                        for (int i = 0; i < allcompappt.size(); i++) {
                            Appointment appointment = allcompappt.get(i);
                            System.out.println((i + 1) + ". Start Time: " + LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter));
                            System.out.println("Doctor: " + staffRepo.getName(appointment.doctor_id));
                            System.out.println("Patient: " + staffRepo.getName(appointment.patient_id));
                            System.out.println("Diagnosis:" + appointment.diagnosis);
                            if (appointment.isPrescribed == 1){
                                System.out.println("Prescription:" + appointment.prescription);
                            } else {
                                System.out.println("Prescription: No prescription set");
                            }
                        }
                    }
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void manageInventoryMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Manage Medication Inventory ===");
            System.out.println("1. Add Medication");
            System.out.println("2. Update Medication Stock");
            System.out.println("3. De-register Medication");
            System.out.println("4. View Replenishment Requests");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    System.out.println("Add Medicine");
                    Medicine m = new Medicine();
                    System.out.println("Key in a new medicine ID");
                    m.id = InputValidater.getValidString();
                    if (medRepo.defaultViewOnlyDatabase().containsKey(m.id)){
                        System.out.println("Medicine already exists! Displaying the medicine info below");
                        medRepo.printMedicineStatus(medRepo.defaultViewOnlyDatabase().get(m.id));
                    }
                    System.out.println("Key in a medicine display name");
                    m.displayName = InputValidater.getValidString();
                    System.out.println("Key in the quantity to input");
                    m.quantity = InputValidater.getValidInteger();
                    System.out.println("Key in a medicine alert level");
                    m.alertLevel = InputValidater.getValidInteger();
                    m.topUpRequested = m.quantity < m.alertLevel;
                    medRepo.registerMedicine(m);
                    break;
                case 2:
                    System.out.println("Enter Medicine ID to replenish:");
                    String id = InputValidater.getValidString();
                    if (!medRepo.defaultViewOnlyDatabase().containsKey(id)){
                        System.out.println("Medicine does not exist! Try again.");
                        break;
                    }
                    Medicine m2 = medRepo.defaultViewOnlyDatabase().get(id);
                    System.out.println("Enter a quantity to replenish (add). The current medicine quantity is " + m2.quantity);
                    int qty = InputValidater.getValidInteger();
                    medRepo.setQuantity(id,qty+m2.quantity);
                    break;
                case 3:
                    System.out.println("Enter Medicine ID to deregister (delete):");
                    String deleteId = InputValidater.getValidString();
                    if (!medRepo.defaultViewOnlyDatabase().containsKey(deleteId)){
                        System.out.println("Medicine does not exist! Try again.");
                    }
                    System.out.println("Confirm Medicine ID again to delete:");
                    String deleteId2 = InputValidater.getValidString();
                    if (deleteId2.equals(deleteId)) {
                        medRepo.deregisterMedicine(deleteId);
                        System.out.println("Medicine deleted successfully!");
                    }
                    else System.out.println("IDs do not match. Try again.");
                    break;
                case 4:
                    medRepo.viewReplenishmentRequests();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }
}