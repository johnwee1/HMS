package menus;

import repository.AppointmentRepository;
import repository.MedicineRepository;

public class PharmacistMenu extends Menu {

    private MedicineRepository med_repo;

    public PharmacistMenu(MedicineRepository med_repo, AppointmentRepository repo, String id){
        super(repo, id);
        this.med_repo = med_repo;
    }

    @Override
    public void userInterface(){


    }

}
