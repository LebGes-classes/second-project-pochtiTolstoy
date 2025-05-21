package DataLoader;

import Person.Customer.Customer;
import Person.Employee.Employee;
import Product.Product;
import Storage.Fabric.Fabric;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import java.util.ArrayList;

public interface DataLoader {
  void saveWarehouses(ArrayList<Warehouse> warehouses);
  void saveSellPoints(ArrayList<SellPoint> sellPoints);
  void saveEmployees(ArrayList<Employee> employees);
  void saveCustomers(ArrayList<Customer> customers);
  void saveProductsToPurchase(ArrayList<Product> products);
  void saveFabrics(ArrayList<Fabric> fabrics);

  ArrayList<Warehouse> loadWarehouses();
  ArrayList<SellPoint> loadSellPoints();
  ArrayList<Employee> loadEmployees();
  ArrayList<Customer> loadCustomers();
  ArrayList<Product> loadProductsToPurchase();
  ArrayList<Fabric> loadFabrics();
}
