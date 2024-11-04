package utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Template class databases.DBLoader can be used to serialize CSV files and deserialize CSV files in memory.
 */
public class DBLoader {

    /**
     * Template
     *
     * @param filename name of csvfile to be passed in for processing
     * @param cls      the class of the database being built
     * @param <T>      the type (models.Appointment, Patient etc.) of object that we are trying to read in
     * @return HashMap of key type String and value store of T, where the string is the ID.
     */
    public static <T> HashMap<String, T> loadCSV(String filename, Class<T> cls) throws IOException {
        List<String> headers = CSVHandler.readHeaders(filename);
        HashMap<String, Field> classFields = new HashMap<>();

        // create a hashmap of the declared field names in class to the actual field object
        for (Field field : cls.getFields()) {
            classFields.put(field.getName(), field);
        }

        //  check if there are invalid headers in the CSV file that are not present in the class declaration
        for (String header : headers) {
            if (!classFields.containsKey(header)) {
                throw new IOException("Header '" + header + "' not found in declaration of class " + cls.getName());
            }
        }
        // return this hashmap
        HashMap<String, T> db = new HashMap<>();

        for (List<String> row : CSVHandler.readRows(filename)) {
            try {
                T obj = cls.getDeclaredConstructor().newInstance(); // create new object to put in hmap
                String id = "";
                assert row.size() == headers.size() : "Unexpected CSV Parse Error!";

                // associate the header to row entry by index and process row entries
                for (int i = 0; i < row.size(); ++i) {
                    Field field = classFields.get(headers.get(i)); // get the field obj to modify
                    String value = row.get(i);


                    // NOTE: main parser logic here, only try parsing ints, boolean, string
                    if (field.getType() == int.class) {
                        field.set(obj, Integer.parseInt(value));
                    } else if (field.getType() == boolean.class) {
                        field.setBoolean(obj, Boolean.parseBoolean(value));
                    } else if (field.getType() == String.class) {
                        field.set(obj, value);
                        if ((headers.get(i)).equalsIgnoreCase("id")){
                            id = value;
                        }
                    }
                }

                if (!id.isEmpty()) {
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

    /**
     * Saves an instance of the database out to a CSV file by saving all fields as strings
     * TODO: Preserve future unimplemented keys - right now they are just simply dropped.
     *
     * @param filename
     * @param db
     * @param <T>      The object.class
     */
    public static <T> void saveCSV(String filename, HashMap<String, T> db) {
        if (db.isEmpty()) {
            System.out.println("No data saved, " + db.getClass().getName() + " has no entries!");
            return;
        }
        T ref = db.values().iterator().next();
        Field[] classFields = ref.getClass().getDeclaredFields();
        List<String> headers = new ArrayList<>();
        for (Field f : classFields) {
            Class<?> ctype = f.getType();
            if (ctype == int.class || ctype == boolean.class || ctype == String.class) {
                headers.add(f.getName());
            }
            List<List<String>> rows = new ArrayList<>();
            for (T obj : db.values()) {
                ArrayList<String> row = new ArrayList<>();
                for (Field f2 : classFields) {
                    try {
                        String val = f2.get(obj).toString();
                        row.add(val);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error in accessing object fields in databases.DBLoader: " + e);
                    }
                }
                rows.add(row);
            }
            try {
                CSVHandler.writeCSV(filename, headers, rows);
            } catch (IOException e) {
                throw new RuntimeException("Error saving CSV: " + e);
            }
        }
    }
}
