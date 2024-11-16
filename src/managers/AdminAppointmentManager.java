package managers;
import models.Appointment;
import repository.AppointmentRepository;

import java.util.List;

public class AdminAppointmentManager {
    public List<Appointment> viewAllAppointments(AppointmentRepository repo) {
        return repo.filterAppointments(null,null,null, null, null);
    }

    public List<Appointment> viewAllAppointmentsByPat(AppointmentRepository repo, String pat_id) {
        return repo.filterAppointments(pat_id,null,null, null, null);
    }

    public List<Appointment> viewAllAppointmentsByDoc(AppointmentRepository repo, String doc_id) {
        return repo.filterAppointments(null,doc_id,null, null, null);
    }

    public List<Appointment> viewScheduledAppointments(AppointmentRepository repo) {
        return repo.filterAppointments(null,null,0,null,null);
    }

    public List<Appointment> viewCompletedAppointments(AppointmentRepository repo) {
        return repo.filterAppointments(null,null,3,null,null);
    }
}
