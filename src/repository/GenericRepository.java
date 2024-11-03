package repository;

import models.IdentifiedObject;
import utils.DBLoader;

import java.io.IOException;
import java.util.HashMap;

// https://www.geeksforgeeks.org/repository-design-pattern/#disadvantages-of-repository-design-pattern

/**
 * Template class that implements default repository functions (saving and loading) and a default CRUD interface for interacting with objects in the database.
 * Requires subclasses to implement custom logic where necessary
 * @param <T>
 */
public class GenericRepository<T extends IdentifiedObject> extends AbstractRepository<T> {
    private final Class<T> classType;
    private final String filename;
    public HashMap<String, T> db;

    public GenericRepository(Class<T> classType, String csv_filepath){
        this.classType = classType;
        this.filename = csv_filepath;
        loadDatabase(); // implicit load
    }

    protected void loadDatabase() {
        try {
            db = DBLoader.loadCSV(filename, classType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Database: " + e);
        }
    }

    protected void saveDatabase(){
        DBLoader.saveCSV(filename,db);
    }

    protected void defaultCreateItem(T item) {
        db.put(item.getID(), item);
        saveDatabase();
    }

    protected T defaultReadItem(String item_id){
        return db.get(item_id);
    }


    protected void defaultUpdateItem(T item){
        String item_id = item.getID();
        db.remove(item_id); // remove old item
        db.put(item_id, item); // add new item
        saveDatabase();
    }

    protected void defaultDeleteItem(String item_id){
        db.remove(item_id);
        saveDatabase();
    }
}