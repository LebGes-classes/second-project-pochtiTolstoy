package UI;

import Company.Company;

public class CompanyStatsUI extends BaseUI {
  public CompanyStatsUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Company Statistics------");
    System.out.println("1. Show Company Statistics");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      showCompanyStatistics();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void showCompanyStatistics() {
    printInfo("\n=== Company Statistics ===");
    printInfo(
        String.format("Total Revenue: %.2f", company.getTotalCompanyRevenue()));
    printInfo(String.format("Total Expenses: %.2f",
                            company.getTotalCompanyExpenses()));
    printInfo(
        String.format("Total Profit: %.2f", company.getTotalCompanyProfit()));
    printInfo(String.format("Active Warehouses: %d",
                            company.getActiveWarehouses().size()));
    printInfo(String.format("Active Sell Points: %d",
                            company.getActiveSellPoints().size()));
    printInfo(String.format("Active Employees: %d",
                            company.getActiveEmployees().size()));
  }
}
