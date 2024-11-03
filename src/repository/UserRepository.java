package repository;

import models.User;
import utils.PasswordHelper;

import java.util.Arrays;

public class UserRepository extends GenericRepository<User> {

    /**
     * Static final array of possible roles defined.
     */
    public static final String[] roles = {"admin","patient","pharmacist", "doctor"};

    public UserRepository(String csv_filepath) {
        super(User.class, csv_filepath);
    }


    /**
     * Gets the role as a string. Throws an exception if the role string is not found in the user object.
     * @param username username to check
     * @return role as a lower-case string
     */
    public String getUserRole(String username){
        String role = db.get(username).role.toLowerCase();
        if (!Arrays.asList(roles).contains(role))
            throw new RuntimeException("Role is not defined in the list of defined roles!");
        return role;
    }

    // TODO: Instead of returning false, return custom exceptions so its prettier.
    public boolean createNewUser(String username, String passwordString, String role) {
        if (db.containsKey(username)) return false;
        if (!Arrays.asList(roles).contains(role)) return false;
        User newUser = new User(username,passwordString,role);
        defaultCreateItem(newUser);
        return true;
    }

    /**
     * Changes the password of the selected user. The underlying CSV file is then saved.
     * @param username username, aka id
     * @param newPassword new password string
     * @return True if password is successfully changed, false if password doesn't meet requirements.
     */
    public boolean updateUserPassword(String username, String newPassword){
        if (!PasswordHelper.passwordRuleValidator(newPassword)) return false;
        User user = defaultReadItem(username);
        user.passwordHash = PasswordHelper.hash(newPassword);
        defaultUpdateItem(user);
        return true;
    }

    /**
     * Delete's the user with the given username. Only used in testing for now
     * @param username username (id) of the user.
     * @return
     */
    public void deleteUser(String username){
        defaultDeleteItem(username);
    }

    /**
     * Retrieves the user object with a given username. only used in testing for now
     * @param username
     * @return
     */
    public User getUserObject(String username){
        return defaultReadItem(username);
    }

}
