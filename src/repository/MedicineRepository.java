package repository;

import models.Medicine;

import java.util.*;

// Currently written with assumption that medicine IDs are human-rememberable IDs

/**
 * Offers higher level functionality for the UI classes. However, doesn't handle exceptions yet
 */
public class MedicineRepository extends GenericRepository<Medicine> {
    Set<Medicine> alertedMedicines = new HashSet<>();
    private static final String headerFormat = "%-20s|%-15s|%-10s|%-12s|%-15s%n";
    private static final String rowFormat = "%-20s|%-15s|%-10d|%-12d|%-15b%n";

    /**
     * Initializes medicines and then builds a list of low qty medicines
     *
     * @param filename
     */
    public MedicineRepository(String filename) {
        super(Medicine.class, filename);
        // builds alertedMedicine list at initialization time
        for (Medicine m : this.defaultViewOnlyDatabase().values()) {
            if (m.quantity < m.alertLevel || m.topUpRequested) {
                alertedMedicines.add(m);
            }
        }

    }


    public Medicine getMedicineObject(String medicineId) {
        return defaultReadItem(medicineId);
    }

    /**
     * Set the qty for which the alert fires
     *
     * @param medicineId
     * @param newAlertLevel
     * @return True if success, false if medicine id does not exist.
     */
    public boolean setAlertLevel(String medicineId, int newAlertLevel) {
        try {
            Medicine med = defaultReadItem(medicineId);
            if (med.quantity < newAlertLevel) {
                med.topUpRequested = true;
                alertedMedicines.add(med);
            }
            med.alertLevel = newAlertLevel;
            defaultUpdateItem(med);
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    /**
     * Manually set alert if necessary
     *
     * @param medicineId
     * @param topUpRequested
     */
    public boolean setRequest(String medicineId, boolean topUpRequested) {
        try {
            Medicine med = defaultReadItem(medicineId);
            if (topUpRequested) {
                alertedMedicines.add(med);
            } else alertedMedicines.remove(med); // no duplication check needed, since hashset used
            med.topUpRequested = topUpRequested;
            defaultUpdateItem(med);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * For pharmacists to dispense qty of a given medicine (and thus decrease its quantity).
     *
     * @param medicineId medicine name
     * @param qty        qty to dispense
     * @return True if the existing medicine quantity is at least equal to the specified qty.
     * Otherwise, the medicine is NOT dispensed (and returns false). Returns false if medicine does not exist.
     */
    public boolean dispense(String medicineId, int qty) {
        try {
            Medicine med = defaultReadItem(medicineId);

            if (qty > med.quantity) return false;
            med.quantity -= qty;
            if (med.quantity < med.alertLevel) {
                med.topUpRequested = true;
                alertedMedicines.add(med);
            }
            return true;
        } catch (RuntimeException e){
            return false;
        }
    }

    /**
     * Convenient function to replenish all medicines to the default replenishment level
     */
    public void replenishAll() {
        for (Medicine m : alertedMedicines) {
            defaultReplenish(m.getID());
        }
    }

    /**
     * Set medicine levels to top up level + 10, maybe can change with a `refill` amt in the future.
     *
     * @param medicineId
     */
    public boolean defaultReplenish(String medicineId) {
        try {
            Medicine med = defaultReadItem(medicineId);
            int q = med.alertLevel + 10;
            setQuantity(medicineId, q);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }


    public void setQuantity(String medicineId, int newQty) {
        Medicine med = defaultReadItem(medicineId);
        med.quantity = newQty;
        if (newQty >= med.alertLevel) {
            med.topUpRequested = false;
            alertedMedicines.remove(med);
        }
        defaultUpdateItem(med);
    }

    /**
     * Register a new medicine into the CSV file.
     *
     * @param medicine
     */
    public void registerMedicine(Medicine medicine) {
        try {
            defaultCreateItem(medicine);
        } catch (RuntimeException e) {
            System.out.println("Medicine registration failed. Medicine ID already exists.");
        }
    }

    public void deregisterMedicine(String medicineId) {
        defaultDeleteItem(medicineId);
    }

    /**
     * Returns a view only list all the medicines that have low quantity for the admin to manage.
     *
     * @return
     */
    public Set<Medicine> getAlerts() {
        return Collections.unmodifiableSet(alertedMedicines);
    }

    //------------- HELPER PRINT FUNCTIONS -----------------

    private void printMedicineCollection(Collection<Medicine> medicines) {
        System.out.println("Medicine Inventory");
        System.out.println("--------------------------------------------------");
        System.out.format(headerFormat, "Display Name", "System ID", "Quantity", "Alert Level", "Alerted?");
        System.out.println("--------------------------------------------------");
        for (Medicine m : medicines) {
            System.out.format(rowFormat, m.displayName, m.id, m.quantity, m.alertLevel, m.topUpRequested);
        }
    }

    /**
     * Prints out the view only list of medicines in the output stream
     */
    public void viewMedicationInventory() {
        printMedicineCollection(defaultViewOnlyDatabase().values());
    }

    /**
     * Prints out the list of alerted medicines
     */
    public void viewReplenishmentRequests(){
        printMedicineCollection(alertedMedicines);
    }

    public void printMedicineStatus(Medicine m){
        System.out.printf("Medicine ID: %s\n", m.id);
        System.out.printf("Display Name: %s\n", m.displayName);
        System.out.printf("Quantity in stock: %s\n", m.quantity);
        System.out.print("Top-up status: ");
        if (m.topUpRequested) System.out.println("Top-up request pending");
        else System.out.println("Normal");
    }
}
