package menus;

import repository.AppointmentRepository;
import repository.UserRepository;
import utils.InputValidater;

public abstract class Menu {
    AppointmentRepository repo;
    String id;

    public Menu(AppointmentRepository repo, String id){
        this.repo = repo;
        this.id = id;
    }

    public abstract void userInterface();

    public void changePassword(){
        
        //Ask for current password
        System.out.println("Enter your current password: ");
        String currentPassword = InputValidater.getValidString();

        //Verify the current password
        if(!userRepo.verifyCurrentPassword(id, currentPassword)){
            System.out.println("Your current password is incorrect. Please try again.");
            return;
        }

        //Ask for the new password
        System.out.println("Enter your new password: ");
        String newPassword = InputValidater.getValidString();

        //Confirm the new password
        System.out.println("Confirm your new password: ");
        String confirmPassword = InputValidater.getValidString();

        //Check if newPassword and confirmPassword match
        if(!newPassword.equals(confirmPassword)){
            System.out.println("Passwords do not match. Please try again.");
            return;
        }
        
        if(!userRepo.updateUserPassword(id, newPassword)) {
            System.out.println("Password updated successfully!");
        }
        }
    }
}
