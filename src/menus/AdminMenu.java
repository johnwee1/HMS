package menus;

import repository.AppointmentRepository;
import repository.MedicineRepository;
import repository.PatientRepository;
import repository.StaffRepository;
import utils.InputValidater;

public class AdminMenu extends Menu {
    AppointmentRepository apptRepo;
    StaffRepository staffRepo;
    PatientRepository patRepo;
    MedicineRepository medRepo;
    String id;

    public AdminMenu(AppointmentRepository repo, StaffRepository staffRepo, String id, PatientRepository patRepo, MedicineRepository medRepo) {
        super(repo, id);
        this.staffRepo = staffRepo;
        this.patRepo = patRepo;
        this.medRepo = medRepo;
    }

    public void userInterface() {
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = InputValidater.getValidInteger();

            switch (choice) {
                case 1:
                    manageHospitalStaffMenu();
                    break;
                case 2:
                    viewAppointmentsMenu();
                    break;
                case 3:
                    manageInventoryMenu();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static void manageHospitalStaffMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Manage Hospital Staff ===");
            System.out.println("1. Add Staff Member");
            System.out.println("2. Update Staff Member");
            System.out.println("3. Remove Staff Member");
            System.out.println("4. Display Staff List");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Add Staff Member functionality");
                    // Add code for adding a staff member
                    break;
                case 2:
                    System.out.println("Update Staff Member functionality");
                    // Add code for updating a staff member
                    break;
                case 3:
                    System.out.println("Remove Staff Member functionality");
                    // Add code for removing a staff member
                    break;
                case 4:
                    System.out.println("Display Staff List functionality");
                    // Add code for displaying staff list
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void viewAppointmentsMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== View Appointments ===");
            System.out.println("1. View All Appointments");
            System.out.println("2. View Appointments by Status");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("View All Appointments functionality");
                    // Add code to view all appointments
                    break;
                case 2:
                    System.out.println("View Appointments by Status functionality");
                    // Add code to view appointments filtered by status
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void manageInventoryMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== Manage Medication Inventory ===");
            System.out.println("1. Add Medication");
            System.out.println("2. Update Medication Stock");
            System.out.println("3. Remove Medication");
            System.out.println("4. Set Low Stock Alert Level");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Add Medication functionality");
                    // Add code for adding medication
                    break;
                case 2:
                    System.out.println("Update Medication Stock functionality");
                    // Add code for updating medication stock
                    break;
                case 3:
                    System.out.println("Remove Medication functionality");
                    // Add code for removing medication
                    break;
                case 4:
                    System.out.println("Set Low Stock Alert Level functionality");
                    // Add code for setting low stock alert level
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