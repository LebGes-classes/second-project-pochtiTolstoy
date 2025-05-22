package UI;

import Company.Company;
import Order.CompanyOrder.CompanyOrder;
import Product.Product;
import Storage.Cell.Cell;
import Storage.Fabric.Fabric;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import Util.*;
import java.util.ArrayList;

public class ProductUI extends BaseUI {
  public ProductUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Products------");
    System.out.println("1. Create Product Specification");
    System.out.println("2. List Available Products");
    System.out.println("3. Purchase Product");
    System.out.println("4. Move product to sell point");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      createProductSpecification();
      break;
    case 2:
      listAvailableProductsToPurchase();
      break;
    case 3:
      purchaseProduct();
      break;
    case 4:
      moveProductToSellPoint();
      break;
    case 5:
      // TODO : move product to Warehouse
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void createProductSpecification() {
    String name = readStringInput("Enter product name: ");
    String description = readStringInput("Enter product description: ");
    double price = readDoubleInput("Enter product price: ");
    ProductType type = selectProductType("Select product type:");

    company.createProductSpecification(name, description, price, 0, type);
    printSuccess("Product created successfully.");
  }

  private void listAvailableProductsToPurchase() {
    printInfo(company.getAvailableProductsFormatted());
  }

  private void purchaseProduct() {
    CompanyOrder order = CompanyOrder.createCompanyOrder(company, this);
    if (order == null) {
      return;
    }

    Fabric fabric = FabricSelector.getAvailableFabric(company, order);
    if (fabric == null) {
      return;
    }

    fabric.acceptOrder(order);
    while (!fabric.isProductReady())
      ;
    Product product = fabric.getProduct(order);

    printInfo("Select avtive warehouse for stoarge: ");
    Warehouse warehouse = WarehouseSelector.selectActiveWarehouse(
        company, this, "Select active warehouse: ");
    if (warehouse == null) {
      System.out.println("No warehouses available!");
      return;
    }

    ArrayList<Cell> cells = warehouse.getCells();
    int quantity = product.getQuantity();
    int availableCells = 0;
    System.out.println("Choose cell:");
    for (int i = 0; i < cells.size(); ++i) {
      if (cells.get(i).getAvailableCapacity() >= quantity) {
        System.out.println(++availableCells + ". " + cells.get(i).toString());
      }
    }

    int choice = readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= availableCells) {
      System.out.println("Invalid cell selection");
      return;
    }

    cells.get(choice).addProduct(product);
    System.out.println("Product purchase is successful.");
  }

  private void moveProductToSellPoint() {
    Warehouse warehouse = WarehouseSelector.selectActiveWarehouse(
        company, this, "Select source warehouse: ");
    if (warehouse == null) {
      System.out.println("No available warehouse.");
      return;
    }

    SellPoint sellPoint = SellPointSelector.selectActiveSellPoint(
        company, this, "Select target sell point: ");
    if (sellPoint == null) {
      System.out.println("No available sell point.");
      return;
    }

    Product product = ProductSelector.selectProductFromWarehouse(
        warehouse, this, "Select product to move: ");
    if (product == null) {
      System.out.println("No available product.");
      return;
    }

    int quantity = readIntInput("Enter quantity to move: ");
    if (company.moveProductToSellPoint(product, quantity, warehouse,
                                       sellPoint)) {
      System.out.println("Product moved successfully.");
    } else {
      System.out.println("Failed to move product.");
    }
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
