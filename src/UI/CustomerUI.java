package UI;

import Company.Company;
import Person.Customer.Customer;
import java.util.ArrayList;

public class CustomerUI extends BaseUI {
  public CustomerUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Customers------");
    System.out.println("1. Add Customer");
    System.out.println("2. List Active Customers");
    System.out.println("3. List All Customers");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      addCustomer();
      break;
    case 2:
      listActiveCustomers();
      break;
    case 3:
      listAllCustomers();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void addCustomer() {
    String name = readStringInput("Enter customer name: ");
    String description = readStringInput("Enter customer description: ");
    String contactInfo = readStringInput("Enter customer contact info: ");
    int age = readIntInput("Enter customer age: ");

    company.addCustomer(name, description, contactInfo, age);
    printSuccess("Customer added successfully.");
  }

  private void listActiveCustomers() {
    ArrayList<Customer> customers = company.getActiveCustomers();
    if (customers.isEmpty()) {
      printInfo("No active customers.");
      return;
    }
    printInfo("Active customers:");
    for (Customer customer : customers) {
      printInfo(customer.toString());
    }
  }

  private void listAllCustomers() {
    ArrayList<Customer> customers = company.getAllCustomers();
    if (customers.isEmpty()) {
      printInfo("No customers.");
      return;
    }
    printInfo("All customers:");
    for (Customer customer : customers) {
      printInfo(customer.toString());
    }
  }
}
