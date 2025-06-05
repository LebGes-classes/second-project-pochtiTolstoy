package Person.Employee.Manager;

import Person.Employee.Employee;
import Storage.Entity;
import Storage.Warehouse.Warehouse;

public class Manager extends Employee {
  private Entity storage;

  public Manager(String name, String description, String contactInfo, int age,
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
    String storageName = (storage != null) ? storage.getName() : "none";
    return "Manager\nStorage name : " + storageName + "\n" + super.toString();
  }
}
