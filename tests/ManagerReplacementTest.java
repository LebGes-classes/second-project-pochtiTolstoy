package tests;

import static org.junit.Assert.*;

import Company.Company;
import DataLoader.*;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Product.Product;
import Product.ProductSpecification;
import Storage.Cell.Cell;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import Util.ProductType;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class ManagerReplacementTest {
  private Company company;
  private DataLoader dataLoader;
  private static final String TEST_RES_DIR = "../tests/res";

  @Before
  public void setUp() {
    File resDir = new File(TEST_RES_DIR);
    if (resDir.exists()) {
      File[] files = resDir.listFiles();
      if (files != null) {
        for (File file : files) {
          file.delete();
        }
      }
    }
    dataLoader = new DataLoaderImpl(TEST_RES_DIR);
    company = new Company(dataLoader);
  }

  @Test
  public void testReplaceActiveManagerWithInactive() {
    // Create initial warehouse with manager and worker
    company.hireManager("Old Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    Manager oldManager = company.getAllManagers()
                             .stream()
                             .filter(m -> m.getName().equals("Old Manager"))
                             .findFirst()
                             .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Old manager should be found", oldManager);
    assertNotNull("Worker should be found", worker);

    // Create warehouse with initial manager
    company.createWarehouse("Test Warehouse", "Test Description", oldManager,
                            worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Create new inactive manager
    company.hireManager("New Manager", "Test Description", "test@test.com", 35,
                        "Manager", 5500.0);
    Manager newManager = company.getAllManagers()
                             .stream()
                             .filter(m -> m.getName().equals("New Manager"))
                             .findFirst()
                             .orElse(null);

    assertNotNull("New manager should be found", newManager);

    // Deactivate new manager
    newManager.setActive(false);

    // Replace manager
    boolean replacementResult =
        company.changeWarehouseManager(warehouse, newManager);

    // Verify results
    assertTrue("Manager replacement should succeed", replacementResult);
    assertFalse("Old manager should be inactive", oldManager.isActive());
    assertTrue("New manager should be active", newManager.isActive());
    assertEquals("Warehouse should have new manager", newManager,
                 warehouse.getManager());
  }

  @Test
  public void testReplaceManagerWithoutReplacement() {
    // Create warehouse with manager and worker
    company.hireManager("Current Manager", "Test Description", "test@test.com",
                        30, "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    Manager currentManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Current Manager"))
            .findFirst()
            .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Current manager should be found", currentManager);
    assertNotNull("Worker should be found", worker);

    // Create warehouse
    company.createWarehouse("Test Warehouse", "Test Description",
                            currentManager, worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Try to replace manager without providing a replacement
    boolean replacementResult = company.changeWarehouseManager(warehouse, null);

    // Verify results
    assertFalse("Manager replacement should fail", replacementResult);
    assertTrue("Current manager should remain active",
               currentManager.isActive());
    assertEquals("Warehouse should keep current manager", currentManager,
                 warehouse.getManager());
  }

  @Test
  public void testReplaceManagerWithoutAccessRights() {
    // Create warehouse with manager and worker
    company.hireManager("Current Manager", "Test Description", "test@test.com",
                        30, "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    Manager currentManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Current Manager"))
            .findFirst()
            .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Current manager should be found", currentManager);
    assertNotNull("Worker should be found", worker);

    // Create warehouse
    company.createWarehouse("Test Warehouse", "Test Description",
                            currentManager, worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Create new manager without proper access rights
    company.hireManager("New Manager", "Test Description", "test@test.com", 35,
                        "Manager", 5500.0);
    Manager newManager = company.getAllManagers()
                             .stream()
                             .filter(m -> m.getName().equals("New Manager"))
                             .findFirst()
                             .orElse(null);

    assertNotNull("New manager should be found", newManager);

    newManager.setActive(true);

    // Try to replace manager without proper access rights
    boolean replacementResult =
        company.changeWarehouseManager(warehouse, newManager);

    // Verify results
    assertFalse("Manager replacement should fail", replacementResult);
    assertTrue("Current manager should remain active",
               currentManager.isActive());
    assertEquals("Warehouse should keep current manager", currentManager,
                 warehouse.getManager());
  }
}
