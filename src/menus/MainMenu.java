package menus;

import repository.UserRepository;
import models.User;
import utils.InputValidater;
import utils.PasswordHelper;

import static java.lang.System.exit;

public class MainMenu {
    String csv_users;
    String csv_appts;
    String csv_medicines;
    String csv_staff;
    String csv_patients;

    public MainMenu(String users, String appts, String medicines, String staff, String patients) {
        this.csv_users = users;
        this.csv_appts=appts;
        this.csv_medicines=medicines;
        this.csv_staff=staff;
        this.csv_patients=patients;
    }

    public void run(){
        final UserRepository userRepository = new UserRepository(csv_users);

        System.out.println("Hospital Management System");
        User user;
        String username;
        while (true) {
            System.out.println("Login with username:");
            try {
                username = InputValidater.getValidString();
                user = userRepository.getUserObject(username);
                break;
            } catch (RuntimeException e) {
                System.out.println("user does not exist! Try again.");
            }
        }
        System.out.println("Password:");
        String password = InputValidater.getValidString();
        int attempts=0;
        for (;attempts<5;attempts++) {
            if (!PasswordHelper.verify(password, user.passwordHash)) {
                System.out.println("Invalid password. Please try again.");
                password = InputValidater.getValidString();
            } else break;
        }
        if (attempts >=5) {
            System.out.println("Login too many times. Goodbye!");
            exit(0); // exit program
        }

        System.out.println("Login successful!");
        String role = userRepository.getUserRole(username);

        MenuFactory mf = new MenuFactory(
                userRepository,
                csv_appts,
                csv_medicines,
                csv_staff,
                csv_patients
        );
        Menu menu  = mf.createMenu(role, user.id);
        menu.userInterface();
    }
}
