package DataLoader;

import java.util.ArrayList;
import Storage.Warehouse.Warehouse;
import Storage.SellPoint.SellPoint;
import Person.Employee.Employee;
import Person.Customer.Customer;
import Product.Product;
import Storage.Fabric.Fabric;

public interface DataLoader {
  void saveWarehouses(ArrayList<Warehouse> warehouses);
  void saveSellPoints(ArrayList<SellPoint> sellPoints);
  void saveEmployees(ArrayList<Employee> employees);
  void saveCustomers(ArrayList<Customer> customers);
  void saveProducts(ArrayList<Product> products);
  void saveFabrics(ArrayList<Fabric> fabrics);

  ArrayList<Warehouse> loadWarehouses();
  ArrayList<SellPoint> loadSellPoints();
  ArrayList<Employee> loadEmployees();
  ArrayList<Customer> loadCustomers();
  ArrayList<Product> loadProducts();
  ArrayList<Fabric> loadFabrics();
}
