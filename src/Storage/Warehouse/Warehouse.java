package Storage.Warehouse;

import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Product.Product;
import Storage.Cell.Cell;
import Storage.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Warehouse extends Entity implements Serializable {
  private ArrayList<Cell> cells;
  private Worker worker;
  private Manager manager;
  private boolean isActive;

  public Warehouse() { this("none", "none", null, null); }

  public Warehouse(String name, String description, Manager manager,
                   Worker worker) {
    super(name, description);
    this.cells = new ArrayList<>();
    addManager(manager);
    addWorker(worker);
    this.isActive = true;
  }

  public ArrayList<Cell> getCells() { return new ArrayList<>(cells); }

  public ArrayList<Product> getProducts() {
    ArrayList<Product> allProducts = new ArrayList<>();
    for (Cell cell : cells) {
      allProducts.addAll(cell.getProducts()
                             .stream()
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList()));
    }
    return allProducts;
  }

  public int getTotalProducts() {
    return cells.stream().mapToInt(cell -> cell.getProducts().size()).sum();
  }

  public void addCell(Cell cell) { cells.add(cell); }

  public void removeCell(Cell cell) { cells.remove(cell); }

  public Manager getManager() { return manager; }

  public void addManager(Manager manager) {
    if (manager == null)
      return;
    manager.removeStorage();
    if (this.manager != null) {
      this.manager.removeStorage();
    }
    this.manager = manager;
    this.manager.assignStorage(this);
  }

  public Worker getWorker() { return this.worker; }

  public void addWorker(Worker worker) {
    if (worker == null)
      return;
    worker.removeStorage();
    if (this.worker != null) {
      this.worker.removeStorage();
    }
    this.worker = worker;
    this.worker.assignStorage(this);
  }

  public boolean isActive() { return isActive; }

  public void setActive(boolean active) { isActive = active; }

  public void deleteWorker() {
    worker.removeStorage();
    this.worker = null;
  }

  public void deleteManager() {
    manager.removeStorage();
    this.manager = null;
  }

  @Override
  public String toString() {
    String managerName = (manager != null) ? manager.getName() : "none";
    String workerName = (getWorker() != null) ? getWorker().getName() : "none";
    return String.format(
        "Warehouse [ id = %s, name = %s, manager = %s, worker = %s, "
            + "active = %b, cells = %d ]",
        getId(), getName(), managerName, workerName, isActive, cells.size());
  }
}
