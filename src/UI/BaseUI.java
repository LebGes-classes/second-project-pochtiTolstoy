package UI;

import Company.Company;
import java.util.Scanner;

// base ui class
public abstract class BaseUI {
  protected Company company;
  protected Scanner scanner;

  public BaseUI(Company company) {
    this.company = company;
    this.scanner = new Scanner(System.in);
  }

  protected int readIntInput(String prompt) {
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

  protected String readStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  protected double readDoubleInput(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }

  protected void printError(String message) {
    System.out.println("Error: " + message);
  }

  protected void printSuccess(String message) {
    System.out.println("Success: " + message);
  }

  protected void printInfo(String message) { System.out.println(message); }

  protected abstract void showMenu();
  protected abstract boolean processMenu();
}
