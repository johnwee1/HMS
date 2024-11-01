package repository;

import models.IdentifiedObject;
import utils.DBLoader;

import java.io.IOException;
import java.util.HashMap;

// https://www.geeksforgeeks.org/repository-design-pattern/#disadvantages-of-repository-design-pattern

public abstract class AbstractRepository<T extends IdentifiedObject> implements InterfaceRepository {
    private final Class<T> classType;
    private final String filename;
    public HashMap<String, T> db;

    public AbstractRepository(Class<T> classType, String csv_filepath){
        this.classType = classType;
        this.filename = csv_filepath;
        loadDatabase(); // implicit load
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

    public void defaultCreateItem(T item) {
        db.put(item.getID(), item);
        saveDatabase();
    }

    public T defaultReadItem(String item_id){
        return db.get(item_id);
    }

    public void defaultUpdateItem(T item){
        String item_id = item.getID();
        db.remove(item_id); // remove old item
        db.put(item_id, item); // add new item
        saveDatabase();
    }

    public void defaultDeleteItem(String item_id){
        db.remove(item_id);
        saveDatabase();
    }
}