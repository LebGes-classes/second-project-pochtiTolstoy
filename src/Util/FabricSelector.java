package Util;

import Company.Company;
import Storage.Fabric.Fabric;
import UI.BaseUI;
import java.util.ArrayList;

public final class FabricSelector {
  private FabricSelector() {}

  public static Fabric selectFabric(Company company, BaseUI ui, String prompt) {
    ArrayList<Fabric> fabrics = company.getAllFabrics();
    if (fabrics.isEmpty()) {
      return null;
    }
    ui.printInfo(prompt);
    for (int i = 0; i < fabrics.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, fabrics.get(i)));
    }
    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= fabrics.size()) {
      return null;
    }
    return fabrics.get(choice);
  }
}
