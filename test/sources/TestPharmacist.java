import menus.Menu;
import menus.PharmacistMenu;
import org.junit.jupiter.api.Test;
import repository.AppointmentRepository;
import repository.MedicineRepository;
import repository.StaffRepository;
import repository.UserRepository;

public class TestPharmacist {
    final static String id = "pharmacist1";
    final static String users = "test_users.csv";
    final static String appointments = "test_appointments.csv";
    final static String medicines = "test_medicines.csv";
    final static String staff = "test_staff.csv";
    final static String patients = "test_patients.csv";

    public static String f(String filename) {
        return java.nio.file.Paths.get(System.getProperty("user.dir"), "test","resources", filename).toString();
    }



    public static void main(String[] args) {
        MedicineRepository medRepo = new MedicineRepository(f(medicines));
        UserRepository userRepo = new UserRepository(f(users));
        AppointmentRepository apptRepo = new AppointmentRepository(f(appointments));

        PharmacistMenu m = new PharmacistMenu(medRepo, apptRepo,userRepo, id);
        m.userInterface();
    }

}

