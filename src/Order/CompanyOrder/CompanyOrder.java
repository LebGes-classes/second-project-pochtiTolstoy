package Order.CompanyOrder;

import Order.Order;
import Product.Product;

public class CompanyOrder extends Order {
  Product product;
  int quantity;
  public CompanyOrder(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() { return product; }

  public int getQuantity() { return quantity; }
}
