package managers;

import models.Appointment;
import repository.AppointmentRepository;

import java.util.List;

public class PharmacistAppointmentManager {

    /**
     * check the outcome record for the individual patient
     * @param repo the appointmentRepository repo
     * @param patId patient ID
     * @return
     */
    public List<Appointment> checkOutcomeRecord(AppointmentRepository repo, String patId) {
        return repo.filterAppointments(patId, null, 0, null, 1);
    }

    /**
     * Returns a list of all outstanding appointments with pending prescriptions
     * @param repo appointmentRepo repo object
     * @return
     */
    public List<Appointment> checkOutstandingRecords(AppointmentRepository repo) {
        return repo.filterAppointments(null, null, 0, null, 1);
    }

    /**
     * Mark the patient's prescription as dispensed.
     * @param repo appointment repo
     * @param patId patient id
     */
    public void completePrescription(AppointmentRepository repo, String patId){
        repo.prescribeMedicine(patId);
    }
}
