package Product;

import Storage.Entity;
import Util.ProductType;

public class ProductSpecification extends Entity {
  private double price;
  private ProductType type;

  public ProductSpecification(String name, String description, double price,
                              ProductType type) {
    super(name, description);
    this.price = price;
    this.type = type;
  }

  public double getPrice() { return price; }

  public ProductType getType() { return type; }
}
