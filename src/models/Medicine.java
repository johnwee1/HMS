package models;

public class Medicine implements IdentifiedObject {
    public String id; // acts as name
    public String displayName;
    public int quantity;
    public int alertLevel;
    public boolean topUpRequested;
    public String getID(){
        return id;
    }
}
