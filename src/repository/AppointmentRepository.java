package repository;

import models.Appointment;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    public boolean createNewAppointment(String startTime, String endTime, int type, int status, String patient_id, String doctor_id) {
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

    public void deleteAppointment(String id) {
        defaultDeleteItem(id);
    }

    public void updateAppointment(String id, String startTime, String endTime, Integer type, Integer status, String patient_id, String doctor_id) {
        Appointment curappt = defaultReadItem(id);
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
    } //usage: to update fields, add in the value to the fields that need to be changed. else add null.

    public void completeAppointment(String id, String prescription, String diagnosis) {
        Appointment curappt = defaultReadItem(id);
        curappt.appointmentStatus = 3;
        if (prescription != null) {
            curappt.isPrescribed = true;
            curappt.prescription = prescription;
        }
        if (diagnosis != null) {
            curappt.diagnosis = diagnosis;
        }
    } //usage: if no prescription/diagnosis put null

    public ArrayList<String> listByPatID(String patID) {
        ArrayList<String> patList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((k, v) -> {
            if (Objects.equals(v.patient_id, patID)) {
                patList.add(v.id);
            }
        });
        return patList;
    }

    public ArrayList<String> listByDocID(String docID){
        ArrayList<String> docList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((k,v) -> {
            if (Objects.equals(v.doctor_id,docID)){
                docList.add(v.id);
            };
        });
        return docList;
    }

    public ArrayList<String> listByStatus(int statusInt){
        ArrayList<String> statusList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((k,v) -> {
            if (v.appointmentStatus == statusInt){
                statusList.add(v.id);
            };
        });
        return statusList;
    }

}
