package UI;

import Company.Company;

import java.util.ArrayList;
import java.util.Scanner;

import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Warehouse.Warehouse;
import Product.Product;
import Util.ProductType;
import Order.CompanyOrder.CompanyOrder;
import Storage.Fabric.Fabric;
import Storage.Cell.Cell;

public class UI {
  private Company company;
  private Scanner scanner;

  public UI(Company company) {
    this.company = company;
    this.scanner = new Scanner(System.in);
  }

  public void start() {
    boolean running = true;
    while (running) {
      running = processMainMenuChoice();
    }
  }


  private boolean processMainMenuChoice() {
    showMainMenu();
    int choice = readIntInput("Enter your choice: ");
    boolean running = true;
    switch (choice) {
      case 1:
        processWarehouseMenu();
        break;
      case 2:
        processProductMenu();
        break;
      case 3:
        processEmployeeMenu();
        break;
      case 4:
        processFabricMenu();
        break;
      case 0:
        running = false;
        break;
      default: 
        System.out.println("Error: invalid choice. Try again.");
        break;
    }
    return running;
  }

  private void processWarehouseMenu() {
    showWarehouseMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        createWarehouse();
        break;
      case 2:
        closeWarehouse();
        break;
      case 3:
        listActiveWarehouses();
        break;
      case 4:
        listAllWarehouses();
        break;
      case 5:
        createCell();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Please try again.");
    }
  }

  private void processProductMenu() {
    showProductMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        createProduct();
        break;
      case 2:
        // moveProduct();
        break;
      case 3:
        purchaseProduct();
        break;
      case 4:
        // sellProduct();
        break;
      case 5:
        listAvailableProducts();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Please try again.");
    }
  }

  private void processEmployeeMenu() {
    showEmployeeMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        hireEmployee();
        break;
      case 2:
        // fireEmployee();
        break;
      case 3:
        listActiveEmployees();
        break;
      case 4:
        listAllEmployees();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Try again.");
    }
  }

  private void processFabricMenu() {
    showFabricMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        createFabric();
        break;
      case 2:
        //listAllFabrics();
        break;
      case 0:
        break;
      default:
        System.out.println("Error: invalid menu option");
    }
  }

  private void createFabric() {
    String name = readStringInput("Enter fabric name: ");
    String description = readStringInput("Enter fabric description: ");
    ProductType type = selectProductType("Enter type of fabric's product: ");
    company.createFabric(name, description, type);
  }

  private void createWarehouse() {
    String name = readStringInput("Enter warehouse name: ");
    String description = readStringInput("Enter warehouse description: ");
    Manager manager = selectManager("Select warehouse manager: ");
    if (manager == null) {
      System.out.println("An error arose when creating a warehouse.");
      return;
    }

    Worker worker = selectWorker("Select warehouse worker: ");
    if (worker == null) {
      System.out.println("An error arose when creating a warehouse.");
      return;
    }

    if (true) {
      company.createWarehouse(name, description, manager, worker);
      System.out.println("Warehouse created successfully.");
    } else {
      System.out.println("An error arose when creating a warehouse.");
    }
  }

  private void createProduct() {
    String name = readStringInput("Enter product name: ");
    String description = readStringInput("Enter product description: ");
    double price = readDoubleInput("Enter product price: ");
    //int quantity = readIntInput("Enter product quantity: ");
    ProductType type = selectProductType("Select product type:");
    company.createProduct(name, description, price, 0, type);
    System.out.println("Product created successfully.");
  }

  private void purchaseProduct() {
    Product product = null;
    CompanyOrder order = createCompanyOrder();
    if (order == null) {
      System.out.println("No available order");
      return;
    }
    Fabric fabric = selectFabric(order);
    if (fabric == null) {
      System.out.println("No available fabric");
      return;
    }
    fabric.acceptOrder(order);
    if (fabric.isProductReady()) {
      product = fabric.getProduct(order);
    }

    // TODO : bind materialized product to warehouse and cell
    Warehouse warehouse = selectActiveWarehouse("Select active warehouse for storage: ");
    if (warehouse == null) {
      System.out.println("No available warehouse");
      return;
    }

    ArrayList<Cell> cells = warehouse.getCells();
    int quantity = product.getQuantity();
    int availableCells = 0;
    System.out.println("Choose cell:");
    for (int i = 0; i < cells.size(); ++i) {
      if (cells.get(i).getAvailableCapacity() >= quantity) {
        System.out.println(++availableCells + ". " + cells.get(i).toString());
      }
    }

    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= availableCells) {
      System.out.println("Invalid cell selection");
      return;
    }

    company.storeProductInCell(cells.get(choice), product);
    System.out.println("Product purchase is sucessfull.");
  }

  private CompanyOrder createCompanyOrder() {
    Product product = selectProduct("Choose available product to purchase:"); 
    if (product == null) {
      return null; 
    }
    int quantity = readIntInput("Enter quantity: ");
    CompanyOrder order = new CompanyOrder(product, quantity);
    return order;
  }

  private Fabric selectFabric(CompanyOrder order) {
    ArrayList<Fabric> fabrics = company.getAllFabrics();
    if (!fabrics.isEmpty()) {
      ProductType type = order.getProduct().getType();
      for (int i = 0; i < fabrics.size(); ++i) {
        if (type == fabrics.get(i).getProductType()) {
          return fabrics.get(i);
        }
      }
    }
    System.out.println("No available fabric.");
    return null;
  }

  private Product selectProduct(String prompt) {
    ArrayList<Product> products = company.getAvailableProducts();
    if (products.isEmpty()) {
      System.out.println("No products available.");
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < products.size(); ++i) {
      System.out.println((i + 1) + ". " + products.get(i));
    }

    int choice = readIntInput("Enter product number: ") - 1;
    if (choice < 0 || choice >= products.size()) {
      System.out.println("Invalid product selection.");
      return null;
    }
    return products.get(choice);
  }

  private ProductType selectProductType(String prompt) {
    showProductTypeMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        return ProductType.ELECTRONICS;
      case 2:
        return ProductType.CLOTHING;
      case 3:
        return ProductType.BOOKS;
      case 4:
        return ProductType.FURNITURE;
      case 5:
        return ProductType.GROCERIES;
      case 0:
        return ProductType.UNKNOWN;
      default:
        System.out.println("Error: Invalid choice");
        return ProductType.UNKNOWN;
    }
  }

  private void listAvailableProducts() {
    ArrayList<Product> products = company.getAvailableProducts();
    if (products.isEmpty()) {
      System.out.println("No products available.");
    } else {
      System.out.println("Available products:");
      for (int i = 0; i < products.size(); ++i) {
        System.out.println((i + 1) + ". " + products.get(i));
      }
    }
  }

  private void createCell() {
    Warehouse warehouse = selectWarehouse("Select warehouse for the new cell: ");
    if (warehouse == null) {
      System.out.println("Error: no available warehouse");
      return;
    }

    String name = readStringInput("Enter cell name: ");
    String description = readStringInput("Enter cell description: ");
    int capacity = readIntInput("Enter cell capacity: ");
    company.createCell(name, description, capacity, warehouse);
    System.out.println("Cell created successfully.");
  }

  private Warehouse selectWarehouse(String prompt) {
    ArrayList<Warehouse> warehouses = company.getAllWarehouses();
    if (warehouses.isEmpty()) {
      System.out.println("No warehouses available.");
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < warehouses.size(); i++) {
      System.out.println((i + 1) + ". " + warehouses.get(i));
    }

    int choice = readIntInput("Enter warehouse number: ") - 1;
    if (choice < 0 || choice >= warehouses.size()) {
      System.out.println("Invalid warehouse selection.");
      return null;
    }
    return warehouses.get(choice);
  }

  private Warehouse selectActiveWarehouse(String prompt) {
    ArrayList<Warehouse> warehouses = company.getActiveWarehouses();
    if (warehouses.isEmpty()) {
      System.out.println("No warehouses available.");
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < warehouses.size(); i++) {
      System.out.println((i + 1) + ". " + warehouses.get(i));
    }

    int choice = readIntInput("Enter warehouse number: ") - 1;
    if (choice < 0 || choice >= warehouses.size()) {
      System.out.println("Invalid warehouse selection.");
      return null;
    }
    return warehouses.get(choice);
  }

  private Manager selectManager(String prompt) {
    ArrayList<Manager> managers = company.getInactiveManagers();
    if (managers.isEmpty()) {
      System.out.println("No inactive managers available.");
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < managers.size(); ++i) {
      System.out.println((i + 1) + ". " + managers.get(i));
    }

    int choice = readIntInput("Enter manager number: ") - 1;
    if (choice < 0 || choice >= managers.size()) {
      System.out.println("Invalid manager selection.");
      return null;
    }
    return managers.get(choice);
  }

  private Worker selectWorker(String prompt) {
    ArrayList<Worker> workers = company.getInactiveWorkers();
    if (workers.isEmpty()) {
      System.out.println("No inactive workers available.");
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < workers.size(); ++i) {
      System.out.println((i + 1) + ". " + workers.get(i));
    }

    int choice = readIntInput("Enter manager number: ") - 1;
    if (choice < 0 || choice >= workers.size()) {
      System.out.println("Invalid manager selection.");
      return null;
    }
    return workers.get(choice);
  }

  private void closeWarehouse() {
    // TODO : select warehouse and close it
  }

  private void listActiveWarehouses() {
    ArrayList<Warehouse> warehouses = company.getActiveWarehouses();
    if (!warehouses.isEmpty()) {
      System.out.println("Active warehouses:");
      for (int i = 0; i < warehouses.size(); ++i) {
        System.out.println((i + 1) + ". " + warehouses.get(i));
      }
    } else {
      System.out.println("No active warehouses found.");
    }
  }

  private void listAllWarehouses() {
    ArrayList<Warehouse> warehouses = company.getAllWarehouses();
    if (!warehouses.isEmpty()) {
      System.out.println("All warehouses:");
      for (int i = 0; i < warehouses.size(); ++i) {
        System.out.println((i + 1) + ". " + warehouses.get(i));
      }
    } else {
      System.out.println("No warehouses found.");
    }
  }

  private void hireEmployee() {
    String name = readStringInput("Enter employee name: ");
    String description = readStringInput("Enter employee description: ");
    String contactInfo = readStringInput("Enter contact info: ");
    int age = readIntInput("Enter age: ");
    String position = readStringInput("Enter employee position: ");
    double salary = readDoubleInput("Enter employee salary: ");

    System.out.println("Choose type of employee:");
    System.out.println("1. Manager");
    System.out.println("2. Worker");
    System.out.println("0. Exit");
    int choice = readIntInput("Enter your choice: ");

    switch (choice) {
      case 1:
        company.hireManager(name, description, contactInfo, age, position, salary);
        break;
      case 2:
        company.hireWorker(name, description, contactInfo, age, position, salary);
        break;
      case 0:
        return;
      default: 
        System.out.println("Error: invalid choice. Try again.");
        return;
    }
    System.out.println("Employee hired successfully.");
  } 

  private void listActiveEmployees() {
    ArrayList<Employee> employees = company.getActiveEmployees();
    if (employees.isEmpty()) {
      System.out.println("No active employees found.");
    } else {
      System.out.println("\nActive Employees:");
      for (int i = 0; i < employees.size(); i++) {
        System.out.println((i + 1) + ". " + employees.get(i));
      }
    }
  }

  private void listAllEmployees() {
    ArrayList<Employee> employees = company.getAllEmployees();
    if (employees.isEmpty()) {
      System.out.println("No employees found.");
    } else {
      System.out.println("All employees:");
      for (int i = 0; i < employees.size(); ++i) {
        System.out.println((i + 1) + ". " + employees.get(i));
      }
    }
  }
  
  private double readDoubleInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextDouble()) {
      System.out.println("Please enter a valid number.");
      System.out.print(prompt);
      scanner.next();
    }
    return scanner.nextDouble();
  }

  private int readIntInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextInt()) {
      System.out.println("Please enter a valid number.");
      System.out.println(prompt);
      scanner.next();
    }
    int num = scanner.nextInt();
    scanner.nextLine();
    return num;
  }

  private String readStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  private void showMainMenu() {
    System.out.println("------Trading system------");
    System.out.println("1. Warehouse management");
    System.out.println("2. Product management");
    System.out.println("3. Employee management");
    System.out.println("4. Fabric managment");
    System.out.println("0. Exit");
  }

  private void showProductTypeMenu() {
    System.out.println("------Product type------");
    System.out.println("1. " + ProductType.ELECTRONICS.toString());
    System.out.println("2. " + ProductType.CLOTHING.toString());
    System.out.println("3. " + ProductType.BOOKS.toString());
    System.out.println("4. " + ProductType.FURNITURE.toString());
    System.out.println("5. " + ProductType.GROCERIES.toString());
    System.out.println("0. Exit");
  }

  private void showProductMenu() {
    System.out.println("------Product system------");
    System.out.println("1. Create product");
    System.out.println("2. Move product");
    System.out.println("3. Purchase product");
    System.out.println("4. Sell product");
    System.out.println("5. List available products");
    System.out.println("0. Exit");
  } 

  private void showWarehouseMenu() {
    System.out.println("------Warehouses------");
    System.out.println("1. Create Warehouse");
    //System.out.println("2. Close Warehouse");
    System.out.println("3. List Active Warehouses");
    System.out.println("4. List All Warehouses");
    System.out.println("5. Create Cell");
    System.out.println("6. List All Warehouses");
    System.out.println("0. Exit");
  }

  private void showEmployeeMenu() {
    System.out.println("------Employee managment------");
    System.out.println("1. Hire employee");
    //System.out.println("2. Fire employee");
    System.out.println("3. List active employees");
    System.out.println("4. List all employees");
    System.out.println("0. Exit");
  }

  private void showFabricMenu() {
    System.out.println("------Fabric managment------");
    System.out.println("1. Create fabric");
    System.out.println("2. List fabrics");
    System.out.println("0. Exit");
  }
}
