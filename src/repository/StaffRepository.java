package repository;

import models.Appointment;
import models.Staff;

import java.util.Arrays;


public class StaffRepository extends GenericRepository<Staff>{
    public StaffRepository(String csv_filepath) {
        super(Staff.class, csv_filepath);
    }

    public boolean createNewStaff(String username, String name, String role, int age, Boolean gender) {
        if (defaultViewOnlyDatabase().containsKey(username)) return false;
        if (!Arrays.asList(role).contains(role)) return false;
        Staff newstaff = new Staff(username,name,role,age,gender);
        defaultCreateItem(newstaff);
        return true;
    }

    public
}
