package models;

import java.util.UUID;

/**
 * Public Schema class for the models.Appointment object.
 * the names of the columns are case-sensitive.
 *
 */
public class Appointment implements IdentifiedObject {
    /**
     * Unlike other IDs, Appointment IDs are generated UUIDs to prevent clashes during generation.
     */
    public String id;

    /**
     * format: DDMMYY HH (in 24hr format). We enforce appointments in the appointment schema to be in 1hr blocks.
     */
    public String startTime;
    /**
     * As above
     */
    public String endTime;
    /**
     * 0: Consultation 1: X-Ray 2: Blood Test
     */
    public int appointmentType;
    /**
     * 0: booked 1: available 2: pending 3: completed 4: leaves for doctors
     */
    public int appointmentStatus;
    public String patient_id;
    public String doctor_id;
    /**
     *  0: No prescription 1: Pending prescription 2: Completed prescription
     */
    public int prescriptionStatus;
    /**
     * Readable diagnosis given by the doctor for the particular appointment
     */
    public String diagnosis;
    /**
     * Readable prescription given by the doctor for the pharmacist to read. Not linked to actual medicine IDs.
     */
    public String prescription;

    public Appointment(){}

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
        if (time.length() != 9) {
            // default catch all for incompatibility
            return time;
        }
        String day = time.substring(0, 2);
        String month = time.substring(2, 4);
        String year = time.substring(4, 6);
        String hour = time.substring(7, 9);

        return String.format("%s/%s/%s %s:00", day, month, year, hour);
    }
}
