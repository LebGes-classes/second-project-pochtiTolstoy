package Util;

import Storage.Cell.Cell;
import Storage.Warehouse.Warehouse;
import UI.BaseUI;
import java.util.ArrayList;

public final class CellSelector {
  private CellSelector() {}

  public static Cell selectCell(Warehouse warehouse, BaseUI ui, String prompt) {
    ArrayList<Cell> cells = warehouse.getCells();
    if (cells.isEmpty()) {
      return null;
    }
    ui.printInfo(prompt);
    for (int i = 0; i < cells.size(); ++i) {
      ui.printInfo(String.format("%d. %s", i + 1, cells.get(i)));
    }
    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= cells.size()) {
      return null;
    }
    return cells.get(choice);
  }
}
