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
            System.out.println("4. Change Password");
            System.out.println("5. Logout");
            System.out.print("Enter your choice (1-5): ");
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
                    changePassword();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 5.");
            }
        }

    }

    /**
     * Admin menu that handles all user related operations
     */
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
                    Staff curstaff = null;
                    while (curstaff == null) {
                        System.out.print("Enter Staff username (UID): ");
                        String docupid = InputValidater.getValidString();

                        curstaff = staffRepo.getStaff(docupid);
                        if (curstaff == null) {
                            System.out.println("Staff not found. Please try again.");
                        }
                    }
                    System.out.println("selected staff:");
                    System.out.println("Name: " + curstaff.name);
                    System.out.println("Role: " + curstaff.role);
                    System.out.println("Age: " + curstaff.age);
                    System.out.println("Gender: " + curstaff.displayGender());
                    System.out.println();
                    System.out.println("Update details: (if no change, retype value)");
                    System.out.print("Enter New Name: ");
                    String staffupname = InputValidater.getValidString();

                    String staffuprole = "";
                    while (!(staffuprole.equalsIgnoreCase("doctor") || staffuprole.equalsIgnoreCase("pharmacist"))) {
                        System.out.print("Enter a valid profession (doctor or pharmacist): ");
                        staffuprole = InputValidater.getValidString(); // Trim to remove leading/trailing spaces
                    }

                    System.out.println("Enter New Age:");
                    int staffupage = InputValidater.getValidInteger();

                    System.out.print("Enter New Gender (0 for Male, 1 for Female): ");
                    int docupintgender = -1;
                    while (docupintgender < 0 || docupintgender > 1) {
                        docupintgender = InputValidater.getValidInteger();
                    }
                    boolean staffupboolgender = true;
                    if (docupintgender == 0) {
                        staffupboolgender = true;
                    } else if (docupintgender == 1) {
                        staffupboolgender = false;
                    }

                    staffRepo.updateStaff(curstaff.getID(),staffupname,staffuprole,staffupage,staffupboolgender);
                    System.out.println("Staff successfully updated");
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
                        System.out.println("4. View all Staff");

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
                                    for (int i = 0; i < rolefilteredStaff.size(); i++) {
                                        Staff staff = rolefilteredStaff.get(i);
                                        System.out.printf("%-5d %-20s %-20s %-5d %-10s%n",
                                                i + 1,
                                                staff.name,
                                                staff.role,
                                                staff.age,
                                                staff.displayGender()
                                        );
                                    }
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
                                    for (int i = 0; i < agefilteredStaff.size(); i++) {
                                        Staff staff = agefilteredStaff.get(i);
                                        System.out.printf("%-5d %-20s %-20s %-5d %-10s%n",
                                                i + 1,
                                                staff.name,
                                                staff.role,
                                                staff.age,
                                                staff.displayGender()
                                        );
                                    }
                                }
                                break;
                            case 3:
                                boolean filtgender; // Declare outside the loop to use later
                                while (true) {
                                    System.out.print("Filter by [m]ale or [f]emale? ");
                                    String genderInput = InputValidater.getValidString().toLowerCase();
                                    char c = genderInput.charAt(0);
                                    if (c == 'm' || c == 'f') {
                                        filtgender = c == 'm'; // true for male
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Please enter 'male'/'m' or 'female'/'f'.");
                                    }
                                }
                                List<Staff> genderfilteredStaff = staffRepo.filterStaff(null, null, null, null, filtgender);
                                if (genderfilteredStaff.isEmpty()) {
                                    System.out.println("No staff members matched your filters.");
                                } else {
                                    System.out.println("Filtered Staff Names:");
                                    for (int i = 0; i < genderfilteredStaff.size(); i++) {
                                        Staff staff = genderfilteredStaff.get(i);
                                        System.out.printf("%-5d %-20s %-20s %-5d %-10s%n",
                                                i + 1,
                                                staff.name,
                                                staff.role,
                                                staff.age,
                                                staff.displayGender()
                                        );
                                    }
                                    break;
                                }
                                break;
                            case 4:
                                List<Staff> allstaff = staffRepo.filterStaff(null,null,null,null,null);
                                if (allstaff.isEmpty()) {
                                    System.out.println("No staff members matched your filters.");
                                } else {
                                    System.out.printf("%-5s %-40.36s %-15s %-5s %-10s%n", "Index", "Name", "Role", "Age", "Gender");
                                    System.out.println("--------------------------------------------------------------");
                                    for (int i = 0; i < allstaff.size(); i++) {
                                        Staff staff = allstaff.get(i);
                                        System.out.printf("%-5d %-40.36s %-15s %-5d %-10s%n",
                                                i + 1,
                                                staff.name,
                                                staff.role,
                                                staff.age,
                                                staff.displayGender()
                                        );
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

    /**
     * admin menu that handles all appointment related tasks that the admin can perform
     * @param admapptmngr
     */
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
                        String header = String.format("%-5s %-20s %-25s %-25s", "S/N", "Start Time", "Doctor Name", "Patient Name");
                        System.out.println(header);
                        System.out.println("------------------------------------------------------------------------------------");

                        for (int i = 0; i < allappt.size(); i++) {
                            Appointment appointment = allappt.get(i);
                            String startTimeFormatted = LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter);
                            String doctorName = staffRepo.getName(appointment.doctor_id);
                            String patientName = patRepo.getName(appointment.patient_id);

                            // Print each row with the same format as the header
                            String row = String.format("%-5d %-20s %-25s %-25s", (i + 1), startTimeFormatted, doctorName, patientName);
                            System.out.println(row);
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
                        // Define column headers and their widths
                        String header = String.format(
                                "%-5s %-20s %-25s %-25s %-20s %-30s",
                                "S/N", "Start Time", "Doctor Name", "Patient Name", "Diagnosis", "Prescription"
                        );
                        System.out.println(header);
                        System.out.println("-----------------------------------------------------------------------------------------------" +
                                "-------------------------------------------");

                        for (int i = 0; i < allcompappt.size(); i++) {
                            Appointment appointment = allcompappt.get(i);
                            String startTimeFormatted = LocalDateTime.parse(appointment.startTime, inputFormatter).format(outputFormatter);
                            String doctorName = staffRepo.getName(appointment.doctor_id);
                            String patientName = patRepo.getName(appointment.patient_id);
                            String diagnosis = appointment.diagnosis;
                            String prescription = appointment.prescriptionStatus == 1 ? appointment.prescription : "No prescription set";

                            // Print each row with the same format as the header
                            String row = String.format(
                                    "%-5d %-20s %-25s %-25s %-20s %-30s",
                                    (i + 1), startTimeFormatted, doctorName, patientName, diagnosis, prescription
                            );
                            System.out.println(row);
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

    /**
     * admin menu that handles all medicine inventory related tasks that the admin can perform
     */
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