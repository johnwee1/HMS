package repository;

import models.IdentifiedObject;
import utils.DBLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/repository-design-pattern/#disadvantages-of-repository-design-pattern

/**
 * Template class that implements default repository functions (saving and loading) and a default CRUD interface for interacting with objects in the database.
 * Requires subclasses to implement custom logic where necessary
 * @param <T>
 */
public class GenericRepository<T extends IdentifiedObject> extends AbstractRepository<T> {
    private final Class<T> classType;
    private final String filename;
    private HashMap<String, T> db;

    public GenericRepository(Class<T> classType, String csv_filepath){
        this.classType = classType;
        this.filename = csv_filepath;
        loadDatabase(); // implicit load
    }

    /**
     * Returns the entire database for iteration. Method usage needs to be accompanied by a saveDatabase() call if underlying db is changed.
     * @return view only database object, for iteration and filtering.
     */
    public Map<String, T> defaultViewOnlyDatabase(){
        return (Map<String, T>) Collections.unmodifiableMap(db);
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
        if (db.containsKey(item.getID())) throw new RuntimeException("Create operation failed! Item does not exist!");
        db.put(item.getID(), item);
        saveDatabase();
    }

    protected T defaultReadItem(String item_id){
        if (!db.containsKey(item_id)) throw new RuntimeException("Read operation failed! Item does not exist!");
        return db.get(item_id);
    }


    protected void defaultUpdateItem(T item){
        String item_id = item.getID();
        if (!db.containsKey(item_id)) throw new RuntimeException("Update operation failed! Item does not exist!");
        db.remove(item_id); // remove old item
        db.put(item_id, item); // add new item
        saveDatabase();
    }

    protected void defaultDeleteItem(String item_id){
        if (!db.containsKey(item_id)) throw new RuntimeException("Delete operation failed! Item does not exist!");
        db.remove(item_id);
        saveDatabase();
    }
}