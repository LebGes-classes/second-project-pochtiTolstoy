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
  private ArrayList<Worker> workers;
  private Manager manager;
  private boolean isActive;

  public Warehouse() { this("none", "none", null, null); }

  public Warehouse(String name, String description, Manager manager,
                   Worker worker) {
    super(name, description);
    this.cells = new ArrayList<>();
    this.workers = new ArrayList<>();
    setManager(manager);
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

  public void setManager(Manager manager) {
    if (manager == null)
      return;
    if (this.manager != null) {
      this.manager.removeWarehouse();
    }
    this.manager = manager;
    manager.assignWarehouse(this);
  }

  public ArrayList<Worker> getWorkers() { return this.workers; }

  public void addWorker(Worker worker) {
    if (worker == null)
      return;
    if (workers.contains(worker))
      return;
    this.workers.add(worker);
    worker.assignWarehouse(this);
  }

  public boolean isActive() { return isActive; }

  public void setActive(boolean active) { isActive = active; }

  public Worker getWorker() {
    return workers.isEmpty() ? null : workers.get(0);
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
