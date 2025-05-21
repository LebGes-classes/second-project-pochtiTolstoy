package Util;

import Company.Company;
import Order.CompanyOrder.CompanyOrder;
import Storage.Fabric.Fabric;
import UI.BaseUI;
import java.util.ArrayList;

public final class FabricSelector {
  private FabricSelector() {}

  public static Fabric selectFabric(Company company, BaseUI ui) {
    ArrayList<Fabric> fabrics = company.getAllFabrics();
    if (fabrics.isEmpty()) {
      return null;
    }
    ui.printInfo("Select fabric: ");
    for (int i = 0; i < fabrics.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, fabrics.get(i)));
    }
    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= fabrics.size()) {
      return null;
    }
    return fabrics.get(choice);
  }

  public static Fabric getAvailableFabric(Company company, CompanyOrder order) {
    ProductType targetType = order.getProduct().getType();
    ArrayList<Fabric> fabrics = company.getAllFabrics();

    for (Fabric fabric : fabrics) {
      if (fabric.getProductType() == targetType) {
        return fabric;
      }
    }

    System.out.println("No available fabric for type: " + targetType);
    return null;
  }
}
