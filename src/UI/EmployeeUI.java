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
    clearScreen();
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
      // TODO : refactor
      // fireEmployee();
      break;
    case 7:
      showEmployeeInfo();
      break;
    case 0:
      return false;
    default:
      printError("Invalid choice. Please try again.");
    }
    waitForEnter();
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
    ArrayList<Employee> employees = company.getAllEmployees();
    ArrayList<Employee> inactiveEmployees = new ArrayList<>();
    for (Employee employee : employees) {
      if (!employee.isActive()) {
        inactiveEmployees.add(employee);
      }
    }
    if (inactiveEmployees.isEmpty()) {
      printInfo("No inactive employees.");
      return;
    }
    for (Employee employee : inactiveEmployees) {
      printInfo(employee.toString());
    }
  }

  // TODO : refactor
  // private void fireEmployee() {
  //   Employee employeeToFire = EmployeeSelector.selectActiveEmployee(
  //       company, this, "Select active emplyee: ", Employee.class,
  //       "No active employee to fire");
  //   if (employeeToFire == null) {
  //     return;
  //   }
  //
  //   ArrayList<Employee> inactiveEmployees = new ArrayList<>();
  //   if (employeeToFire instanceof Manager) {
  //     inactiveEmployees.addAll(company.getInactiveManagers());
  //   } else if (employeeToFire instanceof Worker) {
  //     inactiveEmployees.addAll(company.getInactiveWorkers());
  //   }
  //
  //   if (inactiveEmployees.isEmpty()) {
  //     System.out.println(
  //         "No inactive employees available to replace the fired employee.");
  //     return;
  //   }
  //
  //   System.out.println("Select replacement employee:");
  //   for (int i = 0; i < inactiveEmployees.size(); i++) {
  //     System.out.printf("%d. %s%n", i + 1, inactiveEmployees.get(i));
  //   }
  //
  //   int replacementChoice =
  //       readIntInput("Enter replacement employee number: ") - 1;
  //   if (replacementChoice < 0 ||
  //       replacementChoice >= inactiveEmployees.size()) {
  //     System.out.println("Invalid replacement employee selection.");
  //     return;
  //   }
  //
  //   Employee replacementEmployee = inactiveEmployees.get(replacementChoice);
  //
  //   if (company.replaceEmployee(employeeToFire, replacementEmployee)) {
  //     System.out.println("Employee replaced successfully.");
  //   } else {
  //     System.out.println("Failed to replace employee.");
  //   }
  // }

  private void showEmployeeInfo() {
    Employee employee = EmployeeSelector.selectEmployee(
        company, this, "Select employee to show info: ", Employee.class,
        "No employees available!");
    if (employee == null)
      return;
    printInfo(employee.toString());
  }
}
