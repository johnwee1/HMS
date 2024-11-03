package repository;

import models.Medicine;

public class MedicineRepository extends GenericRepository<Medicine> {
    public MedicineRepository(String filename){
        super(Medicine.class, filename);
    }

    public void setAlertLevel(String medicineId, int newAlertLevel){
        Medicine med = defaultReadItem(medicineId);
        med.alertLevel = newAlertLevel;
        defaultUpdateItem(med);
    }

    public void requestTopUp(String medicineId){
        Medicine med = defaultReadItem(medicineId);
        med.topUpRequested = true;
        defaultUpdateItem(med);
    }

    public void setQuantity(String medicineId, int newQty){
        Medicine med = defaultReadItem(medicineId);
        med.quantity = newQty;
        defaultUpdateItem(med);
    }

    // probably should separate the medicine quantity alerter from this class that only handles CRUD operations
}
