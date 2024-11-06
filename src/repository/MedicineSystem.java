package repository;

import models.Medicine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// Currently written with assumption that medicine IDs are human-rememberable IDs

public class MedicineSystem extends GenericRepository<Medicine> {
    Set<Medicine> alertedMedicines = new HashSet<>();

    /**
     * Initializes medicines and then builds a list of low qty medicines
     * @param filename
     */
    public MedicineSystem(String filename){
        super(Medicine.class, filename);
        // builds alertedMedicine list at initialization time
        for (Medicine m : this.defaultViewOnlyDatabase().values()){
            if (m.quantity < m.alertLevel || m.topUpRequested){
                alertedMedicines.add(m);
            }
        }

    }


    public Medicine getMedicineObject(String medicineId){
        return defaultReadItem(medicineId);
    }

    /**
     * Set the qty for which the alert fires
     * @param medicineId
     * @param newAlertLevel
     */
    public void setAlertLevel(String medicineId, int newAlertLevel){
        Medicine med = defaultReadItem(medicineId);
        if (med.quantity < newAlertLevel) {
            med.topUpRequested = true;
            alertedMedicines.add(med);
        }
        med.alertLevel = newAlertLevel;
        defaultUpdateItem(med);
    }

    /**
     * Manually set alert if necessary
     * @param medicineId
     * @param topUpRequested
     */
    public void setRequest(String medicineId, boolean topUpRequested){
        Medicine med = defaultReadItem(medicineId);
        if (topUpRequested){
            alertedMedicines.add(med);
        }
        else alertedMedicines.remove(med); // no duplication check needed, since hashset used
        med.topUpRequested = topUpRequested;
        defaultUpdateItem(med);
    }

    /**
     * For pharmacists to dispense qty of a given medicine (and thus decrease its quantity).
     *
     * @param medicineId medicine name
     * @param qty qty to dispense
     * @return True if the existing medicine quantity is at least equal to the specified qty.
     * Otherwise, the medicine is NOT dispensed (and returns false)
     */
    public boolean dispense(String medicineId, int qty){
        Medicine med = defaultReadItem(medicineId);
        if (qty > med.quantity) return false;
        med.quantity-=qty;
        if (med.quantity < med.alertLevel){
            med.topUpRequested = true;
            alertedMedicines.add(med);
        }
        return true;
    }

    /**
     * Convenient function to replenish all medicines to the default replenishment level
     */
    public void replenishAll(){
        for (Medicine m : alertedMedicines){
            defaultReplenish(m.getID());
        }
    }

    /**
     * Set medicine levels to top up level + 10, maybe can change with a `refill` amt in the future.
     * @param medicineId
     */
    public void defaultReplenish(String medicineId){
        Medicine med = defaultReadItem(medicineId);
        int q = med.alertLevel+10;
        setQuantity(medicineId, q);
    }


    public void setQuantity(String medicineId, int newQty){
        Medicine med = defaultReadItem(medicineId);
        med.quantity = newQty;
        if (newQty >= med.alertLevel){
            med.topUpRequested = false;
            alertedMedicines.remove(med);
        }
        defaultUpdateItem(med);
    }

    /**
     * Register a new medicine into the CSV file.
     * @param medicine
     */
    public void registerMedicine(Medicine medicine){
        defaultCreateItem(medicine);
    }

    public void deregisterMedicine(String medicineId){
        defaultDeleteItem(medicineId);
    }

    /**
     * Returns a view only list all the medicines that have low quantity for the admin to manage.
     *
     * @return
     */
    public Set<Medicine> getAlerts(){
        return Collections.unmodifiableSet(alertedMedicines);
    }

}
