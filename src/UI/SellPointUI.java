package UI;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.SellPoint.SellPoint;
import Util.*;
import java.util.ArrayList;

public class SellPointUI extends BaseUI {
  public SellPointUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Sell Points------");
    System.out.println("1. Create Sell Point");
    System.out.println("2. Close Sell Point");
    System.out.println("3. List Active Sell Points");
    System.out.println("4. List All Sell Points");
    System.out.println("5. Change Sell Point Manager");
    System.out.println("6. Sell Point Information");
    System.out.println("7. Sell Point Products Information");
    System.out.println("8. Reopen Sell Point");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    clearScreen();
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      createSellPoint();
      break;
    case 2:
      closeSellPoint();
      break;
    case 3:
      listActiveSellPoints();
      break;
    case 4:
      listAllSellPoints();
      break;
    case 5:
      changeSellPointManager();
      break;
    case 6:
      showSellPointInfo();
      break;
    case 7:
      showSellPointProductsInfo();
      break;
    case 8:
      reopenSellPoint();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    waitForEnter();
    return true;
  }

  private void reopenSellPoint() {
    SellPoint sellPoint = SellPointSelector.selectInactiveSellPoint(
        company, this, "Select sell point to reopen: ");
    if (sellPoint == null) {
      printError("No warehouses available.");
      return;
    }

    Manager manager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select manager: ", Manager.class,
        "No managers available!");
    if (manager == null)
      return;

    Worker worker = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select worker: ", Worker.class, "No worker available!");
    if (worker == null)
      return;

    if (company.reopenSellPoint(sellPoint, manager, worker)) {
      printSuccess("Sell point reopened successfully.");
    } else {
      printError("Failed to reopen sell point.");
    }
  }

  private void createSellPoint() {
    String name = readStringInput("Enter sell point name: ");
    String description = readStringInput("Enter sell point description: ");
    Manager manager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select manager: ", Manager.class,
        "No managers available!");
    if (manager == null)
      return;

    Worker worker = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select worker: ", Worker.class,
        "No workers available!");
    if (worker == null)
      return;

    company.createSellPoint(name, description, manager, worker);
    printSuccess("Sell point created successfully.");
  }

  private void closeSellPoint() {
    SellPoint sellPoint = selectActiveSellPoint("Select sell point to close: ");
    if (sellPoint == null) {
      printError("No active sell points available.");
      return;
    }
    company.closeSellPoint(sellPoint);
    printSuccess("Sell point closed successfully.");
  }

  private void listActiveSellPoints() {
    ArrayList<SellPoint> sellPoints = company.getActiveSellPoints();
    if (sellPoints.isEmpty()) {
      printInfo("No active sell points.");
      return;
    }
    for (SellPoint sellPoint : sellPoints) {
      printInfo(sellPoint.toString());
    }
  }

  private void listAllSellPoints() {
    ArrayList<SellPoint> sellPoints = company.getAllSellPoints();
    if (sellPoints.isEmpty()) {
      printInfo("No sell points.");
      return;
    }
    for (SellPoint sellPoint : sellPoints) {
      printInfo(sellPoint.toString());
    }
  }

  private void changeSellPointManager() {
    SellPoint sellPoint =
        selectActiveSellPoint("Select sell point to change manager: ");
    if (sellPoint == null) {
      printError("No active sell points available.");
      return;
    }

    Manager newManager = EmployeeSelector.selectInactiveEmployee(
        company, this, "Select new manager: ", Manager.class,
        "No managers available!");
    if (newManager == null) {
      return;
    }

    if (company.replaceSellPointManager(sellPoint, newManager)) {
      printSuccess("Sell point manager changed successfully.");
    } else {
      printError("Failed to change sell point manager.");
    }
  }

  private void showSellPointInfo() {
    SellPoint sellPoint =
        selectSellPoint("Select sell point to view information: ");
    if (sellPoint == null) {
      printError("No sell points available.");
      return;
    }
    printInfo(company.getSellPointInfo(sellPoint));
  }

  private void showSellPointProductsInfo() {
    SellPoint sellPoint =
        selectSellPoint("Select sell point to view products: ");
    if (sellPoint == null) {
      printError("No sell points available.");
      return;
    }
    printInfo(company.getSellPointProductsInfo(sellPoint));
  }

  private SellPoint selectSellPoint(String prompt) {
    ArrayList<SellPoint> sellPoints = company.getAllSellPoints();
    if (sellPoints.isEmpty()) {
      return null;
    }
    printInfo(prompt);
    for (int i = 0; i < sellPoints.size(); i++) {
      printInfo(String.format("%d. %s", i + 1, sellPoints.get(i)));
    }
    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      return null;
    }
    return sellPoints.get(choice);
  }

  private SellPoint selectActiveSellPoint(String prompt) {
    ArrayList<SellPoint> sellPoints = company.getActiveSellPoints();
    if (sellPoints.isEmpty()) {
      return null;
    }
    printInfo(prompt);
    for (int i = 0; i < sellPoints.size(); i++) {
      printInfo(String.format("%d. %s", i + 1, sellPoints.get(i)));
    }
    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= sellPoints.size()) {
      return null;
    }
    return sellPoints.get(choice);
  }
}
