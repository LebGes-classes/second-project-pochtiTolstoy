package Util;

import Company.Company;
import Person.Customer.Customer;
import Storage.Warehouse.Warehouse;
import UI.BaseUI;
import java.util.ArrayList;

public final class CustomerSelector {
  private CustomerSelector() {}

  public static Customer selectCustomer(Company company, BaseUI ui,
                                        String prompt) {
    ArrayList<Customer> customers = company.getAllCustomers();
    if (customers.isEmpty()) {
      return null;
    }

    System.out.println(prompt);
    for (int i = 0; i < customers.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, customers.get(i));
    }

    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= customers.size()) {
      return null;
    }

    return customers.get(choice);
  }
}
