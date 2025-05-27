package DataLoader;
import Person.Customer.Customer;
import Person.Employee.Employee;
import Product.Product;
import Product.ProductSpecification;
import Storage.Fabric.Fabric;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import java.io.*;
import java.util.ArrayList;

public class DataLoaderImpl implements DataLoader {
  private String WAREHOUSES_FILE;
  private String SELL_POINTS_FILE;
  private String EMPLOYEES_FILE;
  private String CUSTOMERS_FILE;
  private String PRODUCTS_FILE;
  private String FABRICS_FILE;
  private String DATA_DIR;

  public DataLoaderImpl(String pathToFile) {
    DATA_DIR = pathToFile;
    WAREHOUSES_FILE = DATA_DIR + "/warehouses.ser";
    SELL_POINTS_FILE = DATA_DIR + ("/sell_points."
                                   + "ser");
    EMPLOYEES_FILE = DATA_DIR + "/employees.ser";
    CUSTOMERS_FILE = DATA_DIR + "/customers.ser";
    PRODUCTS_FILE = DATA_DIR + "/products.ser";
    FABRICS_FILE = DATA_DIR + "/fabrics.ser";
    File directory = new File(DATA_DIR);
    if (!directory.exists()) {
      directory.mkdir();
    }
  }

  private <T> void saveToFile(ArrayList<T> data, String filename) {
    try (ObjectOutputStream out =
             new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeObject(data);
    } catch (IOException e) {
      System.err.println("Error: failed to save data in " + filename + ": " +
                         e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private <T> ArrayList<T> loadFromFile(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      return new ArrayList<>();
    }

    try (ObjectInputStream in =
             new ObjectInputStream(new FileInputStream(filename))) {
      return (ArrayList<T>)in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Error: loading data from " + filename + ": " +
                         e.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public void saveWarehouses(ArrayList<Warehouse> warehouses) {
    saveToFile(warehouses, WAREHOUSES_FILE);
  }

  @Override
  public void saveSellPoints(ArrayList<SellPoint> sellPoints) {
    saveToFile(sellPoints, SELL_POINTS_FILE);
  }

  @Override
  public void saveEmployees(ArrayList<Employee> employees) {
    saveToFile(employees, EMPLOYEES_FILE);
  }

  @Override
  public void saveCustomers(ArrayList<Customer> customers) {
    saveToFile(customers, CUSTOMERS_FILE);
  }

  @Override
  public void saveProductsToPurchase(ArrayList<ProductSpecification> products) {
    saveToFile(products, PRODUCTS_FILE);
  }

  @Override
  public void saveFabrics(ArrayList<Fabric> fabrics) {
    saveToFile(fabrics, FABRICS_FILE);
  }

  @Override
  public ArrayList<Warehouse> loadWarehouses() {
    return loadFromFile(WAREHOUSES_FILE);
  }

  @Override
  public ArrayList<SellPoint> loadSellPoints() {
    return loadFromFile(SELL_POINTS_FILE);
  }

  @Override
  public ArrayList<Employee> loadEmployees() {
    return loadFromFile(EMPLOYEES_FILE);
  }

  @Override
  public ArrayList<Customer> loadCustomers() {
    return loadFromFile(CUSTOMERS_FILE);
  }

  @Override
  public ArrayList<ProductSpecification> loadProductsToPurchase() {
    return loadFromFile(PRODUCTS_FILE);
  }

  @Override
  public ArrayList<Fabric> loadFabrics() {
    return loadFromFile(FABRICS_FILE);
  }
}
