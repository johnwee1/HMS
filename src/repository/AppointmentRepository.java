package repository;

import models.Appointment;

import java.lang.reflect.Field;


/**
 * databases.AppointmentRepository handles loading and saving of csv files. Other appointment related methods will go in here.
 */
public class AppointmentRepository extends GenericRepository<Appointment> {

    public AppointmentRepository(String csv_filepath) {
        super(Appointment.class, csv_filepath);
    }

    // for testing, but can prettify later down the line when we actually need to implement this
    public void printAllAppointments() {
        defaultViewOnlyDatabase().forEach((k, v) -> {
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

    // all methods for doctors
    public boolean createNewAppointment(String startTime, String endTime, int type, int status, String patient_id, String doctor_id){
        // set patient_id to "" first
        Appointment newAppointment = new Appointment(startTime, endTime, type, status, patient_id, doctor_id);
        try {
            defaultCreateItem(newAppointment);
        } catch (Exception e) {
            System.out.println("failed to create appointment object");
            return false;
        }
        return true;
    }
}
