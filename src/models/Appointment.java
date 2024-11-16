package models;

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
    public int appointmentType; // 0: Consultation 1: X-Ray 2: Blood Test
    public int appointmentStatus; // 0: booked 1: available 2: pending 3: completed 4: leaves for doctors
    public String patient_id;
    public String doctor_id;
    public int prescriptionStatus; // 0: No prescription 1: Pending prescription 2: Completed prescription
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
        this.prescriptionStatus = 0;
        this.diagnosis = "";
        this.prescription = "";
        // set prescription and diagnosis only after completion
    }

    public String getID(){
        return id;
    }

    public void setPrescription(String prescription) {
        this.prescriptionStatus = 2;
        this.prescription = prescription;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /*HELPER METHODS FOR PRESCRIPTION STATE MANAGEMENT*/

    /**
     * Prints the prescription id in readable string format
     * @return string
     */
    public String prescriptionIdToString(){
        return switch (this.prescriptionStatus){
            case 1->"None";
            case 2->"Pending";
            case 3->"Prescribed";
            default -> "Invalid";
        };
    }

    /**
     * Formats the given time stored in the appointment model as a string to read
     * @param time
     * @return readable string in "day/mo/yr hr:00" format
     */
    public String timeToDisplayString(String time){
        if (time == null || time.length() != 8) {
            return null;
        }
        String day = time.substring(0, 2);
        String month = time.substring(2, 4);
        String year = time.substring(4, 6);
        String hour = time.substring(6, 8);

        return String.format("%s/%s/%s %s:00", day, month, year, hour);
    }
}
