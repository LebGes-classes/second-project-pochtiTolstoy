package Order.CompanyOrder;

import Company.Company;
import Order.Order;
import Product.Product;
import UI.BaseUI;
import Util.ProductSelector;

public class CompanyOrder extends Order {
  Product product;
  int quantity;
  private CompanyOrder(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public static CompanyOrder createCompanyOrder(Company company, BaseUI ui) {
    Product product = ProductSelector.selectProduct(
        company, ui, "Choose available product to purchase:");
    if (product == null) {
      ui.printError("No products available!");
      return null;
    }
    int quantity = ui.readIntInput("Enter quantity: ");
    CompanyOrder order = new CompanyOrder(product, quantity);
    return order;
  }

  public Product getProduct() { return product; }

  public int getQuantity() { return quantity; }
}
