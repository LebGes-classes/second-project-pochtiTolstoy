package Util;

import Company.Company;
import Storage.SellPoint.SellPoint;
import UI.BaseUI;
import java.util.ArrayList;
import java.util.function.Supplier;

public final class SellPointSelector {
  private SellPointSelector() {}

  public static SellPoint selectActiveSellPoint(Company company, BaseUI ui,
                                                String prompt) {
    return selectSellPoint(ui, prompt, "No active sell points available",
                           company::getActiveSellPoints);
  }

  public static SellPoint selectInactiveSellPoint(Company company, BaseUI ui,
                                                  String prompt) {
    return selectSellPoint(ui, prompt, "No inactive sell points available",
                           company::getInactiveSellPoints);
  }

  private static SellPoint
  selectSellPoint(BaseUI ui, String prompt, String emptyMessage,
                  Supplier<ArrayList<SellPoint>> sellPointSource) {
    ArrayList<SellPoint> sellPoints = sellPointSource.get();

    if (sellPoints.isEmpty()) {
      ui.printError(emptyMessage);
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < sellPoints.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, sellPoints.get(i)));
    }

    int choice = ui.readIntInput("Enter sell point number: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      ui.printError("Invalid sell point selection!");
      return null;
    }

    return sellPoints.get(choice);
  }
}
