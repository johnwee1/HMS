import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

public class TestDBLoading {
    @TempDir
    Path tempDir;

    final String filename = "appointments.csv";
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();

    @Test
    /**
     * Using AppointmentDB, load and save a CSV file to see if the result is identical.
     * If updating AppointmentDB the respective test/resources/appointments.csv must be updated to reflect this change
     */
    public void AppointmentDBLoadSaveEquiv() throws Exception {
        AppointmentDB appointmentDB = new AppointmentDB();
        appointmentDB.loadDatabase(filepath);
//        appointmentDB.printAllAppointments();
        String output_filename = "save_"+appointmentDB.getClass().getName()+".csv";
        String outpath = tempDir.resolve(output_filename).toString();
        appointmentDB.saveDatabase(outpath);

        // check if the saved files are the same.
        Assertions.assertIterableEquals(CSVHandler.readHeaders(filepath),CSVHandler.readHeaders(outpath));
        Assertions.assertIterableEquals(CSVHandler.readRows(filepath),CSVHandler.readRows(outpath));
    }
}
