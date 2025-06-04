package Storage.SellPoint;

import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Product.Product;
import Storage.Cell.Cell;
import Storage.Entity;
import java.util.*;

public class SellPoint extends Entity {
  private boolean isActive;
  private Manager manager;
  private Worker worker;
  private ArrayList<Product> products;
  private double totalRevenue;
  private double totalExpenses;

  public SellPoint(String name, String description, Manager manager,
                   Worker worker) {
    super(name, description);
    this.isActive = true;
    this.manager = manager;
    this.manager.setActive(true);
    this.worker = worker;
    this.worker.setActive(true);
    this.products = new ArrayList<>();
    this.totalRevenue = 0.0;
    this.totalExpenses = 0.0;
  }

  public boolean isActive() { return isActive; }

  public void setActive(boolean active) { isActive = active; }

  public Manager getManager() { return manager; }

  public void addManager(Manager manager) { this.manager = manager; }

  public Worker getWorker() { return worker; }

  public void addWorker(Worker worker) { this.worker = worker; }

  public ArrayList<Product> getProducts() { return products; }

  public int getTotalProducts() {
    int sum = 0;
    for (int i = 0; i < products.size(); ++i) {
      sum += products.get(i).getQuantity();
    }
    return sum;
  }

  public double getTotalRevenue() { return totalRevenue; }

  public void addRevenue(double amount) { this.totalRevenue += amount; }

  public double getTotalExpenses() { return totalExpenses; }

  public void addExpense(double amount) { this.totalExpenses += amount; }

  public double getProfit() { return totalRevenue - totalExpenses; }

  public boolean sellProduct(Product product, int quantity) {
    for (Iterator<Product> iterator = products.iterator();
         iterator.hasNext();) {
      Product current = iterator.next();
      if (!current.equals(product)) {
        continue;
      }

      int available = current.getQuantity();
      if (available < quantity) {
        return false;
      }

      int updatedQuantity = available - quantity;
      current.setQuantity(updatedQuantity);
      addRevenue(current.getPrice() * quantity);

      if (updatedQuantity == 0) {
        iterator.remove();
      }
      return true;
    }
    return false;
  }

  public boolean returnProduct(Product product, int quantity) {
    for (Product p : products) {
      if (p.equals(product)) {
        p.setQuantity(p.getQuantity() + quantity);
        addExpense(product.getPrice() * quantity);
        return true;
      }
    }

    Product newProduct =
        new Product(product.getName(), product.getDescription(),
                    product.getPrice(), quantity, product.getType());
    products.add(newProduct);
    addExpense(product.getPrice() * quantity);
    return true;
  }

  public void addProduct(Product product) {
    for (Product p : products) {
      if (p.equals(product)) {
        p.setQuantity(p.getQuantity() + product.getQuantity());
        return;
      }
    }
    products.add(product);
  }

  public int getProductQuantity(Product product) {
    return products.stream()
        .filter(p
                -> p.getName().equals(product.getName()) &&
                       p.getType() == product.getType())
        .mapToInt(Product::getQuantity)
        .sum();
  }

  @Override
  public String toString() {
    return String.format(
        "SellPoint{name='%s', active=%b, manager='%s', worker='%s', "
            + "revenue=%.2f, expenses=%.2f, profit=%.2f}",
        getName(), isActive, manager.getName(), worker.getName(), totalRevenue,
        totalExpenses, getProfit());
  }
}
