package UI;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Util.EmployeeSelector;
import java.util.ArrayList;

public class EmployeeUI extends BaseUI {
  public EmployeeUI(Company company) { super(company); }

  @Override
  public void showMenu() {
    System.out.println("------Employees------");
    System.out.println("1. Create Manager");
    System.out.println("2. Create Worker");
    System.out.println("3. List All Employees");
    System.out.println("4. List Active Employees");
    System.out.println("5. List Inactive Employees");
    System.out.println("6. Fire Employee");
    System.out.println("7. Employee Information");
    System.out.println("0. Exit");
  }

  @Override
  public boolean processMenu() {
    showMenu();
    int choice = readIntInput("Enter your choice: ");
    switch (choice) {
    case 1:
      createManager();
      break;
    case 2:
      createWorker();
      break;
    case 3:
      listAllEmployees();
      break;
    case 4:
      listActiveEmployees();
      break;
    case 5:
      listInactiveEmployees();
      break;
    case 6:
      fireEmployee();
      break;
    case 7:
      showEmployeeInfo();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    return true;
  }

  private void createManager() {
    String name = readStringInput("Enter manager name: ");
    String description = readStringInput("Enter manager description: ");
    String contactInfo = readStringInput("Enter contact info: ");
    int age = readIntInput("Enter age: ");
    String position = readStringInput("Enter position: ");
    double salary = readDoubleInput("Enter manager salary: ");

    company.hireManager(name, description, contactInfo, age, position, salary);
    printSuccess("Manager created successfully.");
  }

  private void createWorker() {
    String name = readStringInput("Enter worker name: ");
    String description = readStringInput("Enter worker description: ");
    String contactInfo = readStringInput("Enter contact info: ");
    int age = readIntInput("Enter age: ");
    String position = readStringInput("Enter position: ");
    double salary = readDoubleInput("Enter worker salary: ");

    company.hireWorker(name, description, contactInfo, age, position, salary);
    printSuccess("Worker created successfully.");
  }

  private void listAllEmployees() {
    ArrayList<Employee> employees = company.getAllEmployees();
    if (employees.isEmpty()) {
      printInfo("No employees.");
      return;
    }
    for (Employee employee : employees) {
      printInfo(employee.toString());
    }
  }

  private void listActiveEmployees() {
    ArrayList<Employee> employees = company.getActiveEmployees();
    if (employees.isEmpty()) {
      printInfo("No active employees.");
      return;
    }
    for (Employee employee : employees) {
      printInfo(employee.toString());
    }
  }

  private void listInactiveEmployees() {
    ArrayList<Employee> employees = company.getInactiveEmployees();
    if (employees.isEmpty()) {
      printInfo("No inactive employees.");
      return;
    }
    for (Employee employee : employees) {
      printInfo(employee.toString());
    }
  }

  private void fireEmployee() {
    Employee employee = EmployeeSelector.selectActiveEmployee(
        company, this, "Select employee to fire: ", Employee.class,
        "No employees available!");
    if (employee == null)
      return;
    company.fireEmployee(employee);
    printSuccess("Employee fired successfully.");
  }

  private void showEmployeeInfo() {
    Employee employee = EmployeeSelector.selectEmployee(
        company, this, "Select employee to show info: ", Employee.class,
        "No employees available!");
    if (employee == null)
      return;
    printInfo(employee.toString());
  }
}
