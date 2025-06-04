package Util;

import Company.Company;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import UI.BaseUI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EmployeeSelector {
  private EmployeeSelector() {}

  public static <T extends Employee>
      T selectEmployee(Company company, BaseUI ui, String prompt,
                       Class<T> employeeType, String noEmployeesMessage) {
    return selectEmployeeFromSource(company, ui, prompt, employeeType,
                                    noEmployeesMessage,
                                    Company::getAllEmployees);
  }

  public static <T extends Employee>
      T selectInactiveEmployee(Company company, BaseUI ui, String prompt,
                               Class<T> employeeType,
                               String noEmployeesMessage) {
    return selectEmployeeFromSource(company, ui, prompt, employeeType,
                                    noEmployeesMessage,
                                    Company::getInactiveEmployees);
  }

  public static <T extends Employee>
      T selectActiveEmployee(Company company, BaseUI ui, String prompt,
                             Class<T> employeeType, String noEmployeesMessage) {
    return selectEmployeeFromSource(company, ui, prompt, employeeType,
                                    noEmployeesMessage,
                                    Company::getActiveEmployees);
  }

  private static <T extends Employee> T selectEmployeeFromSource(
      Company company, BaseUI ui, String prompt, Class<T> employeeType,
      String noEmployeesMessage,
      Function<Company, List<? extends Employee>> employeeSource) {
    List<T> employees = employeeSource.apply(company)
                            .stream()
                            .filter(employeeType::isInstance)
                            .map(employeeType::cast)
                            .collect(Collectors.toList());

    return selectFromList(ui, employees, prompt, noEmployeesMessage);
  }

  private static <T extends Employee> T selectFromList(BaseUI ui,
                                                       List<T> employees,
                                                       String prompt,
                                                       String errorMessage) {
    if (employees.isEmpty()) {
      ui.printError(errorMessage);
      return null;
    }

    ui.printInfo(prompt);
    for (int i = 0; i < employees.size(); i++) {
      ui.printInfo(String.format("%d. %s", i + 1, employees.get(i)));
    }

    int choice = ui.readIntInput("Enter choice: ") - 1;
    if (choice < 0 || choice >= employees.size()) {
      ui.printError("Invalid selection!");
      return null;
    }

    return employees.get(choice);
  }
}
