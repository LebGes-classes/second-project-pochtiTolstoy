package Storage.SellPoint;

import Storage.Entity;
import Storage.Cell.Cell;
import Person.Employee.Employee;
import Person.Employee.Manager.Manager;
import Person.Employee.Worker.Worker;
import Product.Product;
import java.util.ArrayList;

public class SellPoint extends Entity {
    private boolean isActive;
    private Manager manager;
    private Worker worker;
    private ArrayList<Cell> cells;
    private double totalRevenue;
    private double totalExpenses;

    public SellPoint(String name, String description, Manager manager, Worker worker) {
        super(name, description);
        this.isActive = true;
        this.manager = manager;
        this.worker = worker;
        this.cells = new ArrayList<>();
        this.totalRevenue = 0.0;
        this.totalExpenses = 0.0;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public ArrayList<Cell> getCells() {
        return new ArrayList<>(cells);
    }

    public int getTotalProducts() {
        return cells.stream()
            .mapToInt(cell -> cell.getProducts().size())
            .sum();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void addRevenue(double amount) {
        this.totalRevenue += amount;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void addExpense(double amount) {
        this.totalExpenses += amount;
    }

    public double getProfit() {
        return totalRevenue - totalExpenses;
    }

    public boolean hasProduct(Product product) {
        for (Cell cell : cells) {
            if (cell.hasProduct(product)) {
                return true;
            }
        }
        return false;
    }

    public boolean sellProduct(Product product, int quantity) {
        for (Cell cell : cells) {
            if (cell.hasProduct(product)) {
                if (cell.getProductQuantity(product) >= quantity) {
                    cell.removeProduct(product, quantity);
                    addRevenue(product.getPrice() * quantity);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean returnProduct(Product product, int quantity) {
        for (Cell cell : cells) {
            if (cell.getAvailableCapacity() >= quantity) {
                cell.addProduct(product, quantity);
                addExpense(product.getPrice() * quantity);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("SellPoint{name='%s', active=%b, manager='%s', worker='%s', revenue=%.2f, expenses=%.2f, profit=%.2f}",
                getName(), isActive, manager.getName(), worker.getName(), totalRevenue, totalExpenses, getProfit());
    }
}
