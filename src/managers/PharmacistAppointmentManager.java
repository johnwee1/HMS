package managers;

import models.Appointment;
import repository.AppointmentRepository;

import java.util.List;

public class PharmacistAppointmentManager {
    public List<Appointment> checkOutcomeRecord(AppointmentRepository repo, String patId) {
        return repo.filterAppointments(patId, null, 0, null, 1);
    }
    public void completePrescription(AppointmentRepository repo, String patId){
        repo.prescribeMedicine(patId);
    }
}
