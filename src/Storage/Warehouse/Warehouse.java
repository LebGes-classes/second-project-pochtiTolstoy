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

  public Warehouse(String name, String description, Manager manager, Worker worker) {
    super(name, description);
    this.cells = new ArrayList<>();
    this.manager = manager;
    this.workers = new ArrayList<>();
    this.workers.add(worker);
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
      this.manager = manager;
  }

  public ArrayList<Worker> getWorkers() {
    return this.workers;
  }

  public void addWorker(Worker worker) {
    this.workers.add(worker);
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
    return String.format(
        "Warehouse [ id = %s, name = %s, manager = %s, workers = %s, " +
        "active = %b, cells = %d, capacity = %d/%d ]",
        getId(), getName(), manager.getName(), workers.size(), 
        isActive, cells.size(), -1, -1);
  }
}
