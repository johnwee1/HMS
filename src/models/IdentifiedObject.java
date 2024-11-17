package models;

public interface IdentifiedObject {
    /**
     * All entity objects are enforced have a string ID (which would be the key to the hash map)
     * @return the id of the object as a string.
     */
    String getID();
}
