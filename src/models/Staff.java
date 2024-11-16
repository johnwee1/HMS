package models;

public class Staff implements IdentifiedObject {
    public String id;
    public String name;
    public String role;
    public int age;
    public boolean genderIsMale; // true: Male false: Female


    public Staff(){}
    public Staff(String id, String name, String role, int age, boolean genderIsMale) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.age = age;
        this.genderIsMale = genderIsMale;
    }

    public String getID(){
        return id;
    }
}
