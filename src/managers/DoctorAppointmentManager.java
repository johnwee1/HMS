package managers;

import models.Appointment;
import repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoctorAppointmentManager {

//    public List<Appointment> checkCompleted(AppointmentRepository repo, String docID){
//        return repo.filterAppointments(null, docID,3, null, null);
//    }

    public List<Appointment> checkPending(AppointmentRepository repo, String docID){
        return repo.filterAppointments(null, docID,2, null, null);
    }

    public List<Appointment> checkBooked(AppointmentRepository repo, String docID){
        return repo.filterAppointments(null, docID,0, null, null);
    }

    public List<Appointment> viewLeaves(AppointmentRepository repo, String docID) {
        return repo.filterAppointments(null, docID,4, null, null);
    }

    public Set<String> patientsUnderCare(AppointmentRepository repo,String docID){
        List<Appointment> list = repo.filterAppointments(null,docID,null,null,null);
        Set<String> patients = new HashSet<>();
        for (Appointment appointment : list) {
            patients.add(appointment.patient_id);
        }
        patients.remove("");
        return patients;
    }

    public void acceptAppointment(AppointmentRepository repo, String apptID){
        repo.updateAppointment(apptID,null,null,null,0,null,null);
    }

    public void declineAppointment(AppointmentRepository repo, String apptID){
        repo.updateAppointment(apptID,null,null,null,1,null,null);
    }

    public boolean createAppointment(AppointmentRepository repo, String start, String end, String docID, int status) {
        if (repo.createNewAppointment(start, end, status, docID)) {
            if (status == 1) {
                System.out.println("Appointment created successfully.");
                return true;
            }
            return status == 4;
        } else {
            System.out.println("Failed to create the appointment.");
            return false;
        }//resolving no return statement error, no use because above is binary
    }


    public void completeAppointment(AppointmentRepository repo,String id, String prescription, String diagnosis, Integer type){
        //if no prescription put null
        repo.completeAppointment(id, prescription, diagnosis, type);
    }
}
