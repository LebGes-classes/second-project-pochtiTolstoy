package UI;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.SellPoint.SellPoint;
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
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
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
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void createSellPoint() {
    String name = readStringInput("Enter sell point name: ");
    String description = readStringInput("Enter sell point description: ");
    Manager manager = selectManager("Select manager: ");
    Worker worker = selectWorker("Select worker: ");

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
    Manager newManager = selectInactiveManager("Select new manager: ");
    if (newManager == null) {
      printError("No inactive managers available for replacement.");
      return;
    }
    if (company.changeSellPointManager(sellPoint, newManager)) {
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

  private Manager selectManager(String prompt) {
    ArrayList<Employee> employees = company.getAllEmployees();
    ArrayList<Manager> managers = new ArrayList<>();
    for (Employee employee : employees) {
      if (employee instanceof Manager) {
        managers.add((Manager)employee);
      }
    }
    if (managers.isEmpty()) {
      return null;
    }
    printInfo(prompt);
    for (int i = 0; i < managers.size(); i++) {
      printInfo(String.format("%d. %s", i + 1, managers.get(i)));
    }
    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= managers.size()) {
      return null;
    }
    return managers.get(choice);
  }

  private Manager selectInactiveManager(String prompt) {
    ArrayList<Manager> managers = company.getInactiveManagers();
    if (managers.isEmpty()) {
      return null;
    }
    printInfo(prompt);
    for (int i = 0; i < managers.size(); i++) {
      printInfo(String.format("%d. %s", i + 1, managers.get(i)));
    }
    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= managers.size()) {
      return null;
    }
    return managers.get(choice);
  }

  private Worker selectWorker(String prompt) {
    ArrayList<Employee> employees = company.getAllEmployees();
    ArrayList<Worker> workers = new ArrayList<>();
    for (Employee employee : employees) {
      if (employee instanceof Worker) {
        workers.add((Worker)employee);
      }
    }
    if (workers.isEmpty()) {
      return null;
    }
    printInfo(prompt);
    for (int i = 0; i < workers.size(); i++) {
      printInfo(String.format("%d. %s", i + 1, workers.get(i)));
    }
    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= workers.size()) {
      return null;
    }
    return workers.get(choice);
  }
}
