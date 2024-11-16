package menus;

import managers.PharmacistAppointmentManager;
import models.Appointment;
import models.Medicine;
import repository.AppointmentRepository;
import repository.MedicineRepository;
import repository.UserRepository;
import utils.InputValidater;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PharmacistMenu extends Menu {

    private final MedicineRepository medRepo;
    private final PharmacistAppointmentManager pharmacistAppointmentManager = new PharmacistAppointmentManager();
    private final Scanner sc = new Scanner(System.in);

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, UserRepository userRepo, String id) {
        super(repo, userRepo, id);
        this.medRepo = med_repo;
    }

    @Override
    public void userInterface() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Pharmacy Management System ===");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("\nPlease select an option (1-6): ");
            List<Appointment> appts = pharmacistAppointmentManager.checkOutstandingRecords(apptRepo);
            int choice = InputValidater.getValidInteger();
            switch (choice) {
                case 1:
                    printAppointments(appts);
                    break;

                case 2:
                    printAppointments(appts);
                    if (appts.isEmpty()) break;
                    System.out.println("Updating Prescription Status...");
                    System.out.println("Enter the number (id) of the appointment for prescription");
                    try {
                        int index = sc.nextInt();
                        if (index > appts.size() - 1 || index < 0) {
                            System.out.println("Invalid input! Please enter a number that's displayed on screen.");
                            break;
                        }
                        if (!updatePrescriptionStatus(appts.get(index).getID())) {
                            System.out.println("Invalid ID!");
                            break;
                        }
                        ;
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Must be an integer!");
                        sc.nextLine();
                    }
                    break;
                case 3:
                    medRepo.viewMedicationInventory();
                    break;

                case 4: // Submit replenishment requests
                    System.out.println("Enter the ID of the medicine to request for.");
                    String m_id = InputValidater.getValidString();
                    if (!submitReplenishmentRequest(m_id)) {
                        System.out.println("Invalid ID!");
                    };
                    break;

                case 5:
                    changePassword();
                    break;

                case 6:
                    System.out.println("\nLogging out...");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid option. Please select a number between 1 and 6.");
            }


        }
    }

    /**
     * Called when prescribing medicine (update appointment outcome)
     *
     * @return true when the operation succeeds, false when the operation results in an invalid input.
     */
    private boolean updatePrescriptionStatus(String apptId) {
        System.out.println("Enter the medicine ID to update. To cancel, just press enter (empty)");
        String medicineId;
        try {
            medicineId = sc.next();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Try again.");
            return false;
        }
        if (medicineId.isEmpty()) return true;
        Medicine m;
        try {
            m = medRepo.getMedicineObject(medicineId);
        } catch (RuntimeException e) {
            System.out.println("Medicine does not exist! Try again.");
            return false;
        }
        medRepo.printMedicineStatus(m);

        System.out.println("Enter the quantity of medicine to dispense to the patient.");
        int qtySpecified;
        try {
            qtySpecified = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Try again.");
            return false;
        }
        if (qtySpecified > m.quantity) {
            if (m.topUpRequested) {
                System.out.printf("Insufficient %s (ID: %s) specified. Top-up is in progress. \n", m.displayName, m.id);
            } else {
                System.out.printf("Insufficient %s (ID: %s) specified, automatically requesting for top-up.\n", m.displayName, m.id);
                medRepo.setRequest(m.getID(), true);
            }
            System.out.println("Medicine dispense cancelled.");
            return true;
        }
        medRepo.dispense(m.id, qtySpecified);
        System.out.println("Medicine dispense successful. Displaying new medicine status");
        apptRepo.prescribeMedicine(apptId);
        medRepo.printMedicineStatus(m);
        return true;
    }

    private boolean submitReplenishmentRequest(String medicineId) {
        if (medRepo.setRequest(medicineId, true)) {
            System.out.println("Success");
            return true;
        } else System.out.println("Medicine id does not exist!");
        return false;

    }

    private void printAppointments(List<Appointment> appts) {
        if (appts.isEmpty()) {
            System.out.println("No outstanding appointments found!\n");
            return;
        }
        final String headerFormat = "%-3s|%-15s|%-20s|%-12s|";
        System.out.printf(headerFormat, "No.", "Time Added", "Prescription", "Status");
        System.out.println("-".repeat(headerFormat.length()));
        final String format = "%-3s|%-15s|%-20s|%-12d|";
        for (int i = 0; i < appts.size(); i++) {
            Appointment a = appts.get(i);
            System.out.printf(format, i, a.endTime, a.prescription, a.isPrescribed);
        }
    }

//● View Appointment Outcome Record
//● Update Prescription Status -----------> ??
//● View Medication Inventory ---------> done
//● Submit Replenishment Request --------> done ?
//● Logout

}
