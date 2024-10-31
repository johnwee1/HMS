package models;

public class Patient implements IdentifiedObject {
    public String id;
    public String name;
    public String currentTreatmentPlan;

    public String getID(){
        return id;
    }
}
