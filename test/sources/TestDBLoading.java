import databases.AppointmentDB;
import databases.CSVHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

public class TestDBLoading {
    final String filename = "appointments.csv";
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();

    @Test
    /**
     * Using databases.AppointmentDB, load and save a CSV file to see if the result is identical.
     * If updating databases.AppointmentDB the respective test/resources/appointments.csv must be updated to reflect this change
     */
    public void AppointmentDBLoadSaveEquiv() throws Exception {
        List<String> in_headers = CSVHandler.readHeaders(filepath);
        List<List<String>> in_rows = CSVHandler.readRows(filepath);
        AppointmentDB appointmentDB = new AppointmentDB(filepath);
        appointmentDB.loadDatabase();
        appointmentDB.printAllAppointments(); // can change later
        appointmentDB.saveDatabase();
        // check if the saved files are the same.
        Assertions.assertIterableEquals(CSVHandler.readHeaders(filepath),in_headers);
        Assertions.assertIterableEquals(CSVHandler.readRows(filepath),in_rows);
    }
}
