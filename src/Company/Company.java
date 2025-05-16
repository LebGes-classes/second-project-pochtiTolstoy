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

public class Company {
  private DataLoader dataLoader;
  private ArrayList<Warehouse> warehouses;
  private ArrayList<SellPoint> sellPoints;
  private ArrayList<Employee> employees;
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;

  public Company(DataLoader dataLoader) {
    this.dataLoader = dataLoader;
    warehouses = dataLoader.loadWarehouses();
    sellPoints = dataLoader.loadSellPoints();
    employees = dataLoader.loadEmployees();
    customers = dataLoader.loadCustomers();
    products = dataLoader.loadProducts();
  }

  public void createWarehouse(String name, String description, 
      Manager manager, Worker worker) {
    Warehouse warehouse = new Warehouse(name, description, manager, worker);
    warehouses.add(warehouse);
    saveData();
  }

  public void createCell(String name, String description, int capactiy, Warehouse warehouse) {
    Cell cell = new Cell(name, description, capactiy);
    warehouse.addCell(cell);
    saveData();
  }

  public void createProduct(String name, String description, double price, int quantity, ProductType type) {
    Product product = new Product(name, description, price, quantity, type);  
    products.add(product);
    saveData();
  }

  public void closeWarehouse(Warehouse warehouse) {
    warehouse.setActive(false);
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

  private void saveData() {
    dataLoader.saveWarehouses(warehouses);
    dataLoader.saveSellPoints(sellPoints);
    dataLoader.saveEmployees(employees);
    dataLoader.saveCustomers(customers);
    dataLoader.saveProducts(products);
  }
}
