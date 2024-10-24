import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;

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
        appointmentDB.load_database(filepath);
//        appointmentDB.printAllAppointments();
        String output_filename = "save_"+appointmentDB.getClass().getName()+".csv";
        String outpath = tempDir.resolve(output_filename).toString();
        appointmentDB.save_database(outpath);
        Reader in = new FileReader(filepath);
        CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.builder().setIgnoreEmptyLines(true).build());
        List<CSVRecord> list = parser.getRecords();
        Reader out = new FileReader(outpath);
        CSVParser outparser = new CSVParser(out, CSVFormat.EXCEL.builder().setIgnoreEmptyLines(true).build());
        List<CSVRecord> list2 = outparser.getRecords();
        Assertions.assertIterableEquals(list,list2);
        // close streams
        in.close();
        parser.close();
        out.close();
        outparser.close();
    }
}
