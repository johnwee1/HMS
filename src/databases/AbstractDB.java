package databases;

import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractDB<T> implements InterfaceDB {
    private final Class<T> classType;
    private final String filename;
    public HashMap<String, T> db;

    public AbstractDB(Class<T> classType, String csv_filepath){
        this.classType = classType;
        this.filename = csv_filepath;
    }

    public void loadDatabase() {
        try {
            db = DBLoader.loadCSV(filename, classType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Database: " + e);
        }
    }

    public void saveDatabase(){
        DBLoader.saveCSV(filename,db);
    }

    public int getSize(){
        return db.size();
    }

}
