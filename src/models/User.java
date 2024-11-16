package models;

import utils.PasswordHelper;

public class User implements IdentifiedObject {
    public User() {};

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
    public String id;
    public String passwordHash;
    public String role;
    public String getID(){
        return id;
    }
}
