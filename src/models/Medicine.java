package models;

/**
 * Public Schema class for the models.Medicine object.
 */
public class Medicine implements IdentifiedObject {
    public String id; // acts as name
    public String displayName;
    public int quantity;
    /**
     * Below this quantity, topUpRequest will autotrigger
     */
    public int alertLevel;
    public boolean topUpRequested;

    public String getID(){
        return id;
    }
}
