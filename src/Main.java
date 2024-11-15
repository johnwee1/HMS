import menus.MainMenu;

public class Main {

    final static String users = "users.csv";
    final static String appointments = "appointments.csv";
    final static String medicines = "medicines.csv";
    final static String staff = "staff.csv";
    final static String patients = "patients.csv";

    public static String f(String filename) {
        return java.nio.file.Paths.get(System.getProperty("user.dir"), "data", filename).toString();
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
