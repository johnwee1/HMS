package models;

/**
 * Public Schema class for the models.Appointment object.
 * the names of the columns are case-sensitive.
 * Will need to implement additional parsing logic to handle time, and I'm not sure if we want to actually put that logic into this class or a separate TimeParser class
 */
public class Appointment {
    public String id;
    public String startTime;
    public String endTime;
    public int appointmentType;
    public int appointmentStatus;
    public int patient_id;
    public int doctor_id;
    public boolean isPrescribed;
    public String diagnosis;
    public String prescription;
}
