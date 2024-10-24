package databases;

import models.Medicine;

public class MedicineDB extends AbstractDB<Medicine> {
    public MedicineDB(String filename){
        super(Medicine.class, filename);
    }
}
