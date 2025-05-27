package Order.CompanyOrder;

import Company.Company;
import Product.Product;
import Product.ProductSpecification;
import UI.BaseUI;
import Util.ProductSelector;

public class CompanyOrder {
  private ProductSpecification spec;
  private int quantity;
  private CompanyOrder(ProductSpecification spec, int quantity) {
    this.spec = spec;
    this.quantity = quantity;
  }

  public static CompanyOrder createCompanyOrder(Company company, BaseUI ui) {
    ProductSpecification spec = ProductSelector.selectProductSpecification(
        company, ui, "Choose available product to purchase:");
    if (spec == null) {
      ui.printError("No products available!");
      return null;
    }
    int quantity = ui.readIntInput("Enter quantity: ");
    CompanyOrder order = new CompanyOrder(spec, quantity);
    return order;
  }

  public ProductSpecification getProductSpecification() { return spec; }

  public int getQuantity() { return quantity; }
}
