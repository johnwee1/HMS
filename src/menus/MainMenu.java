package menus;

import repository.UserRepository;
import models.User;
import utils.InputValidater;
import utils.PasswordHelper;

import java.awt.event.MouseWheelEvent;
import java.util.Scanner;

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

        System.out.println("Login with username:");
        String username = InputValidater.getValidString(); //
        System.out.println("Password:");
        String password = InputValidater.getValidString(); //
        User user;
        while (true) {
            try {
                user = userRepository.getUserObject(username);
                break;
            } catch (RuntimeException e) {
                System.out.println("user does not exist");
            }
        }
        int attempts=0;
        for (;attempts<5;attempts++) {
            if (!PasswordHelper.verify(password, user.passwordHash)) {
                System.out.println("Invalid password. Please try again.");
            } else break;
        }
        if (attempts >=5) {
            System.out.println("Login too many times. goodbye!");
            exit(0); // exit program
        }

        System.out.println("Login successful!");
        String role = userRepository.getUserRole(username);

        // REPLACE WITH FACTORY
        MenuFactory mf = new MenuFactory(
                csv_appts,
                csv_medicines,
                csv_staff,
                csv_patients
        );
        Menu menu  = mf.createMenu(role, user.id);
        menu.userInterface();
    }
}
