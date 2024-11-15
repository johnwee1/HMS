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

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, String id){
        super(repo, id);
        this.med_repo = med_repo;
    }

    @Override
    public void userInterface(){
        Scanner sc = new Scanner(System.in);
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

                switch (choice) {
                    case 1:
                        List<Appointment> appts = pharmacistAppointmentManager.checkOutstandingRecords(repo);
                        String format = "%-20s|%-15s|%-10s|%-12d|";
                        for (Appointment a : appts) {
                            System.out.format(format, a.id, a.endTime, a.prescription, a.isPrescribed);
                        }
                        break;

                    case 2:
                        updatePrescriptionStatus();
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

    private void updatePrescriptionStatus(){
        pharmacistAppointmentManager.completePrescription(repo, id);
//        med_repo.dispense()
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
