package repository;

import models.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class StaffRepository extends GenericRepository<Staff>{
    public StaffRepository(String csv_filepath) {
        super(Staff.class, csv_filepath);
    }

    public Staff getStaff(String username) {
        try {
            Staff staff = defaultReadItem(username);
            return staff;
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getName(String username) {
        Staff staff = defaultReadItem(username);
        if (staff == null) {
            return "staffnotfound";
        }
        return staff.name;
    }

    public boolean createNewStaff(String username, String name, String role, int age, Boolean gender) {
        if (defaultViewOnlyDatabase().containsKey(username)) return false;
        if (!Arrays.asList(role).contains(role)) return false;
        Staff newstaff = new Staff(username,name,role,age,gender);
        defaultCreateItem(newstaff);
        return true;
    }

    public void updateStaff(String username, String name, String role, Integer age, Boolean gender) {
        Staff curStaff = defaultReadItem(username);
        if (curStaff == null) {
            System.out.println("Staff member not found");
            return;
        }
        if (name != null) {
            curStaff.name = name;
        }
        if (role != null) {
            curStaff.role = role;
        }
        if (age != null) {
            curStaff.age = age;
        }
        if (gender != null) {
            curStaff.genderIsMale = gender;
        }
        defaultUpdateItem(curStaff);
    }

    public void deleteStaff(String username) {
        defaultDeleteItem(username);
    }

    public List<Staff> filterStaff(String username, String name, String role, Integer age, Boolean gender) {
        List<Staff> filteredList = new ArrayList<>();
        defaultViewOnlyDatabase().forEach((key, staff) -> {
            boolean matches = true;
            if (username != null) {
                matches = matches && Objects.equals(staff.id, username);
            }
            if (name != null) {
                matches = matches && Objects.equals(staff.name, name);
            }
            if (role != null) {
                matches = matches && Objects.equals(staff.role, role);
            }
            if (age != null) {
                matches = matches && Objects.equals(staff.age, age);
            }
            if (gender != null) {
                matches = matches && Objects.equals(staff.genderIsMale, gender);
            }
            if (matches) {
                filteredList.add(staff);
            }
        });
        return filteredList;
    }


}
