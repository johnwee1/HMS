/**
 * Public Schema class for the Appointment object.
 * the names of the columns are case-sensitive.
 * Will need to implement additional parsing logic to handle time
 */
public class Appointment {
    public int id;
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
