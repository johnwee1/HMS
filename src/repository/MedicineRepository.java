package repository;

import models.Medicine;

public class MedicineRepository extends AbstractRepository<Medicine> {
    public MedicineRepository(String filename){
        super(Medicine.class, filename);
    }
}
