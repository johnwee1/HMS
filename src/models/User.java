package models;

import utils.PasswordHelper;

/**
 * Generic schema for users. User class is only used to store password hashes, but the user IDs and roles are consistent
 * with other databases.
 *
 *
 */
public class User implements IdentifiedObject {
    public String id;
    public String passwordHash;

    public User() {}

    /**
     * When creating new user, constructor will also hash the provided string and store it
     * @param username
     * @param passwordString password in natural string
     * @param role
     */
    public User(String username, String passwordString, String role){
        this.id=username;
        passwordHash= PasswordHelper.hash(passwordString);
        this.role=role;
    }

    /**
     * Either admin, pharmacist, patient or doctor.
     * Will control which menu you get
     */
    public String role;
    public String getID(){
        return id;
    }
}
