public class Main {
    public static void main(String[] args){
        AppointmentDB appointmentDB = new AppointmentDB();
        System.out.println(System.getProperty("user.dir"));
        appointmentDB.load_database(System.getProperty("user.dir")+"/"+"src/appointments.csv");
        appointmentDB.printAllAppointments();
    }
}