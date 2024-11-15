package models;

public class Staff implements IdentifiedObject {
    public String id;
    public String name;
    public String role;
    public int age;
    public boolean gender; // true: Male false: Female

    public Staff(String id, String name, String role, int age, boolean gender) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.age = age;
        this.gender = gender;
    }

    public String getID(){
        return id;
    }
}
