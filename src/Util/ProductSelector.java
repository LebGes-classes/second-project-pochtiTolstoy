package Util;

import Company.Company;
import Person.Customer.Customer;
import Product.Product;
import Product.ProductSpecification;
import Storage.Cell.Cell;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import UI.BaseUI;
import java.util.List;
import java.util.function.Supplier;

public final class ProductSelector {
  private ProductSelector() {}

  public static ProductSpecification
  selectProductSpecification(Company company, BaseUI ui, String prompt) {
    return selectFromSource(ui, prompt, company::getAvailableProductsToPurchase,
                            "No product specifications available",
                            "Invalid selection!");
  }

  public static Product selectProductFromWarehouse(Warehouse warehouse,
                                                   BaseUI ui, String prompt) {
    return selectFromSource(ui, prompt, warehouse::getProducts,
                            "No products available in warehouse",
                            "Invalid product selection!");
  }

  public static Product selectProductFromCell(Cell cell, BaseUI ui,
                                              String prompt) {
    return selectFromSource(ui, prompt, cell::getProducts,
                            "No products available in cell",
                            "Invalid product selection!");
  }

  public static Product selectProductFromSellPoint(SellPoint sellPoint,
                                                   BaseUI ui, String prompt) {
    return selectFromSource(ui, prompt, sellPoint::getProducts,
                            "No products available at sell point",
                            "Invalid product selection!");
  }

  public static Product selectProductFromCustomer(Customer customer, BaseUI ui,
                                                  String prompt) {
    return selectFromSource(ui, prompt, customer::getPurchases,
                            "Customer has no products",
                            "Invalid product selection!");
  }

  private static <T> T selectFromSource(BaseUI ui, String prompt,
                                        Supplier<List<T>> source,
                                        String emptyMessage,
                                        String invalidMessage) {
    List<T> items = source.get();

    if (items.isEmpty()) {
      ui.printError(emptyMessage);
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < items.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, items.get(i)));
    }

    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= items.size()) {
      ui.printError(invalidMessage);
      return null;
    }

    return items.get(choice);
  }
}
