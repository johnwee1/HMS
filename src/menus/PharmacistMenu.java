package menus;

import models.Medicine;
import repository.AppointmentRepository;
import repository.MedicineRepository;

import java.util.Map;

public class PharmacistMenu extends Menu {

    private MedicineRepository med_repo;

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, String id){
        super(repo, id);
        this.med_repo = med_repo;
    }

    @Override
    public void userInterface(){
    }

//    public void viewMedicationInventory(){
//        Map<String, Medicine> db = med_repo.defaultViewOnlyDatabase();
//        String format = "%-20s|%-15s|%-10d|%-12d|%-15b%n";;
//        System.out.println("MEDICINE INVENTORY--------------------------------");
//        System.out.println("--------------------------------------------------");
//        System.out.format("%-20s|%-15s|%-10s|%-12s|%-15s%n", "Display Name","System ID", "Quantity", "Alert Level", "Alerted?");
//        System.out.println("--------------------------------------------------");
//        for (Medicine m : db.values()){
//            System.out.format(format, m.displayName, m.id, m.quantity, m.alertLevel, m.topUpRequested);
//        }
//    }

//            ● View Appointment Outcome Record
//● Update Prescription Status
//● View Medication Inventory
//● Submit Replenishment Request
//● Logout

}
