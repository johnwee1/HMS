package managers;

import models.Appointment;
import repository.AppointmentRepository;

import java.util.List;

public class DoctorAppointmentManager {
    public List<Appointment> checkPending(AppointmentRepository repo, String docID){
        return repo.filterAppointments(null, docID,2, null, null);
    }

    public List<Appointment> checkBooked(AppointmentRepository repo, String docID){
        return repo.filterAppointments(null, docID,0, null, null);
    }

    public List<Appointment> checkCompleted(AppointmentRepository repo, String docID){
        return repo.filterAppointments(null, docID,3, null, null);
    }

    public void acceptAppointment(AppointmentRepository repo, String apptID){
        repo.updateAppointment(apptID,null,null,null,0,null,null);
    }

    public void declineAppointment(AppointmentRepository repo, String apptID){
        repo.updateAppointment(apptID,null,null,null,1,null,null);
    }

    public void createAppointment(AppointmentRepository repo,String start, String end, String docID){
        repo.createNewAppointment(start,end,1,docID);
    }

    public void completeAppointment(AppointmentRepository repo,String id, String prescription, String diagnosis, Integer type){
        //if no prescription put null
        repo.completeAppointment(id, prescription, diagnosis, type);
    }
}
