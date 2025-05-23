package Person.Customer;

import Person.Person;
import Product.Product;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
  private ArrayList<Product> purchases;
  private boolean isActive;

  public Customer(String name, String description, String contactInfo,
                  int age) {
    super(name, description, contactInfo, age);
    this.purchases = new ArrayList<>();
    this.isActive = true;
  }

  public boolean isActive() { return isActive; }

  public void setActive(boolean active) { isActive = active; }

  public void addPurchase(Product product, int quantity) {
    for (Product p : purchases) {
      if (p.isSameProduct(product)) {
        p.setQuantity(p.getQuantity() + quantity);
        return;
      }
    }
    purchases.add(new Product(product, quantity));
  }

  public void removePurchase(Product product, int quantityToRemove) {
    for (Product p : purchases) {
      if (p.isSameProduct(product)) {
        int newQuantity = p.getQuantity() - quantityToRemove;
        if (newQuantity <= 0) {
          purchases.remove(p);
        } else {
          p.setQuantity(newQuantity);
        }
        return;
      }
    }
  }

  public ArrayList<Product> getPurchases() { return purchases; }

  public double getTotalSpent() {
    return purchases.stream()
        .mapToDouble(p -> p.getPrice() * p.getQuantity())
        .sum();
  }

  @Override
  public String toString() {
    return String.format(
        "Customer{name='%s', age=%d, active=%b, totalSpent=%.2f, purchases=%d}",
        getName(), getAge(), isActive, getTotalSpent(), purchases.size());
  }
}
