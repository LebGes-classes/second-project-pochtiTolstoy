package DataLoader;
import java.io.*;
import java.util.ArrayList;
import Storage.Warehouse.Warehouse;
import Storage.SellPoint.SellPoint;
import Person.Employee.Employee;
import Person.Customer.Customer;
import Product.Product;

public class DataLoaderImpl implements DataLoader {
  private static final String DATA_DIR = "res";
  private static final String WAREHOUSES_FILE = DATA_DIR + "/warehouses.ser";
  private static final String SELL_POINTS_FILE = DATA_DIR + "/sell_points.ser";
  private static final String EMPLOYEES_FILE = DATA_DIR + "/employees.ser";
  private static final String CUSTOMERS_FILE = DATA_DIR + "/customers.ser";
  private static final String PRODUCTS_FILE = DATA_DIR + "/products.ser";

  public DataLoaderImpl() {
    File directory = new File(DATA_DIR);
    if (!directory.exists()) {
      directory.mkdir();
    }
  }

  private <T> void saveToFile(ArrayList<T> data, String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeObject(data);
    } catch (IOException e) {
      System.err.println("Error: failed to save data in " + filename + ": " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private <T> ArrayList<T> loadFromFile(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      return new ArrayList<>();
    }

    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      return (ArrayList<T>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Error: loading data from " + filename + ": " + e.getMessage());
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
  public void saveProducts(ArrayList<Product> products) {
      saveToFile(products, PRODUCTS_FILE);
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
  public ArrayList<Product> loadProducts() {
      return loadFromFile(PRODUCTS_FILE);
  }
}
