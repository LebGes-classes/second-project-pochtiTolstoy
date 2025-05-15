package Storage.Warehouse;

import Storage.Entity;
import Storage.Cell.Cell;
import Person.Employee.Worker.Worker;
import Person.Employee.Manager.Manager;
import java.io.Serializable;
import java.util.ArrayList;

public class Warehouse extends Entity implements Serializable {
  private ArrayList<Cell> cells;
  private ArrayList<Worker> workers;
  private Manager manager;
  private boolean isActive;

  public Warehouse() {
    this("none", "none", null, null);
  }

  public Warehouse(String name, String description, Manager manager, Worker worker) {
    super(name, description);
    this.cells = new ArrayList<>();
    this.workers = new ArrayList<>();
    setManager(manager);
    addWorker(worker);
    this.isActive = true;
  }

  public ArrayList<Cell> getCells() {
    return new ArrayList<>(cells);
  }

  public void addCell(Cell cell) {
    cells.add(cell);
  }

  public void removeCell(Cell cell) {
    cells.remove(cell);
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    if (manager == null) return;
    if (this.manager != null) {
      this.manager.removeWarehouse();
    }
    this.manager = manager;
    manager.assignWarehouse(this);
  }

  public ArrayList<Worker> getWorkers() {
    return this.workers;
  }

  public void addWorker(Worker worker) {
    if (worker == null) return;
    if (workers.contains(worker)) return;
    this.workers.add(worker);
    worker.assignWarehouse(this);
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    // TODO : capacity
    String managerName = (manager != null) ? manager.getName() : "none";
    return String.format(
        "Warehouse [ id = %s, name = %s, manager = %s, workers = %s, " +
        "active = %b, cells = %d, capacity = %d/%d ]",
        getId(), getName(), managerName, workers.size(), 
        isActive, cells.size(), -1, -1);
  }
}
