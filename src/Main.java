import DataLoader.*;
import Company.Company;
import UI.UI;

public class Main {
  public static void main(String[] args) {
    DataLoader data = new DataLoaderImpl();
    Company company = new Company(data);
    UI ui = new UI(company);
    ui.start();
  }
}
