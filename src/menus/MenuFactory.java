package menus;

import repository.AppointmentRepository;
import repository.MedicineRepository;

public class MenuFactory {
    static AppointmentRepository apptRepo = new AppointmentRepository("FILEPATH GOES HERE");
    static MedicineRepository medRepo = new MedicineRepository("FILEPATH GOES HERE");

    public static Menu createMenu(String role, String userId) {
        Menu menu;
        switch (role.toLowerCase()) {
            case "patient" -> menu = new PatientMenu();
            case "doctor" -> menu = new DoctorMenu();
            case "pharmacist" -> menu =  new PharmacistMenu(medRepo, apptRepo, userId);
            case "admin" -> menu = new AdminMenu();
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
        return menu;
    }
}