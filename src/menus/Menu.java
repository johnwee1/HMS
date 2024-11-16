package menus;

import repository.AppointmentRepository;
import repository.UserRepository;
import models.User;
import utils.InputValidater;
import utils.PasswordHelper;

import static java.lang.System.exit;

public abstract class Menu {
    AppointmentRepository apptRepo;
    UserRepository userRepo;
    String id;

    public Menu(AppointmentRepository repo, UserRepository userRepo, String id){
        this.apptRepo = repo;
        this.userRepo = userRepo;
        this.id = id;
    }

    /**
     * Abstract userInterface() implemented by all subclasses
     */
    public abstract void userInterface();

    /**
     * CLI menu to change password common to all menus
     */
    public void changePassword(){

        User user = userRepo.getUserObject(id);

        //Ask for current password
        System.out.println("Enter your current password: ");
        String currentPassword = InputValidater.getValidString();

        //Verify the current password
        int attempts=0;
        for (;attempts<5;attempts++) {
            if (!PasswordHelper.verify(currentPassword, user.passwordHash)) {
                System.out.println("Invalid password. Please try again.");
            } else break;
        }
        if (attempts >=5) {
            System.out.println("Current password failed too many times. Goodbye!");
            exit(0); // exit program
        }

        String newPassword = "";
        String confirmPassword = "";
        attempts = 0;
        for (; attempts < 3; attempts++) {
            System.out.println("Enter your new password: ");
            newPassword = InputValidater.getValidString();
            System.out.println("Confirm your new password: ");
            confirmPassword = InputValidater.getValidString();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            }
            else break;
        }

        // 3 tries, after which exit method without changing the password
        if (attempts == 3) {
            System.out.println("Password reset failed too many times. Goodbye!");
            return;
        }

            //Update password in the UserRepo
        if (userRepo.updateUserPassword(id, newPassword)) {
            System.out.println("Password updated successfully!");
        } else {
                System.out.println("Failed to update password. Please try again.");
        }
    }
}

