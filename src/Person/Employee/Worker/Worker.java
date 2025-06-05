package Person.Employee.Worker;

import Person.Employee.Employee;
import Storage.Entity;
import Storage.Warehouse.Warehouse;

public class Worker extends Employee {
  private Entity storage;

  public Worker() { this("none", "none", "none", -1, "none", 0.0); }

  public Worker(String name, String description, String contactInfo, int age,
                String position, double salary) {
    super(name, description, contactInfo, age, position, salary);
    this.storage = null;
  }

  public void assignStorage(Entity storage) {
    this.storage = storage;
    setActive(true);
  }

  public void removeStorage() {
    this.storage = null;
    setActive(false);
  }

  @Override
  public String toString() {
    return "Worker\nWarehouse name : " + this.storage.getName() + "\n" +
        super.toString();
  }
}
