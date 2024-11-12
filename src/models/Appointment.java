package models;

import jdk.jshell.spi.SPIResolutionException;

import java.util.UUID;

/**
 * Public Schema class for the models.Appointment object.
 * the names of the columns are case-sensitive.
 * Will need to implement additional parsing logic to handle time, and I'm not sure if we want to actually put that logic into this class or a separate TimeParser class
 */
public class Appointment implements IdentifiedObject {
    public String id;
    public String startTime; //format: DDMMYYHH (in 24hr format) // we enforce appointments to be in 1hr blocks (CLI)
    public String endTime; //format: DDMMYYHH (in 24hr format)
    public int appointmentType;
    public int appointmentStatus; // 0: booked 1: avaliable 2: pending 3: completed
    public String patient_id;
    public String doctor_id;
    public int isPrescribed; // 0: No prescription 1: Pending prescription 2: Completed prescription
    public String diagnosis;
    public String prescription;

    public Appointment(){};
    public Appointment(String startTime, String endTime, int status, String doctor_id){
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentType = -1;
        this.appointmentStatus = status;
        this.patient_id = "";
        this.doctor_id = doctor_id;
        this.isPrescribed = 0;
        this.diagnosis = "";
        this.prescription = "";
        // set prescription and diagnosis only after completion
    }

    public String getID(){
        return id;
    }

    public void setPrescription(String prescription) {
        this.isPrescribed = 2;
        this.prescription = prescription;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPatient_ID(){return patient_id;}

    public void setPatient_ID(String id){this.patient_id = id;}
}
