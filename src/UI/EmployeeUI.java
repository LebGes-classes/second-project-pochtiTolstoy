package UI;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Storage.Entity;
import Storage.SellPoint.SellPoint;
import Storage.Warehouse.Warehouse;
import Util.EmployeeSelector;
import java.util.ArrayList;
import java.util.Collections;

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

  private void fireEmployee() {
    Employee employeeToFire = selectActiveEmployee();
    if (employeeToFire == null)
      return;

    ArrayList<Employee> inactiveEmployees =
        getInactiveEmployees(employeeToFire);
    if (inactiveEmployees.isEmpty()) {
      System.out.println(
          "No inactive employees available to replace the fired employee.");
      return;
    }

    Employee replacementEmployee = selectReplacementEmployee(inactiveEmployees);
    if (replacementEmployee == null)
      return;

    performReplacement(employeeToFire, replacementEmployee);
  }

  private Employee selectActiveEmployee() {
    return EmployeeSelector.selectActiveEmployee(
        company, this, "Select active employee: ", Employee.class,
        "No active employee to fire");
  }

  private ArrayList<Employee> getInactiveEmployees(Employee employee) {
    if (employee instanceof Manager) {
      return new ArrayList<>(company.getInactiveManagers());
    } else if (employee instanceof Worker) {
      return new ArrayList<>(company.getInactiveWorkers());
    }
    return null;
  }

  private Employee selectReplacementEmployee(ArrayList<Employee> candidates) {
    System.out.println("Select replacement employee:");
    for (int i = 0; i < candidates.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, candidates.get(i));
    }

    int choice = readIntInput("Enter replacement employee number: ") - 1;
    if (choice < 0 || choice >= candidates.size()) {
      System.out.println("Invalid selection.");
      return null;
    }
    return candidates.get(choice);
  }

  private void performReplacement(Employee toFire, Employee replacement) {
    Entity storage = toFire.getStorage();
    boolean success = false;

    if (storage instanceof Warehouse warehouse) {
      if (replacement instanceof Manager manager) {
        success = company.replaceWarehouseManager(warehouse, manager);
      } else if (replacement instanceof Worker worker) {
        success = company.replaceWarehouseWorker(warehouse, worker);
      }
    } else if (storage instanceof SellPoint sellPoint) {
      if (replacement instanceof Worker worker) {
        success = company.replaceSellPointWorker(sellPoint, worker);
      } else if (replacement instanceof Manager manager) {
        success = company.replaceSellPointManager(sellPoint, manager);
      }
    }

    System.out.println(success ? "Employee replaced successfully."
                               : "Failed to replace employee.");
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

  private void showEmployeeInfo() {
    Employee employee = EmployeeSelector.selectEmployee(
        company, this, "Select employee to show info: ", Employee.class,
        "No employees available!");
    if (employee == null)
      return;
    printInfo(employee.toString());
  }
}
