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
        if (password.isEmpty()) return false;
        // add more rules if necessary.
        return true;
    }



}

