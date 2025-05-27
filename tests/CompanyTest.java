package tests;

import static org.junit.Assert.*;

import Company.Company;
import DataLoader.*;
import Person.Customer.Customer;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Product.Product;
import Product.ProductSpecification;
import Storage.Cell.Cell;
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
    dataLoader = new DataLoaderImpl(TEST_RES_DIR);
    company = new Company(dataLoader);
  }

  @Test
  public void testCreateWarehouse() {
    // Hire manager and worker through company
    company.hireManager("Test Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    // Get the hired employees
    Manager manager = company.getAllManagers()
                          .stream()
                          .filter(m -> m.getName().equals("Test Manager"))
                          .findFirst()
                          .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Manager should be found", manager);
    assertNotNull("Worker should be found", worker);

    company.createWarehouse("Test Warehouse", "Test Description", manager,
                            worker);
    assertEquals(1, company.getActiveWarehouses().size());
    assertEquals("Test Warehouse",
                 company.getActiveWarehouses().get(0).getName());
  }

  @Test
  public void testCreateSellPoint() {
    // Hire manager and worker through company
    company.hireManager("Test Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    // Get the hired employees
    Manager manager = company.getAllManagers()
                          .stream()
                          .filter(m -> m.getName().equals("Test Manager"))
                          .findFirst()
                          .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Manager should be found", manager);
    assertNotNull("Worker should be found", worker);

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
    // Hire manager and worker through company
    company.hireManager("Test Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    // Get the hired employees
    Manager manager = company.getAllManagers()
                          .stream()
                          .filter(m -> m.getName().equals("Test Manager"))
                          .findFirst()
                          .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Manager should be found", manager);
    assertNotNull("Worker should be found", worker);

    company.createWarehouse("Test Warehouse", "Test Description", manager,
                            worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);
    company.closeWarehouse(warehouse);

    assertEquals(0, company.getActiveWarehouses().size());
    assertEquals(1, company.getAllWarehouses().size());
  }

  @Test
  public void testWarehouseClosureAndReopening() {
    // Hire manager and worker through company
    company.hireManager("Test Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Test Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);

    // Get the hired employees
    Manager manager = company.getAllManagers()
                          .stream()
                          .filter(m -> m.getName().equals("Test Manager"))
                          .findFirst()
                          .orElse(null);
    Worker worker = company.getAllWorkers()
                        .stream()
                        .filter(w -> w.getName().equals("Test Worker"))
                        .findFirst()
                        .orElse(null);

    assertNotNull("Manager should be found", manager);
    assertNotNull("Worker should be found", worker);

    // Create warehouse with hired employees
    company.createWarehouse("Test Warehouse", "Test Description", manager,
                            worker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Verify initial state
    assertEquals(1, company.getActiveWarehouses().size());
    assertTrue(manager.isActive());
    assertTrue(worker.isActive());

    // Close warehouse
    company.closeWarehouse(warehouse);

    // Verify warehouse and employees are inactive
    assertEquals(0, company.getActiveWarehouses().size());
    assertEquals(1, company.getAllWarehouses().size());
    assertFalse(manager.isActive());
    assertFalse(worker.isActive());

    // Save data
    company.saveData();

    // Create new company instance and load data
    Company newCompany = new Company(dataLoader);

    // Verify warehouse exists and is inactive
    assertEquals(0, newCompany.getActiveWarehouses().size());
    assertEquals(1, newCompany.getAllWarehouses().size());
    Warehouse loadedWarehouse = newCompany.getAllWarehouses().get(0);
    assertFalse(loadedWarehouse.isActive());

    // Verify manager and worker are inactive
    Manager loadedManager = newCompany.getAllManagers()
                                .stream()
                                .filter(m -> m.getName().equals("Test Manager"))
                                .findFirst()
                                .orElse(null);
    Worker loadedWorker = newCompany.getAllWorkers()
                              .stream()
                              .filter(w -> w.getName().equals("Test Worker"))
                              .findFirst()
                              .orElse(null);

    assertNotNull("Manager should not be null", loadedManager);
    assertNotNull("Worker should not be null", loadedWorker);
    assertFalse(loadedManager.isActive());
    assertFalse(loadedWorker.isActive());

    // Reopen warehouse with same manager and worker
    newCompany.reopenWarehouse(loadedWarehouse, loadedManager, loadedWorker);

    // Verify warehouse and employees are active again
    assertEquals(1, newCompany.getActiveWarehouses().size());
    assertTrue(loadedManager.isActive());
    assertTrue(loadedWorker.isActive());
    assertEquals(loadedWarehouse, newCompany.getActiveWarehouses().get(0));
  }

  @Test
  public void testMoveProductBetweenWarehouses() {
    // Create source warehouse
    company.hireManager("Source Manager", "Test Description", "test@test.com",
                        30, "Manager", 5000.0);
    company.hireWorker("Source Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);
    Manager sourceManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Source Manager"))
            .findFirst()
            .orElse(null);
    Worker sourceWorker = company.getAllWorkers()
                              .stream()
                              .filter(w -> w.getName().equals("Source Worker"))
                              .findFirst()
                              .orElse(null);
    company.createWarehouse("Source Warehouse", "Test Description",
                            sourceManager, sourceWorker);
    Warehouse sourceWarehouse = company.getActiveWarehouses().get(0);

    // Create destination warehouse
    company.hireManager("Dest Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Dest Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);
    Manager destManager = company.getAllManagers()
                              .stream()
                              .filter(m -> m.getName().equals("Dest Manager"))
                              .findFirst()
                              .orElse(null);
    Worker destWorker = company.getAllWorkers()
                            .stream()
                            .filter(w -> w.getName().equals("Dest Worker"))
                            .findFirst()
                            .orElse(null);
    company.createWarehouse("Dest Warehouse", "Test Description", destManager,
                            destWorker);
    Warehouse destWarehouse = company.getActiveWarehouses().get(0);

    // Create product and add to source warehouse
    company.createProductSpecification("Test Product", "Test Description",
                                       100.0, 10, ProductType.ELECTRONICS);
    ProductSpecification productSpec =
        company.getAvailableProductsToPurchase().get(0);
    Product product = new Product(productSpec, 5);
    product.setQuantity(5);

    // Add product to source warehouse cell
    Cell sourceCell = new Cell("Source Cell", "Test Description", 10);
    sourceWarehouse.addCell(sourceCell);
    sourceCell.addProduct(product, 5);

    // Add cell to destination warehouse
    Cell destCell = new Cell("Dest Cell", "Test Description", 10);
    destWarehouse.addCell(destCell);

    // Move product between cells
    boolean moveResult =
        company.moveProductBetweenCells(product, 3, sourceCell, destCell);

    // Verify results
    assertTrue("Product movement should succeed", moveResult);
    assertEquals("Source cell should have 2 items left", 2,
                 sourceCell.getProductQuantity(product));
    assertEquals("Destination cell should have 3 items", 3,
                 destCell.getProductQuantity(product));
  }

  @Test
  public void testMoveProductToSellPoint() {
    // Create warehouse
    company.hireManager("Warehouse Manager", "Test Description",
                        "test@test.com", 30, "Manager", 5000.0);
    company.hireWorker("Warehouse Worker", "Test Description", "test@test.com",
                       25, "Worker", 3000.0);
    Manager warehouseManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Warehouse Manager"))
            .findFirst()
            .orElse(null);
    Worker warehouseWorker =
        company.getAllWorkers()
            .stream()
            .filter(w -> w.getName().equals("Warehouse Worker"))
            .findFirst()
            .orElse(null);
    company.createWarehouse("Test Warehouse", "Test Description",
                            warehouseManager, warehouseWorker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Create sell point
    company.hireManager("SellPoint Manager", "Test Description",
                        "test@test.com", 30, "Manager", 5000.0);
    company.hireWorker("SellPoint Worker", "Test Description", "test@test.com",
                       25, "Worker", 3000.0);
    Manager sellPointManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("SellPoint Manager"))
            .findFirst()
            .orElse(null);
    Worker sellPointWorker =
        company.getAllWorkers()
            .stream()
            .filter(w -> w.getName().equals("SellPoint Worker"))
            .findFirst()
            .orElse(null);
    company.createSellPoint("Test Sell Point", "Test Description",
                            sellPointManager, sellPointWorker);
    SellPoint sellPoint = company.getActiveSellPoints().get(0);

    // Create product and add to warehouse
    company.createProductSpecification("Test Product", "Test Description",
                                       100.0, 10, ProductType.ELECTRONICS);
    ProductSpecification productSpec =
        company.getAvailableProductsToPurchase().get(0);
    Product product = new Product(productSpec, 5);
    product.setQuantity(5);

    // Add product to warehouse cell
    Cell cell = new Cell("Test Cell", "Test Description", 10);
    warehouse.addCell(cell);
    cell.addProduct(product, 5);

    // Move product to sell point
    boolean moveResult =
        company.moveProductToSellPoint(product, 3, warehouse, sellPoint);

    // Verify results
    assertTrue("Product movement should succeed", moveResult);
    assertEquals("Warehouse cell should have 2 items left", 2,
                 cell.getProductQuantity(product));
    assertEquals("Sell point should have 3 items", 3,
                 sellPoint.getProductQuantity(product));
  }

  @Test
  public void testMoveProductInsufficientQuantity() {
    // Create source warehouse
    company.hireManager("Source Manager", "Test Description", "test@test.com",
                        30, "Manager", 5000.0);
    company.hireWorker("Source Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);
    Manager sourceManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Source Manager"))
            .findFirst()
            .orElse(null);
    Worker sourceWorker = company.getAllWorkers()
                              .stream()
                              .filter(w -> w.getName().equals("Source Worker"))
                              .findFirst()
                              .orElse(null);
    company.createWarehouse("Source Warehouse", "Test Description",
                            sourceManager, sourceWorker);
    Warehouse sourceWarehouse = company.getActiveWarehouses().get(0);

    // Create destination warehouse
    company.hireManager("Dest Manager", "Test Description", "test@test.com", 30,
                        "Manager", 5000.0);
    company.hireWorker("Dest Worker", "Test Description", "test@test.com", 25,
                       "Worker", 3000.0);
    Manager destManager = company.getAllManagers()
                              .stream()
                              .filter(m -> m.getName().equals("Dest Manager"))
                              .findFirst()
                              .orElse(null);
    Worker destWorker = company.getAllWorkers()
                            .stream()
                            .filter(w -> w.getName().equals("Dest Worker"))
                            .findFirst()
                            .orElse(null);
    company.createWarehouse("Dest Warehouse", "Test Description", destManager,
                            destWorker);
    Warehouse destWarehouse = company.getActiveWarehouses().get(0);

    // Create product and add to source warehouse
    company.createProductSpecification("Test Product", "Test Description",
                                       100.0, 10, ProductType.ELECTRONICS);
    ProductSpecification productSpec =
        company.getAvailableProductsToPurchase().get(0);
    Product product = new Product(productSpec, 5);
    product.setQuantity(5);

    // Add product to source warehouse cell
    Cell sourceCell = new Cell("Source Cell", "Test Description", 10);
    sourceWarehouse.addCell(sourceCell);
    sourceCell.addProduct(product, 5);

    // Add cell to destination warehouse
    Cell destCell = new Cell("Dest Cell", "Test Description", 10);
    destWarehouse.addCell(destCell);

    // Try to move more product than available
    boolean moveResult =
        company.moveProductBetweenCells(product, 10, sourceCell, destCell);

    // Verify results
    assertFalse("Product movement should fail", moveResult);
    assertEquals("Source cell should still have 5 items", 5,
                 sourceCell.getProductQuantity(product));
    assertEquals("Destination cell should have 0 items", 0,
                 destCell.getProductQuantity(product));
  }

  @Test
  public void testMoveProductToInactiveStorage() {
    // Create warehouse
    company.hireManager("Warehouse Manager", "Test Description",
                        "test@test.com", 30, "Manager", 5000.0);
    company.hireWorker("Warehouse Worker", "Test Description", "test@test.com",
                       25, "Worker", 3000.0);
    Manager warehouseManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("Warehouse Manager"))
            .findFirst()
            .orElse(null);
    Worker warehouseWorker =
        company.getAllWorkers()
            .stream()
            .filter(w -> w.getName().equals("Warehouse Worker"))
            .findFirst()
            .orElse(null);
    company.createWarehouse("Test Warehouse", "Test Description",
                            warehouseManager, warehouseWorker);
    Warehouse warehouse = company.getActiveWarehouses().get(0);

    // Create sell point and close it
    company.hireManager("SellPoint Manager", "Test Description",
                        "test@test.com", 30, "Manager", 5000.0);
    company.hireWorker("SellPoint Worker", "Test Description", "test@test.com",
                       25, "Worker", 3000.0);
    Manager sellPointManager =
        company.getAllManagers()
            .stream()
            .filter(m -> m.getName().equals("SellPoint Manager"))
            .findFirst()
            .orElse(null);
    Worker sellPointWorker =
        company.getAllWorkers()
            .stream()
            .filter(w -> w.getName().equals("SellPoint Worker"))
            .findFirst()
            .orElse(null);
    company.createSellPoint("Test Sell Point", "Test Description",
                            sellPointManager, sellPointWorker);
    SellPoint sellPoint = company.getActiveSellPoints().get(0);
    company.closeSellPoint(sellPoint);

    // Create product and add to warehouse
    company.createProductSpecification("Test Product", "Test Description",
                                       100.0, 10, ProductType.ELECTRONICS);
    ProductSpecification productSpec =
        company.getAvailableProductsToPurchase().get(0);
    Product product = new Product(productSpec, 5);
    product.setQuantity(5);

    // Add product to warehouse cell
    Cell cell = new Cell("Test Cell", "Test Description", 10);
    warehouse.addCell(cell);
    cell.addProduct(product, 5);

    // Try to move product to inactive sell point
    boolean moveResult =
        company.moveProductToSellPoint(product, 3, warehouse, sellPoint);

    // Verify results
    assertFalse("Product movement should fail", moveResult);
    assertEquals("Warehouse cell should still have 5 items", 5,
                 cell.getProductQuantity(product));
    assertEquals("Sell point should have 0 items", 0,
                 sellPoint.getProductQuantity(product));
  }
}
