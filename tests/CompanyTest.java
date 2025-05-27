package tests;

import static org.junit.Assert.*;

import Company.Company;
import DataLoader.*;
import Person.Customer.Customer;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Fabric.Fabric;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import Util.ProductType;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class CompanyTest {
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
    dataLoader = new DataLoaderImpl("../tests/res");
    company = new Company(dataLoader);
  }

  @Test
  public void testCreateWarehouse() {
    Manager manager = new Manager("Test Manager", "Test Description",
                                  "test@test.com", 30, "Manager", 5000.0);
    Worker worker = new Worker("Test Worker", "Test Description",
                               "test@test.com", 25, "Worker", 3000.0);

    company.createWarehouse("Test Warehouse", "Test Description", manager,
                            worker);
    assertEquals(1, company.getActiveWarehouses().size());
    assertEquals("Test Warehouse",
                 company.getActiveWarehouses().get(0).getName());
  }

  @Test
  public void testCreateSellPoint() {
    Manager manager = new Manager("Test Manager", "Test Description",
                                  "test@test.com", 30, "Manager", 5000.0);
    Worker worker = new Worker("Test Worker", "Test Description",
                               "test@test.com", 25, "Worker", 3000.0);

    company.createSellPoint("Test Sell Point", "Test Description", manager,
                            worker);
    assertEquals(1, company.getActiveSellPoints().size());
    assertEquals("Test Sell Point",
                 company.getActiveSellPoints().get(0).getName());
  }

  @Test
  public void testAddCustomer() {
    company.addCustomer("Test Customer", "Test Description", "test@test.com",
                        25);
    assertEquals(1, company.getActiveCustomers().size());
    assertEquals("Test Customer",
                 company.getActiveCustomers().get(0).getName());
  }

  @Test
  public void testCreateFabric() {
    company.createFabric("Test Fabric", "Test Description",
                         ProductType.ELECTRONICS);
    assertEquals(1, company.getAllFabrics().size());
    assertEquals("Test Fabric", company.getAllFabrics().get(0).getName());
  }

  @Test
  public void testCloseWarehouse() {
    Manager manager = new Manager("Test Manager", "Test Description",
                                  "test@test.com", 30, "Manager", 5000.0);
    Worker worker = new Worker("Test Worker", "Test Description",
                               "test@test.com", 25, "Worker", 3000.0);

    company.createWarehouse("Test Warehouse", "Test Description", manager,
                            worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);
    company.closeWarehouse(warehouse);

    assertEquals(0, company.getActiveWarehouses().size());
    assertEquals(1, company.getAllWarehouses().size());
  }
}
