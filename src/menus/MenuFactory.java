package menus;

import repository.AppointmentRepository;
import repository.MedicineRepository;
import repository.PatientRepository;
import repository.StaffRepository;

public class MenuFactory {
    String csv_appts;
    String csv_medicines;
    String csv_staff;
    String csv_patients;
    MenuFactory(String appts, String medicines, String staff, String patients) {
        this.csv_appts=appts;
        this.csv_medicines=medicines;
        this.csv_staff=staff;
        this.csv_patients=patients;
    }
    AppointmentRepository apptRepo = new AppointmentRepository(csv_appts);
    MedicineRepository medRepo = new MedicineRepository(csv_medicines);
    StaffRepository staffRepo = new StaffRepository(csv_staff);
    PatientRepository patientRepo = new PatientRepository(csv_patients);

    public Menu createMenu(String role, String userId) {
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