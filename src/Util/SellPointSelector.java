package Util;

import Company.Company;
import Storage.SellPoint.SellPoint;
import UI.BaseUI;
import java.util.ArrayList;

public final class SellPointSelector {
  private SellPointSelector() {}

  public static SellPoint selectActiveSellPoint(Company company, BaseUI ui,
                                                String prompt) {
    ArrayList<SellPoint> sellPoints = company.getActiveSellPoints();
    if (sellPoints.isEmpty()) {
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < sellPoints.size(); ++i) {
      ui.printInfo((i + 1) + ". " + sellPoints.get(i));
    }

    int choice = ui.readIntInput("Enter sell point number: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      ui.printError("Invalid sell point selection!");
      return null;
    }
    return sellPoints.get(choice);
  }
}
