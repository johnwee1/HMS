package menus;

import repository.UserRepository;
import models.User;
import utils.InputValidater;
import utils.PasswordHelper;

import static java.lang.System.exit;

/**
 * First entrypoint into the HMS, able to store the different locations of CSV files for easier testing/mocking.
 * Filepaths need to be first preprocessed by the calling class.
 */
public class MainMenu {
    String csv_users;
    String csv_appts;
    String csv_medicines;
    String csv_staff;
    String csv_patients;

    /**
     * Take in all the different csv file paths of the different files below before building the menus.
     * @param users
     * @param appts
     * @param medicines
     * @param staff
     * @param patients
     */
    public MainMenu(String users, String appts, String medicines, String staff, String patients) {
        this.csv_users = users;
        this.csv_appts=appts;
        this.csv_medicines=medicines;
        this.csv_staff=staff;
        this.csv_patients=patients;
    }

    /**
     * Handles the login, then hands off to the respective menu after login.
     */
    public void run(){
        UserRepository userRepository = null;
        try {
            userRepository = new UserRepository(csv_users);
        } catch (RuntimeException e) {
            System.out.println("Data files are not available! The data folder should be in the same folder as the HMS.");
            exit(1);
        }

        System.out.println("Hospital Management System");
        createNewDefaultUser(userRepository);
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

    /**
     * Function to check if no user exists. If none exists, create a new admin user.
     */
    private void createNewDefaultUser(UserRepository userRepository) {
        if (!userRepository.defaultViewOnlyDatabase().isEmpty()) return;
        String DEFAULT_USERNAME = "admin";
        String DEFAULT_PASSWORD = "password";
        userRepository.createNewUser(DEFAULT_USERNAME,DEFAULT_PASSWORD, "admin");
        System.out.println("No users exist! Log in with the default admin account.");
        System.out.println("Username: "+DEFAULT_USERNAME);
        System.out.println("Password: "+DEFAULT_PASSWORD);
    }
}
