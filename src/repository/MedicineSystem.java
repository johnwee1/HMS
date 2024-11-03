package repository;

import models.Medicine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MedicineSystem extends GenericRepository<Medicine> {
    List<Medicine> alertedMedicines = new ArrayList<Medicine>();

    public MedicineSystem(String filename){
        super(Medicine.class, filename);
        // builds alertedMedicine list at initialization time
        for (Medicine m : this.defaultViewOnlyDatabase().values()){
            if (m.quantity < m.alertLevel || m.topUpRequested){
                alertedMedicines.add(m);
            }
        }

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

    /**
     * Returns a view only list all the medicines that have low quantity
     * @return
     */
    public List<Medicine> getAlerts(){
        return Collections.unmodifiableList(alertedMedicines);
    }

    // probably should separate the medicine quantity alerter from this class that only handles CRUD operations
}
