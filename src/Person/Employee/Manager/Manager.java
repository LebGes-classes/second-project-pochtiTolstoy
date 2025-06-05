package Person.Employee.Manager;

import Person.Employee.Employee;
import Storage.Entity;
import Storage.Warehouse.Warehouse;

public class Manager extends Employee {
  public Manager(String name, String description, String contactInfo, int age,
                 String position, double salary) {
    super(name, description, contactInfo, age, position, salary);
    setStorage(null);
  }

  public void assignStorage(Entity storage) {
    setStorage(storage);
    setActive(true);
  }

  public void removeStorage() {
    setStorage(null);
    setActive(false);
  }

  @Override
  public String toString() {
    String storageName =
        (getStorage() != null) ? getStorage().getName() : "none";
    return "Manager\nStorage name : " + storageName + "\n" + super.toString();
  }
}
