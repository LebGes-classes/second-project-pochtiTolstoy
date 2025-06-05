package UI;

import Company.Company;
import java.util.Scanner;

public class UI extends BaseUI {
  private WarehouseUI warehouseUI;
  private ProductUI productUI;
  private EmployeeUI employeeUI;
  private FabricUI fabricUI;
  private SellPointUI sellPointUI;
  private CustomerUI customerUI;
  private CompanyStatsUI companyStatsUI;

  public UI(Company company) {
    super(company);
    this.warehouseUI = new WarehouseUI(company);
    this.productUI = new ProductUI(company);
    this.employeeUI = new EmployeeUI(company);
    this.fabricUI = new FabricUI(company);
    this.sellPointUI = new SellPointUI(company);
    this.customerUI = new CustomerUI(company);
    this.companyStatsUI = new CompanyStatsUI(company);
  }

  @Override
  protected boolean processMenu() {
    clearScreen();
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      warehouseUI.start();
      break;
    case 2:
      productUI.start();
      break;
    case 3:
      employeeUI.start();
      break;
    case 4:
      fabricUI.start();
      break;
    case 5:
      sellPointUI.start();
      break;
    case 6:
      customerUI.start();
      break;
    case 7:
      companyStatsUI.start();
      break;
    case 0:
      return false;
    default:
      System.out.println("Error: invalid choice. Try again.");
      break;
    }
    return true;
  }

  @Override
  protected void showMenu() {
    System.out.println("\n=== Main Menu ===");
    System.out.println("1. Warehouse Management");
    System.out.println("2. Product Management");
    System.out.println("3. Employee Management");
    System.out.println("4. Fabric Management");
    System.out.println("5. Sell Point Management");
    System.out.println("6. Customer Management");
    System.out.println("7. Company Statistics");
    System.out.println("0. Exit");
  }
}
