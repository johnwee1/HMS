package repository;

import models.Appointment;
import utils.DateTimeHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
    public boolean createNewAppointment(String startTime, String endTime, int status, String doctor_id) {
        // set patient_id to "" first
        Map<String, Appointment> appointments = defaultViewOnlyDatabase();
        boolean match = appointments.values().stream().anyMatch(appointment -> Objects.equals(appointment.doctor_id,doctor_id) && Objects.equals(appointment.startTime,startTime));
        if (match) {
            System.out.println("Doctor has already created this appointment");
            return false;
        }
        if (DateTimeHandler.isValid(startTime)) {
            Appointment newAppointment = new Appointment(startTime, endTime, status, doctor_id);
            try {
                defaultCreateItem(newAppointment);
            } catch (Exception e) {
                System.out.println("Failed to create appointment object");
                return false;
            }
            return true;
        }
        else {
            System.out.println("Invalid time.");
            return false;
        }
    }

    public void deleteAppointment(String id) {
        defaultDeleteItem(id);
    }

    public void updateAppointment(String id, String startTime, String endTime, Integer type, Integer status, String patient_id, String doctor_id) {
        Appointment curappt = defaultReadItem(id);
        if (curappt == null){
            System.out.println("appointment not found");
            return;
        }
        if (startTime != null) {
            curappt.startTime = startTime;
        }
        if (endTime != null) {
            curappt.endTime = endTime;
        }
        if (type != null) {
            curappt.appointmentType = type;
        }
        if (status != null) {
            curappt.appointmentStatus = status;
        }
        if (patient_id != null) {
            curappt.patient_id = patient_id;
        }
        if (doctor_id != null) {
            curappt.doctor_id = doctor_id;
        }
        defaultUpdateItem(curappt);
    } //usage: to update fields, add in the value to the fields that need to be changed. else add null.

    public void completeAppointment(String id, String prescription, String diagnosis, Integer type) {
        Appointment curappt = defaultReadItem(id);
        curappt.appointmentStatus = 3;
        if (type != null){
            curappt.appointmentType = type;
        }
        if (prescription != null) {
            curappt.isPrescribed = 1;
            curappt.prescription = prescription;
        }
        if (diagnosis != null) {
            curappt.diagnosis = diagnosis;
        }
        defaultUpdateItem(curappt);
    } //usage: if no prescription/diagnosis put null

    public boolean prescribeMedicine(String appointmentId) {
        try {
            Appointment curappt = defaultReadItem(appointmentId);
            curappt.isPrescribed = 2;
            defaultUpdateItem(curappt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public List<Appointment> filterAppointments(String patID, String docID, Integer statusInt, String startTime, Integer isPrescribed) {
        ArrayList<Appointment> filteredList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((k, appt) -> {
            boolean matches = true;
            if (patID != null) {
                matches = matches && Objects.equals(appt.patient_id, patID) ;
            }
            if (docID != null) {
                matches = matches && Objects.equals(appt.doctor_id, docID);
            }
            if (statusInt != null) {
                matches = matches && appt.appointmentStatus == statusInt;
            }
            if (startTime != null) {
                matches = matches && Objects.equals(appt.startTime, startTime);
            }
            if (isPrescribed != null){
                matches = matches && appt.isPrescribed == isPrescribed;
            }
            if (matches) {
                filteredList.add(appt);
            }


        });
        return filteredList;
    }


}
