package UI;

import Company.Company;
import Storage.Fabric.Fabric;
import Util.FabricSelector;
import Util.ProductType;
import java.util.ArrayList;

public class FabricUI extends BaseUI {
  public FabricUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Fabrics------");
    System.out.println("1. Create Fabric");
    System.out.println("2. List All Fabrics");
    System.out.println("3. Fabric Information");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      createFabric();
      break;
    case 2:
      listAllFabrics();
      break;
    case 3:
      showFabricInfo();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void createFabric() {
    String name = readStringInput("Enter fabric name: ");
    String description = readStringInput("Enter fabric description: ");
    ProductType type = selectProductType("Enter type of fabric's product: ");

    company.createFabric(name, description, type);
    printSuccess("Fabric created successfully.");
  }

  private void listAllFabrics() {
    ArrayList<Fabric> fabrics = company.getAllFabrics();
    if (fabrics.isEmpty()) {
      printInfo("No fabrics available.");
      return;
    }
    printInfo("Available fabrics:");
    for (Fabric fabric : fabrics) {
      printInfo(fabric.toString());
    }
  }

  private void showFabricInfo() {
    Fabric fabric = FabricSelector.selectFabric(
        company, this, "Select fabric to view information: ");
    if (fabric == null) {
      printError("No fabrics available.");
      return;
    }
    printInfo(fabric.toString());
  }

  private ProductType selectProductType(String prompt) {
    showProductTypeMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      return ProductType.ELECTRONICS;
    case 2:
      return ProductType.CLOTHING;
    case 3:
      return ProductType.BOOKS;
    case 4:
      return ProductType.FURNITURE;
    case 5:
      return ProductType.GROCERIES;
    case 0:
      return ProductType.UNKNOWN;
    default:
      printError("Invalid choice");
      return ProductType.UNKNOWN;
    }
  }

  private void showProductTypeMenu() {
    System.out.println("------Product type------");
    System.out.println("1. " + ProductType.ELECTRONICS.toString());
    System.out.println("2. " + ProductType.CLOTHING.toString());
    System.out.println("3. " + ProductType.BOOKS.toString());
    System.out.println("4. " + ProductType.FURNITURE.toString());
    System.out.println("5. " + ProductType.GROCERIES.toString());
    System.out.println("0. Exit");
  }
}
