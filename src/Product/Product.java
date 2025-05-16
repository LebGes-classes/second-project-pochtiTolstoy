package Product;

import Storage.Entity;

public class Product extends Entity {
  private double price;
  private int quantity;

  public Product(String name, String description, double price, int quantity) {
    super(name, description);
    this.price = price;
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return String.format("Product{name='%s', price=%.2f, quantity=%d}", 
      getName(), price, quantity);
  }
}

