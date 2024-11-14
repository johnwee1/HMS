import models.Appointment;
import org.junit.jupiter.api.*;
import repository.AppointmentRepository;

import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAppointments {
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"), "test", "resources", "appointments.csv").toString();
    AppointmentRepository repo = new AppointmentRepository(filepath);

    @Test
    @Order(1)
    public void TestCreateAppointment() {
        repo.createNewAppointment("12112411","12112212",1,"test_doc");
        repo.createNewAppointment("32112411", "12112411", 1, "test_doc"); //test invalid time
    }

    @Test
    @Order(2)
    public void TestUpdateAppointment() {
        List<Appointment> doc_list = repo.filterAppointments(null,"test_doc",1, null, null);
        for (Appointment appt : doc_list) {
            repo.updateAppointment(appt.getID(),null,null,null,0,"pat_a",null);
        }

    }

    @Test
    @Order(3)
    public void TestCompleteAppointment(){
        List<Appointment> doc_list = repo.filterAppointments(null,"test_doc",0, null, null);
        for (Appointment appt : doc_list) {
            repo.completeAppointment(appt.getID(),"prescription","diagnosis",5);
        }
    }

    @Test
    @Order(3)
    public void TestDeleteAppointment() {
        List<Appointment> delete_list = repo.filterAppointments(null, "test_doc", 3, null, null);
        for (Appointment appt : delete_list) {
            repo.deleteAppointment(appt.getID());
        }
    }


}
