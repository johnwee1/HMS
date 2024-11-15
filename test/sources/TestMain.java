import menus.MainMenu;

public class TestMain {
    final static String users = "test_users.csv";
    final static String appointments = "test_appointments.csv";
    final static String medicines = "test_medicines.csv";
    final static String staff = "test_staff.csv";
    final static String patients = "test_patients.csv";

    public static String f(String filename) {
        return java.nio.file.Paths.get(System.getProperty("user.dir"), "test","resources", filename).toString();
    }

    // main entry point into program
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(
                f(users),
                f(appointments),
                f(medicines),
                f(staff),
                f(patients)
        );
        mainMenu.run();
    }
}
