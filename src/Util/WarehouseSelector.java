package Util;

import Company.Company;
import Storage.Warehouse.Warehouse;
import UI.BaseUI;
import java.util.ArrayList;

public final class WarehouseSelector {
  private WarehouseSelector() {}

  public static Warehouse selectActiveWarehouse(Company company, BaseUI ui,
                                                String prompt) {
    ArrayList<Warehouse> warehouses = company.getActiveWarehouses();
    if (warehouses.isEmpty()) {
      ui.printError("No warehouses available.");
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < warehouses.size(); i++) {
      ui.printInfo((i + 1) + ". " + warehouses.get(i));
    }

    int choice = ui.readIntInput("Enter warehouse number: ") - 1;
    if (choice < 0 || choice >= warehouses.size()) {
      ui.printError("Invalid warehouse selection.");
      return null;
    }
    return warehouses.get(choice);
  }

  public static Warehouse selectWarehouse(Company company, BaseUI ui,
                                          String prompt) {
    ArrayList<Warehouse> warehouses = company.getAllWarehouses();
    if (warehouses.isEmpty()) {
      return null;
    }
    ui.printInfo(prompt);
    for (int i = 0; i < warehouses.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, warehouses.get(i)));
    }
    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= warehouses.size()) {
      return null;
    }
    return warehouses.get(choice);
  }
}
