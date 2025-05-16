package Util;

public enum ProductType {
  ELECTRONICS("Electronics", 1000),
  CLOTHING("Clothing", 500),
  BOOKS("Books", 100),
  FURNITURE("Furniture", 800),
  GROCERIES("Groceries", 100),
  UNKNOWN("Unknown", 0);

  private final String displayName;
  private final int timeToProduce;

  ProductType(String displayName, int timeToProduce) {
    this.displayName = displayName;
    this.timeToProduce = timeToProduce;
  }

  public String getDisplayName() {
    return displayName;
  }

  public int getTimeToProduce() {
    return timeToProduce;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
