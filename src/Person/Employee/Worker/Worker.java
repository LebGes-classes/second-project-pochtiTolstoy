package Person.Employee.Worker;

import Person.Employee.Employee;
import Storage.Warehouse.Warehouse;

public class Worker extends Employee {
  private Warehouse warehouse;

  public Worker() {
    this("none", "none", "none", -1, "none", 0.0);
  }

  public Worker(String name, String description, String contactInfo, int age, String position, double salary) {
    super(name, description, contactInfo, age, position, salary);
    this.warehouse = new Warehouse();
  }

  public void assignWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
    setActive(true);
  }
  
  @Override
  public String toString() {
    return "Worker\nWarehouse name : " + this.warehouse.getName() + "\n" + super.toString();
  }
}
