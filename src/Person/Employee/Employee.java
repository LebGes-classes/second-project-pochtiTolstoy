package Person.Employee;

import Person.Person;
import Storage.Entity;

public abstract class Employee extends Person {
  private String position;
  private double salary;
  private boolean isActive;
  private Entity storage;

  public Employee(String name, String description, String contactInfo, int age,
                  String position, double salary) {
    super(name, description, contactInfo, age);
    this.position = position;
    this.salary = salary;
    this.isActive = false;
    this.storage = null;
  }

  public void setStorage(Entity storage) { this.storage = storage; }

  public Entity getStorage() { return this.storage; }

  public String getPosition() { return position; }

  public void setPosition(String position) { this.position = position; }

  public double getSalary() { return salary; }

  public void setSalary(double salary) { this.salary = salary; }

  public boolean isActive() { return isActive; }

  public void setActive(boolean active) { isActive = active; }

  @Override
  public String toString() {
    return super.toString() + "position       : " + position + "\n"
        + "salary         : " + salary + "\n"
        + "isActive       : " + isActive + "\n";
  }
}
