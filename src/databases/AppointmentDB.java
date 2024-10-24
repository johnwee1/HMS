package databases;

import models.Appointment;

import java.lang.reflect.Field;


/**
 * databases.AppointmentDB handles loading and saving of csv files. Other appointment related methods will go in here.
 */
public class AppointmentDB extends AbstractDB<Appointment> {

    public AppointmentDB(String csv_filepath) {
        super(Appointment.class, csv_filepath);
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