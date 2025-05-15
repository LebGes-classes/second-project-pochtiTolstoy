package Person.Employee.Manager;

import Person.Employee.Employee;
import Storage.Warehouse.Warehouse;

public class Manager extends Employee {
  private Warehouse warehouse;

  public Manager() {
    this("none", "none", "none", -1, "none", 0.0);
  }

  public Manager(String name, String description, String contactInfo, int age, String position, double salary) {
    super(name, description, contactInfo, age, position, salary);
    this.warehouse = null;
  }

  public void assignWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
    setActive(true);
  }

  public void removeWarehouse() {
    setActive(false);
    warehouse = null;
  }
  
  @Override
  public String toString() {
    String warehouseName = (warehouse != null) ? warehouse.getName() : "none";
    return "Manager\nWarehouse name : " + warehouseName + "\n" + super.toString();
  }
}
