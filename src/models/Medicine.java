package models;

public class Medicine implements IdentifiedObject {
    public String id; // acts as name
    public int qty;
    public int alertLevel;
    public boolean topUpRequested;
    public String getID(){
        return id;
    }
}
