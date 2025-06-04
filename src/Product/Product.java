package Product;

import Storage.Entity;
import Util.ProductType;
import java.util.Objects;

public class Product extends Entity {
  private double price;
  private int quantity;
  private ProductType type;

  public Product(String name, String description, double price, int quantity,
                 ProductType type) {
    super(name, description);
    this.price = price;
    this.quantity = quantity;
    this.type = type;
  }

  public Product(Product product) {
    this(product.getName(), product.getDescription(), product.getPrice(),
         product.getQuantity(), product.getType());
  }

  public Product(Product product, int quantity) {
    this(product.getName(), product.getDescription(), product.getPrice(),
         quantity, product.getType());
  }

  public Product(ProductSpecification spec, int quantity) {
    this(spec.getName(), spec.getDescription(), spec.getPrice(), quantity,
         spec.getType());
  }

  public double getPrice() { return price; }

  public void setPrice(double price) { this.price = price; }

  public int getQuantity() { return quantity; }

  public void setQuantity(int quantity) { this.quantity = quantity; }

  public ProductType getType() { return type; }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Product product = (Product)o;
    return Double.compare(product.price, price) == 0 &&
        Objects.equals(getName(), product.getName()) &&
        Objects.equals(getDescription(), product.getDescription()) &&
        type == product.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getDescription(), price, type);
  }

  @Override
  public String toString() {
    return String.format(
        "Product{name='%s', price=%.2f, quantity=%d, type='%s'}", getName(),
        price, quantity, type.toString());
  }
}
