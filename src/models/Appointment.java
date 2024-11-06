package models;

import java.util.UUID;

/**
 * Public Schema class for the models.Appointment object.
 * the names of the columns are case-sensitive.
 * Will need to implement additional parsing logic to handle time, and I'm not sure if we want to actually put that logic into this class or a separate TimeParser class
 */
public class Appointment implements IdentifiedObject {
    public String id;
    public String startTime; //format: DDMMYY:HH (in 24hr format)
    public String endTime; //format: DDMMYY:HH (in 24hr format)
    public int appointmentType;
    public int appointmentStatus; // 0: booked 1: avaliable 2: pending 3: completed
    public String patient_id;
    public String doctor_id;
    public boolean isPrescribed;
    public String diagnosis;
    public String prescription;

    public Appointment(){};
    public Appointment(String startTime, String endTime, int type, int status, String patient_id, String doctor_id){
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentType = type;
        this.appointmentStatus = status;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.isPrescribed = false;
        this.diagnosis = "";
        this.prescription = "";
        // set prescription and diagnosis only after completion
    }

    public String getID(){
        return id;
    }
}
