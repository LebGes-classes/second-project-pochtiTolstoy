package Util;

public enum ProductType {
  ELECTRONICS("Electronics"),
  CLOTHING("Clothing"),
  BOOKS("Books"),
  FURNITURE("Furniture"),
  GROCERIES("Groceries"),
  UNKNOWN("Unknown");

  private final String displayName;

  ProductType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
