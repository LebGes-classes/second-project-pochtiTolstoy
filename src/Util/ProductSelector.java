package Util;

import Company.Company;
import Product.Product;
import UI.BaseUI;
import java.util.ArrayList;

public final class ProductSelector {
  private ProductSelector() {}

  public static Product selectProduct(Company company, BaseUI ui,
                                      String prompt) {
    ArrayList<Product> products = company.getAvailableProductsToPurchase();
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
