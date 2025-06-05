package UI;

import Company.Company;
import java.io.IOException;
import java.util.Scanner;

// base ui class
public abstract class BaseUI {
  protected Company company;
  protected Scanner scanner;

  public BaseUI(Company company) {
    this.company = company;
    this.scanner = new Scanner(System.in);
  }

  protected abstract void showMenu();
  protected abstract boolean processMenu();

  public void start() {
    boolean running = true;
    while (running) {
      running = processMenu();
    }
  }

  public int readIntInput(String prompt) {
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

  public String readStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  public double readDoubleInput(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }

  public void printError(String message) {
    System.out.println("Error: " + message);
  }

  public void printSuccess(String message) {
    System.out.println("Success: " + message);
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public void waitForEnter() {
    System.out.println("Press enter to continue: ");
    scanner.nextLine();
  }

  public void printInfo(String message) { System.out.println(message); }
}
