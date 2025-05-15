package UI;

import Company.Company;

import java.util.ArrayList;
import java.util.Scanner;

import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Warehouse.Warehouse;

public class UI {
  private Company company;
  private Scanner scanner;

  public UI(Company company) {
    this.company = company;
    this.scanner = new Scanner(System.in);
  }

  public void start() {
    boolean running = true;
    while (running) {
      running = processMainMenuChoice();
    }
  }


  private boolean processMainMenuChoice() {
    showMainMenu();
    int choice = readIntInput("Enter your choice: ");
    boolean running = true;
    switch (choice) {
      case 1:
        processWarehouseMenu();
        break;
      case 0:
        running = false;
        break;
      default: 
        System.out.println("Error: invalid choice. Try again.");
        break;
    }
    return running;
  }

  private void processWarehouseMenu() {
    showWarehouseMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
      case 1:
        createWarehouse();
        break;
      case 2:
        closeWarehouse();
        break;
      case 3:
        listActiveWarehouses();
        break;
      case 4:
        listAllWarehouses();
        break;
      case 0:
        return;
      default:
        System.out.println("Invalid choice. Please try again.");
    }
  }

  private void createWarehouse() {
    String name = readStringInput("Enter warehouse name: ");
    String description = readStringInput("Enter warehouse description: ");
    // TODO : select worker and manager
    Manager manager = new Manager();
    Worker worker = new Worker();
    if (true) {
      company.createWarehouse(name, description, manager, worker);
      System.out.println("Warehouse created successfully.");
    } else {
      System.out.println("An error arose when creating a warehouse.");
    }
  }

  private void closeWarehouse() {
    // TODO : select warehouse and close it
  }

  private void listActiveWarehouses() {
    ArrayList<Warehouse> warehouses = company.getActiveWarehouses();
    if (!warehouses.isEmpty()) {
      System.out.println("Active warehouses:");
      for (int i = 0; i < warehouses.size(); ++i) {
        System.out.println((i + 1) + ". " + warehouses.get(i));
      }
    } else {
      System.out.println("No active warehouses found.");
    }
  }

  private void listAllWarehouses() {
    ArrayList<Warehouse> warehouses = company.getAllWarehouses();
    if (!warehouses.isEmpty()) {
      System.out.println("All warehouses:");
      for (int i = 0; i < warehouses.size(); ++i) {
        System.out.println((i + 1) + ". " + warehouses.get(i));
      }
    } else {
      System.out.println("No warehouses found.");
    }
  }

  private int readIntInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextInt()) {
      System.out.println("Please enter a valid number.");
      System.out.println(prompt);
      scanner.next();
    }
    int num = scanner.nextInt();
    scanner.nextLine();
    return num;
  }

  private String readStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  public void showMainMenu() {
    System.out.println("------Trading system------");
    System.out.println("1. Warehouse Management");
    System.out.println("0. Exit");
  }

  private void showWarehouseMenu() {
    System.out.println("------Warehouses------");
    System.out.println("1. Create Warehouse");
    System.out.println("2. Close Warehouse");
    System.out.println("3. List Active Warehouses");
    System.out.println("4. List All Warehouses");
    System.out.println("0. Exit");
  }
}
