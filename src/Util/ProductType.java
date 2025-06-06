package Util;

public enum ProductType {
  ELECTRONICS("Electronics", 100),
  CLOTHING("Clothing", 50),
  BOOKS("Books", 10),
  FURNITURE("Furniture", 80),
  GROCERIES("Groceries", 10),
  UNKNOWN("Unknown", 0);

  private final String displayName;
  private final int timeToProduce;

  ProductType(String displayName, int timeToProduce) {
    this.displayName = displayName;
    this.timeToProduce = timeToProduce;
  }

  public String getDisplayName() { return displayName; }

  public int getTimeToProduce() { return timeToProduce; }

  @Override
  public String toString() {
    return displayName;
  }
}
