package Util;

import Company.Company;
import Product.Product;
import Product.ProductSpecification;
import UI.BaseUI;
import java.util.ArrayList;

public final class ProductSelector {
  private ProductSelector() {}

  public static ProductSpecification
  selectProductSpecification(Company company, BaseUI ui, String prompt) {
    ArrayList<ProductSpecification> products =
        company.getAvailableProductsToPurchase();
    if (products.isEmpty()) {
      return null;
    }
    ui.printInfo(prompt);
    for (int i = 0; i < products.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, products.get(i)));
    }
    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= products.size()) {
      return null;
    }
    return products.get(choice);
  }
}
