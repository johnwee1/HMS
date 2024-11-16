package menus;

import repository.*;

public class MenuFactory {
    String csv_appts;
    String csv_medicines;
    String csv_staff;
    String csv_patients;
    AppointmentRepository apptRepo;
    UserRepository userRepo;
    PatientRepository patientRepo;
    MedicineRepository medRepo;
    StaffRepository staffRepo;

    /**
     * Generic factory class to create a new menu to display to the user, depending on their role.
     * Additionally, creates a default user if none exists yet (since staff repository is only initialized here.)
     * @param userRepo
     * @param appts
     * @param medicines
     * @param staff
     * @param patients
     */
    MenuFactory(UserRepository userRepo, String appts, String medicines, String staff, String patients) {
        this.userRepo = userRepo;
        this.csv_appts = appts;
        this.csv_medicines=medicines;
        this.csv_staff=staff;
        this.csv_patients=patients;
        apptRepo = new AppointmentRepository(csv_appts);
        medRepo = new MedicineRepository(csv_medicines);
        staffRepo = new StaffRepository(csv_staff);
        patientRepo = new PatientRepository(csv_patients);
        medRepo = new MedicineRepository(csv_medicines);
        if (staffRepo.defaultViewOnlyDatabase().isEmpty()){
            // create new admin user, if admin user does not exist.
            staffRepo.createNewStaff("admin","Administrator","admin",0,true);
        }
    }


    public Menu createMenu(String role, String userId) {
        Menu menu;
        switch (role.toLowerCase()) {
            case "patient" -> menu = new PatientMenu(userId, apptRepo,staffRepo,userRepo,patientRepo);
            case "doctor" -> menu = new DoctorMenu(apptRepo,staffRepo,userId,userRepo,patientRepo);
            case "pharmacist" -> menu =  new PharmacistMenu(medRepo, apptRepo,userRepo, userId);
            case "admin" -> menu = new AdminMenu(userId,apptRepo,staffRepo,patientRepo,medRepo,userRepo);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
        return menu;
    }
}