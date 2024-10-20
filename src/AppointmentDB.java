import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

public class AppointmentDB {
    //default constructor is sufficient
    public HashMap<Integer,Appointment> db;
    public void load_database(String appointment_filename){
        try {
            db=DBLoader.load_txt(appointment_filename,Appointment.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load AppointmentDB: "+e);
        }
    }

    public void save_database(String appointment_filename){
        DBLoader.save_txt(appointment_filename,db);
    }

    // for testing
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
