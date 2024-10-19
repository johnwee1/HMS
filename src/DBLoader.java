import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Template class DBLoader can be used to serialize CSV files and deserialize CSV files in memory.
 */
public class DBLoader {
    /**
     * Template
     * @param filename - name of csvfile to be passed in for processing
     * @param cls - the class of the database being built
     * @return HashMap<Integer, T> where the integer is the ID of the item T.
     * @param <T> - the type (Appointment, Patient etc.) of object that we are trying to read in
     * @throws IOException
     */
    public static <T> HashMap<Integer,T> load_txt(String filename, Class<T> cls) throws IOException {
        Reader in = new FileReader(filename);
        CSVParser parser = CSVFormat.EXCEL.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(in);
        String[] csvHeaders = parser.getHeaderNames().toArray(new String[0]);
        HashMap<String, Field> classFields = new HashMap<>();
        for (Field field:cls.getFields()){
            System.out.println("field name = "+field.getName());
            classFields.put(field.getName(), field);
        }
        for (String header: csvHeaders){
            if (!classFields.containsKey(header)) {
                throw new IOException("Header '" + header + "' not found in declaration of class " + cls.getName());
            }
        }
        // return this hashmap
        HashMap<Integer, T> db = new HashMap<>();
        for (CSVRecord record : parser) {
            try {
                T obj = cls.getDeclaredConstructor().newInstance(); // create new object to put in hmap
                int id = -1;
                for (String header:csvHeaders) {
                    Field field = classFields.get(header);
                    String value = record.get(header);

                    // main parser logic here, only try parsing ints or bool else string
                    if (field.getType()==int.class) {
                        int intValue = Integer.parseInt(value);
                        field.set(obj, intValue);
                        if (header.equalsIgnoreCase("id")) {
                            id = intValue;
                        }
                    } else if (field.getType()==boolean.class){
                        field.setBoolean(obj,Boolean.parseBoolean(value));
                    }
                    else field.set(obj, value);
                }
                if (id != -1) {
                    db.put(id, obj);
                } else {
                    // this should never happen
                    throw new IOException("ID field not found or invalid in CSV record");
                }
            } catch (ReflectiveOperationException e) {
                throw new IOException("Error creating object of class " + cls.getName(), e);
            }
        }
        return db;
    }
}
