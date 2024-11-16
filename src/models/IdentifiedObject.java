package models;

public interface IdentifiedObject {
    /**
     * All entity objects will have an ID (key to the hash map)
     * @return
     */
    public String getID();
}
