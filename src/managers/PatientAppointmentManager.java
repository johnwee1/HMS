package managers;

import models.Appointment;
import repository.AppointmentRepository;
import java.util.List;

public class PatientAppointmentManager {
    public List<Appointment> checkAvaliablity(AppointmentRepository repo) {
        return repo.filterAppointments(null,null,1, null,null);
    }

    public List<Appointment> checkBooked(AppointmentRepository repo, String patId) {
        return repo.filterAppointments(patId, null, 0, null,null);
    }

    public void bookAppointment(AppointmentRepository repo, String apptID, String patId) {
        repo.updateAppointment(apptID,null,null,null,2, patId, null);
    }

    public void cancelAppointment(AppointmentRepository repo, String apptID){
        //operates based on list of booked appointments
        repo.updateAppointment(apptID,null,null, null, 1, "", null);
    }
}
