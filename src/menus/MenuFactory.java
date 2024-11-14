package menus;

public class MenuFactory {
    public static Menu createMenu(String role) {
        Menu menu;
        switch (role.toLowerCase()) {
            case "patient" -> menu = new PatientMenu();
            case "doctor" -> menu = new DoctorMenu();
            case "pharmacist" -> menu =  new PharmacistMenu();
            case "admin" -> menu = new AdminMenu();
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
        return menu;
    }
}