package Company;

import java.util.ArrayList;
import java.util.stream.Collectors;

import DataLoader.DataLoader;
import Storage.Warehouse.Warehouse;
import Storage.SellPoint.SellPoint;
import Person.Employee.Employee;
import Person.Customer.Customer;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Cell.Cell;
import Product.Product;
import Util.ProductType;
import Storage.Fabric.Fabric;

public class Company {
  private DataLoader dataLoader;
  private ArrayList<Warehouse> warehouses;
  private ArrayList<SellPoint> sellPoints;
  private ArrayList<Employee> employees;
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;
  private ArrayList<Fabric> fabrics;

  public Company(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
    warehouses = dataLoader.loadWarehouses();
    sellPoints = dataLoader.loadSellPoints();
    employees = dataLoader.loadEmployees();
    customers = dataLoader.loadCustomers();
    products = dataLoader.loadProducts();
    fabrics = dataLoader.loadFabrics();
  }

  public void createWarehouse(String name, String description, 
      Manager manager, Worker worker) {
    Warehouse warehouse = new Warehouse(name, description, manager, worker);
    warehouses.add(warehouse);
    saveData();
  }

  public void createCell(String name, String description, int capacity, Warehouse warehouse) {
    Cell cell = new Cell(name, description, capacity);
    warehouse.addCell(cell);
    saveData();
  }

  public void createSellPoint(String name, String description, 
      Manager manager, Worker worker) {
    SellPoint sellPoint = new SellPoint(name, description, manager, worker);
    sellPoints.add(sellPoint);
    saveData();
  }

  public void createProduct(String name, String description, double price, int quantity, ProductType type) {
    Product product = new Product(name, description, price, quantity, type);  
    products.add(product);
    saveData();
  }

  public void createFabric(String name, String description, ProductType type) {
    Fabric fabric = new Fabric(name, description, type, type.getTimeToProduce());
    fabrics.add(fabric);
    saveData();
  }

  public void closeWarehouse(Warehouse warehouse) {
    warehouse.setActive(false);
    // Deactivate warehouse employees
    warehouse.getManager().setActive(false);
    warehouse.getWorker().setActive(false);
    saveData();
  }

  public void closeSellPoint(SellPoint sellPoint) {
    sellPoint.setActive(false);
    saveData();
  }

  public ArrayList<Warehouse> getActiveWarehouses() {
    return warehouses.stream()
      .filter(Warehouse::isActive)
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<Warehouse> getAllWarehouses() {
    return new ArrayList<>(warehouses);
  }

  public ArrayList<SellPoint> getActiveSellPoints() {
    return sellPoints.stream()
      .filter(SellPoint::isActive)
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<SellPoint> getAllSellPoints() {
    return new ArrayList<>(sellPoints);
  }

  public ArrayList<Product> getAvailableProducts() {
    return new ArrayList<>(products);
  }

  public void hireManager(String name, String description, String contactInfo, int age, String position, double salary) {
    Employee employee = new Manager(name, description, contactInfo, age, position, salary);
    employees.add(employee);
    saveData();
  }

  public void hireWorker(String name, String description, String contactInfo, int age, String position, double salary) {
    Employee employee = new Worker(name, description, contactInfo, age, position, salary);
    employees.add(employee);
    saveData();
  }

  public boolean fireEmployee(Employee employee) {
    employee.setActive(false);
    saveData();
    return true;
  }

  public boolean replaceEmployee(Employee oldEmployee, Employee newEmployee) {
    if (oldEmployee == null || newEmployee == null || !oldEmployee.isActive() || newEmployee.isActive()) {
      return false;
    }

    // Find and update the employee in all warehouses
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getManager() == oldEmployee) {
        warehouse.setManager((Manager) newEmployee);
      }
      if (warehouse.getWorker() == oldEmployee) {
        warehouse.addWorker((Worker) newEmployee);
      }
    }

    // Find and update the employee in all sell points
    for (SellPoint sellPoint : sellPoints) {
      if (sellPoint.getManager() == oldEmployee) {
        sellPoint.setManager((Manager) newEmployee);
      }
    }

    oldEmployee.setActive(false);
    newEmployee.setActive(true);
    saveData();
    return true;
  }

  public ArrayList<Employee> getAllEmployees() {
    return new ArrayList<>(employees);
  }

  public ArrayList<Employee> getActiveEmployees() {
    return employees.stream()
        .filter(Employee::isActive)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<Manager> getInactiveManagers() {
    return employees.stream()
      .filter(employee -> employee instanceof Manager)
      .map(employee -> (Manager) employee)
      .filter(manager -> !manager.isActive())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<Worker> getInactiveWorkers() {
    return employees.stream()
      .filter(employee -> employee instanceof Worker)
      .map(employee -> (Worker) employee)
      .filter(worker -> !worker.isActive())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<Fabric> getAllFabrics() {
    return new ArrayList<>(fabrics);
  }

  public boolean moveProductToSellPoint(Product product, int quantity, 
      Warehouse warehouse, SellPoint sellPoint) {
    if (!warehouse.isActive() || !sellPoint.isActive()) {
      return false;
    }

    Cell sourceCell = null;
    for (Cell cell : warehouse.getCells()) {
      if (cell.hasProduct(product) && cell.getProductQuantity(product) >= quantity) {
        sourceCell = cell;
        break;
      }
    }

    if (sourceCell == null) {
      return false;
    }

    Cell targetCell = null;
    for (Cell cell : sellPoint.getCells()) {
      if (cell.getAvailableCapacity() >= quantity) {
        targetCell = cell;
        break;
      }
    }

    if (targetCell == null) {
      targetCell = new Cell("Cell-" + sellPoint.getCells().size(), 
                          "Auto-created cell", quantity);
      sellPoint.addCell(targetCell);
    }

    sourceCell.removeProduct(product, quantity);
    targetCell.addProduct(product, quantity);
    saveData();
    return true;
  }

  public boolean moveProductBetweenWarehouses(Product product, int quantity,
      Warehouse sourceWarehouse, Cell sourceCell,
      Warehouse targetWarehouse, Cell targetCell) {
    if (!sourceWarehouse.isActive() || !targetWarehouse.isActive()) {
      return false;
    }

    // Verify source cell has enough products
    if (!sourceCell.hasProduct(product) || sourceCell.getProductQuantity(product) < quantity) {
      return false;
    }

    // Verify target cell has enough capacity
    if (targetCell.getAvailableCapacity() < quantity) {
      return false;
    }

    // Move the product
    sourceCell.removeProduct(product, quantity);
    targetCell.addProduct(product, quantity);
    saveData();
    return true;
  }

  public boolean sellProductToCustomer(Product product, int quantity, 
      SellPoint sellPoint, Customer customer) {
    if (!sellPoint.isActive()) {
      return false;
    }

    if (sellPoint.sellProduct(product, quantity)) {
      customer.addPurchase(product, quantity);
      saveData();
      return true;
    }
    return false;
  }

  public boolean returnProductFromCustomer(Product product, int quantity, 
      SellPoint sellPoint, Customer customer) {
    if (!sellPoint.isActive()) {
      return false;
    }

    if (sellPoint.returnProduct(product, quantity)) {
      customer.removePurchase(product, quantity);
      saveData();
      return true;
    }
    return false;
  }

  public double getTotalCompanyRevenue() {
    return sellPoints.stream()
      .mapToDouble(SellPoint::getTotalRevenue)
      .sum();
  }

  public double getTotalCompanyExpenses() {
    return sellPoints.stream()
      .mapToDouble(SellPoint::getTotalExpenses)
      .sum();
  }

  public double getTotalCompanyProfit() {
    return getTotalCompanyRevenue() - getTotalCompanyExpenses();
  }

  public ArrayList<Customer> getAllCustomers() {
    return new ArrayList<>(customers);
  }

  public ArrayList<Customer> getActiveCustomers() {
    return customers.stream()
        .filter(Customer::isActive)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public void addCustomer(String name, String description, String contactInfo, int age) {
    Customer customer = new Customer(name, description, contactInfo, age);
    customers.add(customer);
    saveData();
  }

  public boolean reopenWarehouse(Warehouse warehouse, Manager manager, Worker worker) {
    if (manager == null || worker == null) {
      return false;
    }

    warehouse.setActive(true);
    warehouse.setManager(manager);
    warehouse.addWorker(worker);
    manager.setActive(true);
    worker.setActive(true);
    saveData();
    return true;
  }

  public boolean changeWarehouseManager(Warehouse warehouse, Manager newManager) {
    if (warehouse == null || newManager == null || !warehouse.isActive() || newManager.isActive()) {
      return false;
    }

    Manager oldManager = warehouse.getManager();
    oldManager.setActive(false);
    warehouse.setManager(newManager);
    newManager.setActive(true);
    saveData();
    return true;
  }

  public boolean changeSellPointManager(SellPoint sellPoint, Manager newManager) {
    if (sellPoint == null || newManager == null || !sellPoint.isActive() || newManager.isActive()) {
      return false;
    }

    Manager oldManager = sellPoint.getManager();
    oldManager.setActive(false);
    sellPoint.setManager(newManager);
    newManager.setActive(true);
    saveData();
    return true;
  }

  public String getWarehouseInfo(Warehouse warehouse) {
    StringBuilder info = new StringBuilder();
    info.append("Warehouse Information:\n");
    info.append("Name: ").append(warehouse.getName()).append("\n");
    info.append("Description: ").append(warehouse.getDescription()).append("\n");
    info.append("Status: ").append(warehouse.isActive() ? "Active" : "Inactive").append("\n");
    info.append("Manager: ").append(warehouse.getManager()).append("\n");
    info.append("Worker: ").append(warehouse.getWorker()).append("\n");
    info.append("Total Cells: ").append(warehouse.getCells().size()).append("\n");
    info.append("Total Products: ").append(warehouse.getTotalProducts()).append("\n");
    return info.toString();
  }

  public String getSellPointInfo(SellPoint sellPoint) {
    StringBuilder info = new StringBuilder();
    info.append("Sell Point Information:\n");
    info.append("Name: ").append(sellPoint.getName()).append("\n");
    info.append("Description: ").append(sellPoint.getDescription()).append("\n");
    info.append("Status: ").append(sellPoint.isActive() ? "Active" : "Inactive").append("\n");
    info.append("Manager: ").append(sellPoint.getManager()).append("\n");
    info.append("Total Cells: ").append(sellPoint.getCells().size()).append("\n");
    info.append("Total Products: ").append(sellPoint.getTotalProducts()).append("\n");
    info.append("Total Revenue: ").append(sellPoint.getTotalRevenue()).append("\n");
    info.append("Total Expenses: ").append(sellPoint.getTotalExpenses()).append("\n");
    info.append("Total Profit: ").append(sellPoint.getTotalRevenue() - sellPoint.getTotalExpenses()).append("\n");
    return info.toString();
  }

  public String getWarehouseProductsInfo(Warehouse warehouse) {
    StringBuilder info = new StringBuilder();
    info.append("Products in Warehouse ").append(warehouse.getName()).append(":\n");
    for (Cell cell : warehouse.getCells()) {
      info.append("\nCell: ").append(cell.getName()).append("\n");
      for (Product product : cell.getProducts()) {
        info.append("- ").append(product.getName())
            .append(" (Quantity: ").append(cell.getProductQuantity(product))
            .append(", Price: ").append(product.getPrice())
            .append(")\n");
      }
    }
    return info.toString();
  }

  public String getSellPointProductsInfo(SellPoint sellPoint) {
    StringBuilder info = new StringBuilder();
    info.append("Products in Sell Point ").append(sellPoint.getName()).append(":\n");
    for (Cell cell : sellPoint.getCells()) {
      info.append("\nCell: ").append(cell.getName()).append("\n");
      for (Product product : cell.getProducts()) {
        info.append("- ").append(product.getName())
            .append(" (Quantity: ").append(cell.getProductQuantity(product))
            .append(", Price: ").append(product.getPrice())
            .append(")\n");
      }
    }
    return info.toString();
  }

  public String getAvailableProductsForPurchase() {
    StringBuilder info = new StringBuilder();
    info.append("Products Available for Purchase:\n");
    for (Product product : products) {
      info.append("- ").append(product.getName())
          .append(" (Type: ").append(product.getType())
          .append(", Price: ").append(product.getPrice())
          .append(")\n");
    }
    return info.toString();
  }

  private void saveData() {
    dataLoader.saveWarehouses(warehouses);
    dataLoader.saveSellPoints(sellPoints);
    dataLoader.saveEmployees(employees);
    dataLoader.saveCustomers(customers);
    dataLoader.saveProducts(products);
    dataLoader.saveFabrics(fabrics);
  }
}
