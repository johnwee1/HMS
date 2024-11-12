package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeHandler {
    public static boolean isValid(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHH");
        try{
            LocalDateTime.parse(date, formatter);
            return true;
        }
        catch (DateTimeParseException e){
            return false;
        }
    }
}
