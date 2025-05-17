package Person.Customer;

import Person.Person;
import Product.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends Person {
    private Map<Product, Integer> purchases;
    private boolean isActive;

    public Customer(String name, String description, String contactInfo, int age) {
        super(name, description, contactInfo, age);
        this.purchases = new HashMap<>();
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addPurchase(Product product, int quantity) {
        purchases.merge(product, quantity, Integer::sum);
    }

    public void removePurchase(Product product, int quantity) {
        int currentQuantity = purchases.getOrDefault(product, 0);
        if (currentQuantity <= quantity) {
            purchases.remove(product);
        } else {
            purchases.put(product, currentQuantity - quantity);
        }
    }

    public Map<Product, Integer> getPurchases() {
        return new HashMap<>(purchases);
    }

    public double getTotalSpent() {
        return purchases.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    @Override
    public String toString() {
        return String.format("Customer{name='%s', age=%d, active=%b, totalSpent=%.2f, purchases=%d}",
            getName(), getAge(), isActive, getTotalSpent(), purchases.size());
    }
}
