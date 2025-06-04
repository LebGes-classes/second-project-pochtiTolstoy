package Util;

import Company.Company;
import Storage.Warehouse.Warehouse;
import UI.BaseUI;
import java.util.ArrayList;
import java.util.function.Supplier;

public final class WarehouseSelector {
  private WarehouseSelector() {}

  public static Warehouse selectActiveWarehouse(Company company, BaseUI ui,
                                                String prompt) {
    return selectWarehouse(ui, prompt, "No active warehouses available",
                           "Invalid warehouse selection!",
                           company::getActiveWarehouses);
  }

  public static Warehouse selectWarehouse(Company company, BaseUI ui,
                                          String prompt) {
    return selectWarehouse(ui, prompt, "No warehouses available",
                           "Invalid selection!", company::getAllWarehouses);
  }

  private static Warehouse
  selectWarehouse(BaseUI ui, String prompt, String emptyMessage,
                  String invalidMessage,
                  Supplier<ArrayList<Warehouse>> warehouseSource) {
    ArrayList<Warehouse> warehouses = warehouseSource.get();

    if (warehouses.isEmpty()) {
      ui.printError(emptyMessage);
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < warehouses.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, warehouses.get(i)));
    }

    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= warehouses.size()) {
      ui.printError(invalidMessage);
      return null;
    }

    return warehouses.get(choice);
  }
}
