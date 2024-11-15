package menus;

import repository.UserRepository;
import models.User;
import utils.PasswordHelper;

import java.awt.event.MouseWheelEvent;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainMenu {
    final static String filename = "test_users.csv";
    final static String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        final UserRepository userRepository = new UserRepository(filepath);

        System.out.println("Hospital Management System");

        System.out.println("Login with username:");
        String username = s.next(); //
        System.out.println("Password:");
        String password = s.next(); //
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
         Menu menu  = MenuFactory.createMenu(role);
         menu.userInterface();
    }
}
