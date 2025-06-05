package Person.Employee.Worker;

import Person.Employee.Employee;
import Storage.Entity;
import Storage.Warehouse.Warehouse;

public class Worker extends Employee {
  public Worker(String name, String description, String contactInfo, int age,
                String position, double salary) {
    super(name, description, contactInfo, age, position, salary);
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
    return "Worker\nStorage name : " + storageName + "\n" + super.toString();
  }
}
