package menus;

import managers.PharmacistAppointmentManager;
import models.Appointment;
import models.Medicine;
import repository.AppointmentRepository;
import repository.MedicineRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PharmacistMenu extends Menu {

    private PharmacistAppointmentManager pharmacistAppointmentManager = new PharmacistAppointmentManager();
    private MedicineRepository med_repo;
    private Scanner sc = new Scanner(System.in);

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, String id){
        super(repo, id);
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

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // Clear the buffer
                List<Appointment> appts = pharmacistAppointmentManager.checkOutstandingRecords(repo);
                String format = "%-3s|%-15s|%-10s|%-12d|";

                switch (choice) {
                    case 1:
                        for (int i = 0; i < appts.size(); i++) {
                            Appointment a = appts.get(i);
                            System.out.format(format, i, a.endTime, a.prescription, a.isPrescribed);
                        }
                        break;

                    case 2:
                        for (int i = 0; i < appts.size(); i++) {
                            Appointment a = appts.get(i);
                            System.out.format(format, i, a.endTime, a.prescription, a.isPrescribed);
                        }
                        System.out.println("Enter the ID of the appointment for prescription");
                        int index = sc.nextInt();
                        updatePrescriptionStatus(index);
                        break;

                    case 3:
                        med_repo.viewMedicationInventory();
                        break;

                    case 4:
                        submitReplenishmentRequest();
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
                sc.nextLine(); // Clear the buffer
            }
        }
    }

    private void updatePrescriptionStatus(int index){
        System.out.println("Enter the medicine ID to update");
        String medicineId = sc.next();
        // display medicine information
        System.out.println("Enter the quantity of medicine that was prescribed to the patient.");
        System.out.println("If the quantity is insufficient, the medicine will not be dispensed and will automatically request for top up.");
    }

    private void submitReplenishmentRequest(){
//        med_repo.setRequest();
    }

//● View Appointment Outcome Record
//● Update Prescription Status -----------> ??
//● View Medication Inventory ---------> done
//● Submit Replenishment Request --------> done ?
//● Logout

}
