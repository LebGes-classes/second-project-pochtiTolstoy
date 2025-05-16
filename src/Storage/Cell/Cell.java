package Storage.Cell;

import java.util.ArrayList;
import Storage.Entity;
import Product.Product;

public class Cell extends Entity {
  private int capacity;
  private ArrayList<Product> products;

  public Cell(String name, String description, int capacity) {
    super(name, description);
    this.capacity = capacity;
    this.products = new ArrayList<>();
  }

  public int getCapacity() {
    return capacity;
  }

  public int getAvailableCapacity() {
    int usedCapacity = products.stream()
      .mapToInt(Product::getQuantity)
      .sum();
    return capacity - usedCapacity;
  }

  public ArrayList<Product> getProducts() {
    return new ArrayList<>(products);
  }

  public void addProduct(Product product) {
    if (getAvailableCapacity() < product.getQuantity()) {
      throw new IllegalStateException("Cell does not have enough capacity");
    }
    products.add(product);
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }

  @Override
  public String toString() {
    return String.format("Cell{id=%s, name='%s', capacity=%d/%d, products=%d}",
      getId(), getName(), products.size(), capacity, products.size());
  }
}
