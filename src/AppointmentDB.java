import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * AppointmentDB handles loading and saving of csv files. Other appointment related methods will go in here.
 */
public class AppointmentDB extends InterfaceDB {
    //default constructor is sufficient
    public HashMap<Integer,Appointment> db;

    /**
     * Loads `appointment_filename` as a CSV. have to pass the absolute path of the file.
     * @param appointment_filename
     */
    public void loadDatabase(String appointment_filename){
        try {
            db=DBLoader.loadCSV(appointment_filename,Appointment.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AppointmentDB: "+e);
        }
    }

    /**
     * Saves `appointment_filename` as a CSV. Have to pass absolute path of the file.
     * @param appointment_filename
     */
    public void saveDatabase(String appointment_filename){
        DBLoader.saveCSV(appointment_filename,db);
    }

    // for testing, but can prettify later down the line when we actually need to implement this
    public void printAllAppointments() {
        db.forEach((k, v) -> {
            System.out.println("Key= " + k);
            for (Field f : v.getClass().getFields()) {
                try {
                    System.out.println(f.getName() + ": " + f.get(v));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
