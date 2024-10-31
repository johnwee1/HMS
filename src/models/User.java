package models;

import utils.PasswordHelper;

public class User implements IdentifiedObject {
    public User() {};
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
