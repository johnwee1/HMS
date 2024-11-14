package menus;

import repository.UserRepository;
import models.User;
import utils.PasswordHelper;
import java.util.Scanner;

public class DefaultLogin {
    public static void main(String[] args){
       Scanner s = new Scanner(System.in);

        System.out.println("Hospital Management System");

        System.out.println("Login with username:");
        String username = s.next();
        UserRepository userRepository = new UserRepository();
        System.out.println("Password:");
        String password = s.next();

        try {
            //Fetch user from repo
            User user = userRepository.getUserObject(username);

            //Verify password
            if (!PasswordHelper.verify(password,user.passwordHash)){
                System.out.println("Invalid password. Please try again.");
                return;
            }

            System.out.println("Login successful!");

            String role = userRepository.getUserRole(username);

            //Get user to confirm role
            System.out.println("Please confirm that you are a " + role + " (y/n): ");
            String userConfirmation = s.nextLine().trim().toLowerCase();

            //Handles any input that is not "y"
            if (!userConfirmation.equals("y")){
                System.out.println("Your role saved in the system does not match. Please try again.");
                return;
            }

            //switch cases for routing to specific CLIs
            switch(role){
                case "patient" -> new PatientMenu().run();
                case "doctor" -> new DoctorMenu().run();
                case "pharmacist" -> new PharmacistMenu.run();
                case "admin" -> new AdminMenu.run();
                default -> System.out.println("Role unknown. Please try again.");
            }

        } catch (RuntimeException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}
