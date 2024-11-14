import repository.AppointmentRepository;
import utils.CSVHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.ResourceHandler;

import java.util.List;

public class TestDBLoading {
    final String filename = "appointments.csv";
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();
    private final ResourceHandler rh = new CSVHandler();

    /**
     * Using databases.AppointmentRepository, load and save a CSV file to see if the result is identical.
     * If updating databases.AppointmentRepository the respective test/resources/appointments.csv must be updated to reflect this change
     */
    @Test
    public void AppointmentDBLoadSaveEquiv() throws Exception {
        List<String> in_headers = rh.readHeaders(filepath);
        List<List<String>> in_rows = rh.readRows(filepath);
        AppointmentRepository appointmentDB = new AppointmentRepository(filepath);
        appointmentDB.printAllAppointments(); // can change later
        // check if the saved files are the same.
        Assertions.assertIterableEquals(rh.readHeaders(filepath),in_headers);
        Assertions.assertIterableEquals(rh.readRows(filepath),in_rows);
    }
}
