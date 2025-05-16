package Storage.Fabric;

import java.lang.IllegalArgumentException;

import Util.ProductType;
import Storage.Entity;
import Product.Product;
import Order.CompanyOrder.CompanyOrder;

public class Fabric extends Entity {
  private final ProductType productType;
  private final int timeToProduce;
  private Product product;
  private boolean productReady;
  
  public Fabric(String name, String description, ProductType type, int timeToProduce) {
    super(name, description);
    this.productType = type;
    this.timeToProduce = timeToProduce;
    this.productReady = false;
    this.product = null;
  }

  private void validateInput(ProductType productType, int timeToProduce) {
    if (productType == null || productType == ProductType.UNKNOWN) {
      throw new IllegalArgumentException("Product type in inavlid state.");
    }
    if (timeToProduce <= 0) {
      throw new IllegalArgumentException("Time to product should be greater than 0.");
    }
  }

  public void acceptOrder(CompanyOrder order) {
    this.product = order.getProduct();    
    int quantity = order.getQuantity();
    if (quantity <= 0) return;
    System.out.println("Creating products...");
    for (int i = 0; i < quantity; ++i) {
      createProduct();
      try {
        Thread.sleep(timeToProduce);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    this.productReady = true;
    System.out.println("Products created sucessfully.");
  }

  public Product getProduct(CompanyOrder order) {
    if (productReady) {
      productReady = false;
      return this.product;
    }
    return null;
  }

  private void createProduct() {
    this.product.setQuantity(this.product.getQuantity() + 1);
  }

  public ProductType getProductType() { return productType; }

  public int getTimeToProduce() { return timeToProduce; }

  public boolean isProductReady() { return productReady; }
}
