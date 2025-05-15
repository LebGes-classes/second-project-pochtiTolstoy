package Person;

import Storage.Entity;

public abstract class Person extends Entity {
  private String contactInfo;
  private int age;

  public Person() {
    this.contactInfo = "none";
    this.age = -1;
  }

  public Person(String name, String description, String contactInfo, int age) {
    super(name, description);
    this.contactInfo = contactInfo;
    this.age = age;
  }

  public int getAge() { return age; }
  public String getContactInfo() { return contactInfo; }

  public void setAge(int age) { this.age = age; }
  public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

  @Override
  public String toString() {
    return super.toString() +
      "contact info : " + contactInfo + "\n" +
      "age          : " + age + "\n";
  }
}
