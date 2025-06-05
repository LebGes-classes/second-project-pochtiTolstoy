package Storage.Cell;

import Product.Product;
import Storage.Entity;
import java.util.ArrayList;

public class Cell extends Entity {
  private int capacity;
  private ArrayList<Product> products;

  public Cell(String name, String description, int capacity) {
    super(name, description);
    this.capacity = capacity;
    this.products = new ArrayList<>();
  }

  public int getCapacity() { return capacity; }

  public int getAvailableCapacity() {
    int usedCapacity = products.stream().mapToInt(Product::getQuantity).sum();
    return capacity - usedCapacity;
  }

  public ArrayList<Product> getProducts() { return products; }

  public boolean hasProduct(Product product) {
    // TODO : should be refactored to use product comparator
    return products.stream().anyMatch(
        p
        -> p.getName().equals(product.getName()) &&
               p.getType() == product.getType());
  }

  public int getProductQuantity(Product product) {
    return products.stream()
        .filter(p -> p.equals(product))
        .mapToInt(Product::getQuantity)
        .sum();
  }

  public boolean addProduct(Product product) {
    return addProduct(product, product.getQuantity());
  }

  public boolean addProduct(Product product, int quantity) {
    if (getAvailableCapacity() < quantity) {
      return false;
    }
    for (Product p : products) {
      if (p.equals(product)) {
        p.setQuantity(p.getQuantity() + quantity);
        return true;
      }
    }
    products.add(new Product(product, quantity));
    return true;
  }

  public void removeProduct(Product product, int quantity) {
    int remainingQuantity = quantity;
    ArrayList<Product> toRemove = new ArrayList<>();

    for (Product p : products) {
      if (p.equals(product)) {
        if (p.getQuantity() <= remainingQuantity) {
          remainingQuantity -= p.getQuantity();
          toRemove.add(p);
        } else {
          p.setQuantity(p.getQuantity() - remainingQuantity);
          break;
        }
      }
    }

    products.removeAll(toRemove);
  }

  @Override
  public String toString() {
    return String.format("Cell {id=%s, name='%s', capacity=%d/%d, products=%d}",
                         getId(), getName(), capacity - getAvailableCapacity(),
                         capacity, products.size());
  }
}
