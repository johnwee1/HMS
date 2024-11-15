package menus;

import managers.PharmacistAppointmentManager;
import models.Appointment;
import models.Medicine;
import repository.AppointmentRepository;
import repository.MedicineRepository;
import repository.UserRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PharmacistMenu extends Menu {

    private final MedicineRepository med_repo;
    private final PharmacistAppointmentManager pharmacistAppointmentManager = new PharmacistAppointmentManager();
    private final Scanner sc = new Scanner(System.in);

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, UserRepository userRepo, String id){
        super(repo,userRepo, id);
        this.med_repo = med_repo;
    }

    @Override
    public void userInterface(){
        boolean running = true;
        while (running) {
            System.out.println("\n=== Pharmacy Management System ===");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("\nPlease select an option (1-5): ");
            List<Appointment> appts = pharmacistAppointmentManager.checkOutstandingRecords(apptRepo);
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        printAppointments(appts);
                        break;

                    case 2:
                        printAppointments(appts);
                        System.out.println("Enter the ID of the appointment for prescription");
                    try {
                        int index = sc.nextInt();
                        while (!updatePrescriptionStatus(index)){};
                        break;
                    } catch (InputMismatchException e){
                        System.out.println("Invalid input!");
                        break;
                    }
                    case 3:
                        med_repo.viewMedicationInventory();
                        break;

                    case 4: // Submit replenishment requests
                        System.out.println("Enter the ID of the medicine to request for");
                        try {
                            String m_id = sc.next();
                            while (!submitReplenishmentRequest(m_id)){};
                        } catch (InputMismatchException e){
                            System.out.println("Invalid input!");
                        }
                        break;

                    case 5:
                        System.out.println("\nLogging out...");
                        running = false;
                        break;

                    default:
                        System.out.println("\nInvalid option. Please select a number between 1 and 5.");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            }
        }
    }

    /**
     * Called when prescribing medicine (update appointment outcome)
     * @param index
     * @return true when the operation succeeds, false when the operation results in an invalid input.
     */
    private boolean updatePrescriptionStatus(int index){
        System.out.println("Enter the medicine ID to update. To cancel, just press enter (empty)");
        String medicineId;
        try {
            medicineId = sc.next();
        } catch (InputMismatchException e){
            System.out.println("Invalid input! Try again.");
            return false;
        }
        if (medicineId.isEmpty()) return true;
        Medicine m;
        try {
            m = med_repo.getMedicineObject(medicineId);
        } catch (RuntimeException e){
            System.out.println("Medicine does not exist! Try again.");
            return false;
        }
        med_repo.printMedicineStatus(m);

        System.out.println("Enter the quantity of medicine to dispense to the patient.");
        int qtySpecified;
        try {
            qtySpecified = sc.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input! Try again.");
            return false;
        }
        if (qtySpecified > m.quantity){
            if (m.topUpRequested){
                System.out.printf("Insufficient %s (ID: %s) specified. Top-up is in progress. \n", m.displayName, m.id);
            }
            else{
                System.out.printf("Insufficient %s (ID: %s) specified, automatically requesting for top-up.\n", m.displayName, m.id);
                med_repo.setRequest(m.getID(), true);
            }
            System.out.println("Medicine dispense cancelled.");
            return true;
        }
        med_repo.dispense(m.id, qtySpecified);
        System.out.println("Medicine dispense successful. Displaying new medicine status");
        med_repo.printMedicineStatus(m);
        return true;
    }

    private boolean submitReplenishmentRequest(String medicineId){
        if (med_repo.setRequest(medicineId, true)){
            System.out.println("Success");
            return true;
        }
        else System.out.println("Medicine id does not exist!");
        return false;

    }

    private void printAppointments(List<Appointment> appts){
        final String headerFormat = "%-3s|%-15s|%-20s|%-12s|";
        System.out.printf(headerFormat, "No.", "Time Added", "Prescription", "Status");

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
