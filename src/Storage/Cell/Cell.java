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
    return products.stream().anyMatch(
        p
        -> p.getName().equals(product.getName()) &&
               p.getType() == product.getType());
  }

  public int getProductQuantity(Product product) {
    return products.stream()
        .filter(p
                -> p.getName().equals(product.getName()) &&
                       p.getType() == product.getType())
        .mapToInt(Product::getQuantity)
        .sum();
  }

  public void addProduct(Product product) {
    if (getAvailableCapacity() < product.getQuantity()) {
      throw new IllegalStateException("Cell does not have enough capacity");
    }
    products.add(new Product(product));
  }

  public void addProduct(Product product, int quantity) {
    if (getAvailableCapacity() < quantity) {
      throw new IllegalStateException("Cell does not have enough capacity");
    }
    Product newProduct = new Product(product);
    newProduct.setQuantity(quantity);
    products.add(newProduct);
  }

  public void removeProduct(Product product) { products.remove(product); }

  public void removeProduct(Product product, int quantity) {
    int remainingQuantity = quantity;
    ArrayList<Product> toRemove = new ArrayList<>();

    for (Product p : products) {
      if (p.getName().equals(product.getName()) &&
          p.getType() == product.getType()) {
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
    return String.format("Cell{id=%s, name='%s', capacity=%d/%d, products=%d}",
                         getId(), getName(), getAvailableCapacity(), capacity,
                         products.size());
  }
}
