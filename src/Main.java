import Company.Company;
import DataLoader.*;
import UI.UI;

public class Main {
  public static void main(String[] args) {
    DataLoader data = new DataLoaderImpl();
    Company company = new Company(data);
    (new UI(company)).start();
    company.saveData();
  }
}
