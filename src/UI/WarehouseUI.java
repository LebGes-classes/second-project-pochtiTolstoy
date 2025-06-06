package UI;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Cell.Cell;
import Storage.Warehouse.Warehouse;
import Util.EmployeeSelector;
import Util.WarehouseSelector;
import java.util.ArrayList;

public class WarehouseUI extends BaseUI {
  public WarehouseUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Warehouses------");
    System.out.println("1. Create Warehouse");
    System.out.println("2. Close Warehouse");
    System.out.println("3. Reopen Warehouse");
    System.out.println("4. List Active Warehouses");
    System.out.println("5. List All Warehouses");
    System.out.println("6. Create Cell");
    System.out.println("7. Change Warehouse Manager");
    System.out.println("8. Warehouse Information");
    System.out.println("9. Warehouse Products Information");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    clearScreen();
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      createWarehouse();
      break;
    case 2:
      closeWarehouse();
      break;
    case 3:
      reopenWarehouse();
      break;
    case 4:
      listActiveWarehouses();
      break;
    case 5:
      listAllWarehouses();
      break;
    case 6:
      createCell();
      break;
    case 7:
      changeWarehouseManager();
      break;
    case 8:
      showWarehouseInfo();
      break;
    case 9:
      showWarehouseProductsInfo();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    waitForEnter();
    return true;
  }

  private void createWarehouse() {
    String name = readStringInput("Enter warehouse name: ");
    String description = readStringInput("Enter warehouse description: ");

    Manager manager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select manager: ", Manager.class,
        "No managers available!");
    if (manager == null)
      return;

    Worker worker = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select worker: ", Worker.class,
        "No workers available!");
    if (worker == null)
      return;

    company.createWarehouse(name, description, manager, worker);
    printSuccess("Warehouse created successfully.");
  }

  private void closeWarehouse() {
    Warehouse warehouse = WarehouseSelector.selectActiveWarehouse(
        company, this, "Select warehouse to close: ");
    if (warehouse == null) {
      printError("No active warehouses available.");
      return;
    }
    company.closeWarehouse(warehouse);
    printSuccess("Warehouse closed successfully.");
  }

  private void reopenWarehouse() {
    Warehouse warehouse = WarehouseSelector.selectWarehouse(
        company, this, "Select warehouse to reopen: ");
    if (warehouse == null) {
      printError("No warehouses available.");
      return;
    }

    Manager manager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select manager: ", Manager.class,
        "No managers available!");
    if (manager == null)
      return;

    Worker worker = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select worker: ", Worker.class, "No worker available!");
    if (worker == null)
      return;

    if (company.reopenWarehouse(warehouse, manager, worker)) {
      printSuccess("Warehouse reopened successfully.");
    } else {
      printError("Failed to reopen warehouse.");
    }
  }

  private void listActiveWarehouses() {
    ArrayList<Warehouse> warehouses = company.getActiveWarehouses();
    if (warehouses.isEmpty()) {
      printInfo("No active warehouses.");
      return;
    }
    for (Warehouse warehouse : warehouses) {
      printInfo(warehouse.toString());
    }
  }

  private void listAllWarehouses() {
    ArrayList<Warehouse> warehouses = company.getAllWarehouses();
    if (warehouses.isEmpty()) {
      printInfo("No warehouses.");
      return;
    }
    for (Warehouse warehouse : warehouses) {
      printInfo(warehouse.toString());
    }
  }

  private void createCell() {
    Warehouse warehouse = WarehouseSelector.selectActiveWarehouse(
        company, this, "Select warehouse: ");
    if (warehouse == null) {
      printError("No active warehouses available.");
      return;
    }
    String name = readStringInput("Enter cell name: ");
    String description = readStringInput("Enter cell description: ");
    int capacity = readIntInput("Enter cell capacity: ");

    company.createCell(name, description, capacity, warehouse);
    printSuccess("Cell created successfully.");
  }

  private void changeWarehouseManager() {
    Warehouse warehouse = WarehouseSelector.selectActiveWarehouse(
        company, this, "Select warehouse to change manager: ");
    if (warehouse == null) {
      printError("No active warehouses available.");
      return;
    }

    Manager newManager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select new manager: ", Manager.class,
        "No managers available!");
    if (newManager == null) {
      return;
    }

    if (company.replaceWarehouseManager(warehouse, newManager)) {
      printSuccess("Warehouse manager changed successfully.");
    } else {
      printError("Failed to change warehouse manager.");
    }
  }

  private void showWarehouseInfo() {
    Warehouse warehouse = WarehouseSelector.selectWarehouse(
        company, this, "Select warehouse to view information: ");
    if (warehouse == null) {
      printError("No warehouses available.");
      return;
    }
    printInfo(company.getWarehouseInfo(warehouse));
  }

  private void showWarehouseProductsInfo() {
    Warehouse warehouse = WarehouseSelector.selectWarehouse(
        company, this, "Select warehouse to view products: ");
    if (warehouse == null) {
      printError("No warehouses available.");
      return;
    }
    printInfo(company.getWarehouseProductsInfo(warehouse));
  }
}
