package utils;

import models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class PasswordHelper {
    private static final String salt = "HMS";

    /**
     *
     * @param password unhashed password
     * @return hashed password equivalent as a string
     */
    public static String hash(String password){
        password = password+salt;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashedpwd = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedpwd); // base64 guarantees no commas inside return
    }

    /**
     * Function to check password if it meets the criteria
     * @param password
     * @return true if password requirements are met
     */
    public static boolean passwordRuleValidator(String password){
        return !password.isEmpty();
        // add more rules if necessary.
    }

    /**
     * Function to verify if the entered password matches the stored hashed password
     * @param enteredPassword the plain text password entered by user
     * @param storedHashedPassword the hashed password stored
     * @return True if passwords match, false if they do not
     */
    public static boolean verify(String enteredPassword, String storedHashedPassword){
        //Hash the entered password
        String hashedEnteredPassword = hash(enteredPassword);
        //Compare with stored hashed password
        return hashedEnteredPassword.equals(storedHashedPassword);
    }

}

