import models.Medicine;
import org.junit.jupiter.api.*;
import repository.MedicineSystem;

import java.util.Set;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMedicine {
    final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"), "test", "resources", "test_medicines.csv").toString();
    MedicineSystem system = new MedicineSystem(filepath);
    private final String test_id = "test_medicine";
    private final int defaultAlertLevel = 30;

    @Test
    @Order(1)
    public void TestRegister() {
        Medicine m = new Medicine();
        // Might need to replace with constructor at some point
        m.id = test_id;
        m.displayName = "Test Medicine Object";
        m.quantity = 0;
        m.alertLevel = defaultAlertLevel;
        m.topUpRequested = false;
        system.registerMedicine(m);
    }

    @Test
    @Order(2)
    public void TestInsufficientDispense() {
        Assertions.assertFalse(system.dispense(test_id, 50));
    }

    @Test
    @Order(3)
    public void TestRequestReplenish() {
        system.setRequest(test_id, true);
    }

    @Test
    @Order(4)
    public void TestRetrieveAndReplenishAlerts() {
        Set<Medicine> alerts = system.getAlerts();
        Medicine tmp = new Medicine();
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> {
            alerts.add(tmp);
        });
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> {
            alerts.remove(system.getMedicineObject(test_id));
        });
        Medicine m = system.getMedicineObject(test_id);
        Assertions.assertTrue(alerts.contains(m));
        system.setQuantity(test_id, defaultAlertLevel);
        // the Set should not have any test medicine left now that its updated
        final Set<Medicine> alerts2 = system.getAlerts();
        for (Medicine m2:alerts) System.out.println(m2.quantity);
        for (Medicine m2:alerts) System.out.println(m2.alertLevel);
        Assertions.assertEquals(0, alerts2.size());
    }

    @Test
    @Order(5)
    public void TestChangeAlertLevels(){
        Medicine test_obj = system.getMedicineObject(test_id);
        system.setQuantity(test_id, defaultAlertLevel/2);
        system.setAlertLevel(test_id, defaultAlertLevel*2);
        final Set<Medicine> alerts = system.getAlerts();
        Assertions.assertTrue(alerts.contains(test_obj)); // qty < alert now
        system.setQuantity(test_obj.getID(), 2*defaultAlertLevel); // set qty not to trigger alert anymore
        final Set<Medicine> alerts2 = system.getAlerts();
        Assertions.assertFalse(alerts2.contains(test_obj)); // no more alerts
    }

    @Test
    @Order(6)
    public void TestDelete(){
        system.deregisterMedicine(test_id);
        Assertions.assertThrowsExactly(RuntimeException.class, ()->{system.getMedicineObject(test_id);});
    }
}