package UI;

import Company.Company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Warehouse.Warehouse;
import Product.Product;
import Util.ProductType;
import Order.CompanyOrder.CompanyOrder;
import Storage.Fabric.Fabric;
import Storage.Cell.Cell;
import Storage.SellPoint.SellPoint;
import Person.Customer.Customer;

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
      case 5:
        processSellPointMenu();
        break;
      case 6:
        processCustomerMenu();
        break;
      case 7:
        showCompanyStatistics();
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
        moveProduct();
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
      case 6:
        showAvailableProductsForPurchase();
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
        fireEmployee();
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

  private void processSellPointMenu() {
    showSellPointMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        createSellPoint();
        break;
      case 2:
        closeSellPoint();
        break;
      case 3:
        listActiveSellPoints();
        break;
      case 4:
        listAllSellPoints();
        break;
      case 5:
        moveProductToSellPoint();
        break;
      case 6:
        sellProductToCustomer();
        break;
      case 7:
        returnProductFromCustomer();
        break;
      case 8:
        changeSellPointManager();
        break;
      case 9:
        showSellPointInfo();
        break;
      case 10:
        showSellPointProductsInfo();
        break;
      case 11:
        showSellPointProfitability();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Please try again.");
    }
  }

  private void processCustomerMenu() {
    showCustomerMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        addCustomer();
        break;
      case 2:
        listActiveCustomers();
        break;
      case 3:
        listAllCustomers();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Please try again.");
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

    cells.get(choice).addProduct(product);
    System.out.println("Product purchase is successful.");
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
    Warehouse warehouse = selectActiveWarehouse("Select warehouse to close: ");
    if (warehouse == null) {
      System.out.println("No available warehouse to close.");
      return;
    }

    company.closeWarehouse(warehouse);
    System.out.println("Warehouse closed successfully. All employees have been deactivated.");
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
    System.out.println("\n=== Main Menu ===");
    System.out.println("1. Warehouse Management");
    System.out.println("2. Product Management");
    System.out.println("3. Employee Management");
    System.out.println("4. Fabric Management");
    System.out.println("5. Sell Point Management");
    System.out.println("6. Customer Management");
    System.out.println("7. Company Statistics");
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
    System.out.println("6. Available Products for Purchase");
    System.out.println("0. Exit");
  } 

  private void showWarehouseMenu() {
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

  private void showEmployeeMenu() {
    System.out.println("------Employee managment------");
    System.out.println("1. Hire employee");
    System.out.println("2. Fire employee");
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

  private void showSellPointMenu() {
    System.out.println("\n=== Sell Point Management ===");
    System.out.println("1. Create Sell Point");
    System.out.println("2. Close Sell Point");
    System.out.println("3. List Active Sell Points");
    System.out.println("4. List All Sell Points");
    System.out.println("5. Move Product to Sell Point");
    System.out.println("6. Sell Product to Customer");
    System.out.println("7. Return Product from Customer");
    System.out.println("8. Change Sell Point Manager");
    System.out.println("9. Sell Point Information");
    System.out.println("10. Sell Point Products Information");
    System.out.println("11. Sell Point Profitability");
    System.out.println("0. Back to Main Menu");
  }

  private void createSellPoint() {
    String name = readStringInput("Enter sell point name: ");
    String description = readStringInput("Enter sell point description: ");
    Manager manager = selectManager("Select sell point manager: ");
    if (manager == null) {
      System.out.println("An error arose when creating a sell point.");
      return;
    }

    Worker worker = selectWorker("Select sell point worker: ");
    if (worker == null) {
      System.out.println("An error arose when creating a sell point.");
      return;
    }

    company.createSellPoint(name, description, manager, worker);
    System.out.println("Sell point created successfully.");
  }

  private void closeSellPoint() {
    SellPoint sellPoint = selectActiveSellPoint("Select sell point to close: ");
    if (sellPoint == null) {
      System.out.println("No available sell point.");
      return;
    }

    company.closeSellPoint(sellPoint);
    System.out.println("Sell point closed successfully.");
  }

  private void listActiveSellPoints() {
    ArrayList<SellPoint> sellPoints = company.getActiveSellPoints();
    if (sellPoints.isEmpty()) {
      System.out.println("No active sell points.");
      return;
    }

    System.out.println("\nActive Sell Points:");
    for (int i = 0; i < sellPoints.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, sellPoints.get(i));
    }
  }

  private void listAllSellPoints() {
    ArrayList<SellPoint> sellPoints = company.getAllSellPoints();
    if (sellPoints.isEmpty()) {
      System.out.println("No sell points.");
      return;
    }

    System.out.println("\nAll Sell Points:");
    for (int i = 0; i < sellPoints.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, sellPoints.get(i));
    }
  }

  private void moveProductToSellPoint() {
    Warehouse warehouse = selectActiveWarehouse("Select source warehouse: ");
    if (warehouse == null) {
      System.out.println("No available warehouse.");
      return;
    }

    SellPoint sellPoint = selectActiveSellPoint("Select target sell point: ");
    if (sellPoint == null) {
      System.out.println("No available sell point.");
      return;
    }

    Product product = selectProduct("Select product to move: ");
    if (product == null) {
      System.out.println("No available product.");
      return;
    }

    int quantity = readIntInput("Enter quantity to move: ");
    if (company.moveProductToSellPoint(product, quantity, warehouse, sellPoint)) {
      System.out.println("Product moved successfully.");
    } else {
      System.out.println("Failed to move product.");
    }
  }

  private void sellProductToCustomer() {
    SellPoint sellPoint = selectActiveSellPoint("Select sell point: ");
    if (sellPoint == null) {
      System.out.println("No available sell point.");
      return;
    }

    Product product = selectProduct("Select product to sell: ");
    if (product == null) {
      System.out.println("No available product.");
      return;
    }

    int quantity = readIntInput("Enter quantity to sell: ");
    Customer customer = selectCustomer("Select customer: ");
    if (customer == null) {
      System.out.println("No available customer.");
      return;
    }

    if (company.sellProductToCustomer(product, quantity, sellPoint, customer)) {
      System.out.println("Product sold successfully.");
    } else {
      System.out.println("Failed to sell product.");
    }
  }

  private void returnProductFromCustomer() {
    SellPoint sellPoint = selectActiveSellPoint("Select sell point: ");
    if (sellPoint == null) {
      System.out.println("No available sell point.");
      return;
    }

    Customer customer = selectCustomer("Select customer: ");
    if (customer == null) {
      System.out.println("No available customer.");
      return;
    }

    Product product = selectProduct("Select product to return: ");
    if (product == null) {
      System.out.println("No available product.");
      return;
    }

    int quantity = readIntInput("Enter quantity to return: ");
    if (company.returnProductFromCustomer(product, quantity, sellPoint, customer)) {
      System.out.println("Product returned successfully.");
    } else {
      System.out.println("Failed to return product.");
    }
  }

  private void showCompanyStatistics() {
    System.out.println("\n=== Company Statistics ===");
    System.out.printf("Total Revenue: %.2f%n", company.getTotalCompanyRevenue());
    System.out.printf("Total Expenses: %.2f%n", company.getTotalCompanyExpenses());
    System.out.printf("Total Profit: %.2f%n", company.getTotalCompanyProfit());
    System.out.printf("Active Warehouses: %d%n", company.getActiveWarehouses().size());
    System.out.printf("Active Sell Points: %d%n", company.getActiveSellPoints().size());
    System.out.printf("Active Employees: %d%n", company.getActiveEmployees().size());
  }

  private SellPoint selectActiveSellPoint(String prompt) {
    ArrayList<SellPoint> sellPoints = company.getActiveSellPoints();
    if (sellPoints.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < sellPoints.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, sellPoints.get(i));
    }

    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      return null;
    }

    return sellPoints.get(choice);
  }

  private Customer selectCustomer(String prompt) {
    ArrayList<Customer> customers = company.getAllCustomers();
    if (customers.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < customers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, customers.get(i));
    }

    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= customers.size()) {
      return null;
    }

    return customers.get(choice);
  }

  private void showCustomerMenu() {
    System.out.println("\n=== Customer Management ===");
    System.out.println("1. Add Customer");
    System.out.println("2. List Active Customers");
    System.out.println("3. List All Customers");
    System.out.println("0. Back to Main Menu");
  }

  private void addCustomer() {
    String name = readStringInput("Enter customer name: ");
    String description = readStringInput("Enter customer description: ");
    String contactInfo = readStringInput("Enter customer contact info: ");
    int age = readIntInput("Enter customer age: ");

    company.addCustomer(name, description, contactInfo, age);
    System.out.println("Customer added successfully.");
  }

  private void listActiveCustomers() {
    ArrayList<Customer> customers = company.getActiveCustomers();
    if (customers.isEmpty()) {
      System.out.println("No active customers.");
      return;
    }

    System.out.println("\nActive Customers:");
    for (int i = 0; i < customers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, customers.get(i));
    }
  }

  private void listAllCustomers() {
    ArrayList<Customer> customers = company.getAllCustomers();
    if (customers.isEmpty()) {
      System.out.println("No customers.");
      return;
    }

    System.out.println("\nAll Customers:");
    for (int i = 0; i < customers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, customers.get(i));
    }
  }

  private void reopenWarehouse() {
    // First, find an inactive warehouse
    ArrayList<Warehouse> inactiveWarehouses = company.getAllWarehouses().stream()
        .filter(warehouse -> !warehouse.isActive())
        .collect(Collectors.toCollection(ArrayList::new));

    if (inactiveWarehouses.isEmpty()) {
      System.out.println("No inactive warehouses available to reopen.");
      return;
    }

    System.out.println("Select warehouse to reopen:");
    for (int i = 0; i < inactiveWarehouses.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveWarehouses.get(i));
    }

    int choice = readIntInput("Enter warehouse number: ") - 1;
    if (choice < 0 || choice >= inactiveWarehouses.size()) {
      System.out.println("Invalid warehouse selection.");
      return;
    }

    Warehouse warehouse = inactiveWarehouses.get(choice);

    // Get inactive manager
    ArrayList<Manager> inactiveManagers = company.getInactiveManagers();
    if (inactiveManagers.isEmpty()) {
      System.out.println("No inactive managers available. Cannot reopen warehouse.");
      return;
    }

    System.out.println("Select manager for the warehouse:");
    for (int i = 0; i < inactiveManagers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveManagers.get(i));
    }

    int managerChoice = readIntInput("Enter manager number: ") - 1;
    if (managerChoice < 0 || managerChoice >= inactiveManagers.size()) {
      System.out.println("Invalid manager selection.");
      return;
    }

    Manager manager = inactiveManagers.get(managerChoice);

    // Get inactive worker
    ArrayList<Worker> inactiveWorkers = company.getInactiveWorkers();
    if (inactiveWorkers.isEmpty()) {
      System.out.println("No inactive workers available. Cannot reopen warehouse.");
      return;
    }

    System.out.println("Select worker for the warehouse:");
    for (int i = 0; i < inactiveWorkers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveWorkers.get(i));
    }

    int workerChoice = readIntInput("Enter worker number: ") - 1;
    if (workerChoice < 0 || workerChoice >= inactiveWorkers.size()) {
      System.out.println("Invalid worker selection.");
      return;
    }

    Worker worker = inactiveWorkers.get(workerChoice);

    if (company.reopenWarehouse(warehouse, manager, worker)) {
      System.out.println("Warehouse reopened successfully.");
    } else {
      System.out.println("Failed to reopen warehouse.");
    }
  }

  private void fireEmployee() {
    // First, get the active employee to fire
    ArrayList<Employee> activeEmployees = company.getActiveEmployees();
    if (activeEmployees.isEmpty()) {
      System.out.println("No active employees to fire.");
      return;
    }

    System.out.println("Select employee to fire:");
    for (int i = 0; i < activeEmployees.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, activeEmployees.get(i));
    }

    int choice = readIntInput("Enter employee number: ") - 1;
    if (choice < 0 || choice >= activeEmployees.size()) {
      System.out.println("Invalid employee selection.");
      return;
    }

    Employee employeeToFire = activeEmployees.get(choice);

    // Check if there are inactive employees of the same type
    ArrayList<Employee> inactiveEmployees = new ArrayList<>();
    if (employeeToFire instanceof Manager) {
      inactiveEmployees.addAll(company.getInactiveManagers());
    } else if (employeeToFire instanceof Worker) {
      inactiveEmployees.addAll(company.getInactiveWorkers());
    }

    if (inactiveEmployees.isEmpty()) {
      System.out.println("No inactive employees available to replace the fired employee.");
      return;
    }

    System.out.println("Select replacement employee:");
    for (int i = 0; i < inactiveEmployees.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveEmployees.get(i));
    }

    int replacementChoice = readIntInput("Enter replacement employee number: ") - 1;
    if (replacementChoice < 0 || replacementChoice >= inactiveEmployees.size()) {
      System.out.println("Invalid replacement employee selection.");
      return;
    }

    Employee replacementEmployee = inactiveEmployees.get(replacementChoice);

    if (company.replaceEmployee(employeeToFire, replacementEmployee)) {
      System.out.println("Employee replaced successfully.");
    } else {
      System.out.println("Failed to replace employee.");
    }
  }

  private void changeWarehouseManager() {
    // First, select an active warehouse
    Warehouse warehouse = selectActiveWarehouse("Select warehouse to change manager: ");
    if (warehouse == null) {
      System.out.println("No active warehouses available.");
      return;
    }

    // Get inactive managers
    ArrayList<Manager> inactiveManagers = company.getInactiveManagers();
    if (inactiveManagers.isEmpty()) {
      System.out.println("No inactive managers available for replacement.");
      return;
    }

    System.out.println("Select new manager:");
    for (int i = 0; i < inactiveManagers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveManagers.get(i));
    }

    int choice = readIntInput("Enter manager number: ") - 1;
    if (choice < 0 || choice >= inactiveManagers.size()) {
      System.out.println("Invalid manager selection.");
      return;
    }

    Manager newManager = inactiveManagers.get(choice);

    if (company.changeWarehouseManager(warehouse, newManager)) {
      System.out.println("Warehouse manager changed successfully.");
    } else {
      System.out.println("Failed to change warehouse manager.");
    }
  }

  private void changeSellPointManager() {
    // First, select an active sell point
    SellPoint sellPoint = selectActiveSellPoint("Select sell point to change manager: ");
    if (sellPoint == null) {
      System.out.println("No active sell points available.");
      return;
    }

    // Get inactive managers
    ArrayList<Manager> inactiveManagers = company.getInactiveManagers();
    if (inactiveManagers.isEmpty()) {
      System.out.println("No inactive managers available for replacement.");
      return;
    }

    System.out.println("Select new manager:");
    for (int i = 0; i < inactiveManagers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, inactiveManagers.get(i));
    }

    int choice = readIntInput("Enter manager number: ") - 1;
    if (choice < 0 || choice >= inactiveManagers.size()) {
      System.out.println("Invalid manager selection.");
      return;
    }

    Manager newManager = inactiveManagers.get(choice);

    if (company.changeSellPointManager(sellPoint, newManager)) {
      System.out.println("Sell point manager changed successfully.");
    } else {
      System.out.println("Failed to change sell point manager.");
    }
  }

  private void moveProduct() {
    // Select source warehouse
    Warehouse sourceWarehouse = selectActiveWarehouse("Select source warehouse: ");
    if (sourceWarehouse == null) {
      System.out.println("No active warehouses available.");
      return;
    }

    // Select source cell
    Cell sourceCell = selectCellWithProducts(sourceWarehouse, "Select source cell: ");
    if (sourceCell == null) {
      System.out.println("No cells with products available in the source warehouse.");
      return;
    }

    // Select product from source cell
    Product product = selectProductFromCell(sourceCell, "Select product to move: ");
    if (product == null) {
      System.out.println("No products available in the selected cell.");
      return;
    }

    // Get available quantity
    int availableQuantity = sourceCell.getProductQuantity(product);
    System.out.printf("Available quantity: %d%n", availableQuantity);

    // Get quantity to move
    int quantity = readIntInput("Enter quantity to move: ");
    if (quantity <= 0 || quantity > availableQuantity) {
      System.out.println("Invalid quantity.");
      return;
    }

    // Select target warehouse
    Warehouse targetWarehouse = selectActiveWarehouse("Select target warehouse: ");
    if (targetWarehouse == null) {
      System.out.println("No active warehouses available.");
      return;
    }

    // Select target cell
    Cell targetCell = selectCellWithCapacity(targetWarehouse, quantity, "Select target cell: ");
    if (targetCell == null) {
      System.out.println("No cells with sufficient capacity available in the target warehouse.");
      return;
    }

    // Move the product
    if (company.moveProductBetweenWarehouses(product, quantity, sourceWarehouse, sourceCell, targetWarehouse, targetCell)) {
      System.out.println("Product moved successfully.");
    } else {
      System.out.println("Failed to move product.");
    }
  }

  private Cell selectCellWithProducts(Warehouse warehouse, String prompt) {
    ArrayList<Cell> cells = warehouse.getCells();
    ArrayList<Cell> cellsWithProducts = new ArrayList<>();

    for (Cell cell : cells) {
      if (!cell.getProducts().isEmpty()) {
        cellsWithProducts.add(cell);
      }
    }

    if (cellsWithProducts.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < cellsWithProducts.size(); i++) {
      Cell cell = cellsWithProducts.get(i);
      System.out.printf("%d. %s (Products: %d)%n", 
          i + 1, cell, cell.getProducts().size());
    }

    int choice = readIntInput("Enter cell number: ") - 1;
    if (choice < 0 || choice >= cellsWithProducts.size()) {
      System.out.println("Invalid cell selection.");
      return null;
    }

    return cellsWithProducts.get(choice);
  }

  private Cell selectCellWithCapacity(Warehouse warehouse, int requiredCapacity, String prompt) {
    ArrayList<Cell> cells = warehouse.getCells();
    ArrayList<Cell> cellsWithCapacity = new ArrayList<>();

    for (Cell cell : cells) {
      if (cell.getAvailableCapacity() >= requiredCapacity) {
        cellsWithCapacity.add(cell);
      }
    }

    if (cellsWithCapacity.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < cellsWithCapacity.size(); i++) {
      Cell cell = cellsWithCapacity.get(i);
      System.out.printf("%d. %s (Available capacity: %d)%n", 
          i + 1, cell, cell.getAvailableCapacity());
    }

    int choice = readIntInput("Enter cell number: ") - 1;
    if (choice < 0 || choice >= cellsWithCapacity.size()) {
      System.out.println("Invalid cell selection.");
      return null;
    }

    return cellsWithCapacity.get(choice);
  }

  private Product selectProductFromCell(Cell cell, String prompt) {
    ArrayList<Product> products = new ArrayList<>(cell.getProducts());
    if (products.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < products.size(); i++) {
      Product product = products.get(i);
      System.out.printf("%d. %s (Quantity: %d)%n", 
          i + 1, product, cell.getProductQuantity(product));
    }

    int choice = readIntInput("Enter product number: ") - 1;
    if (choice < 0 || choice >= products.size()) {
      System.out.println("Invalid product selection.");
      return null;
    }

    return products.get(choice);
  }

  private void showWarehouseInfo() {
    Warehouse warehouse = selectWarehouse("Select warehouse to view information: ");
    if (warehouse == null) {
      System.out.println("No warehouses available.");
      return;
    }
    System.out.println(company.getWarehouseInfo(warehouse));
  }

  private void showWarehouseProductsInfo() {
    Warehouse warehouse = selectWarehouse("Select warehouse to view products: ");
    if (warehouse == null) {
      System.out.println("No warehouses available.");
      return;
    }
    System.out.println(company.getWarehouseProductsInfo(warehouse));
  }

  private void showSellPointInfo() {
    SellPoint sellPoint = selectSellPoint("Select sell point to view information: ");
    if (sellPoint == null) {
      System.out.println("No sell points available.");
      return;
    }
    System.out.println(company.getSellPointInfo(sellPoint));
  }

  private void showSellPointProductsInfo() {
    SellPoint sellPoint = selectSellPoint("Select sell point to view products: ");
    if (sellPoint == null) {
      System.out.println("No sell points available.");
      return;
    }
    System.out.println(company.getSellPointProductsInfo(sellPoint));
  }

  private void showSellPointProfitability() {
    SellPoint sellPoint = selectSellPoint("Select sell point to view profitability: ");
    if (sellPoint == null) {
      System.out.println("No sell points available.");
      return;
    }
    System.out.println(company.getSellPointInfo(sellPoint));
  }

  private void showAvailableProductsForPurchase() {
    System.out.println(company.getAvailableProductsForPurchase());
  }

  private SellPoint selectSellPoint(String prompt) {
    ArrayList<SellPoint> sellPoints = company.getAllSellPoints();
    if (sellPoints.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < sellPoints.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, sellPoints.get(i));
    }

    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      return null;
    }

    return sellPoints.get(choice);
  }
}
