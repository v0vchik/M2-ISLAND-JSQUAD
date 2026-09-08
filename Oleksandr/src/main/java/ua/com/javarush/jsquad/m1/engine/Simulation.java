package engine;

import entity.Animal;
import location.GameCell;
import location.Island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class Simulation {
    private final Island island;
    private final ScheduledExecutorService scheduler;
    private int dayNumber = 0;

    public Simulation(Island island) {
        this.island = island;
        this.scheduler = Executors.newScheduledThreadPool(2);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::nextDay, 0, 45, TimeUnit.SECONDS);
    }

    private void nextDay() {
        dayNumber++;
        System.out.println("\n=== ДЕНЬ СИМУЛЯЦІЇ № " + dayNumber + " ===");

        GameCell[][] grid = island.getGrid();

        // проходимо по кожній клітині
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                GameCell cell = grid[x][y];
                processCell(cell, x, y);
            }
        }


        GameCell sampleCell = grid[0][0];
        sampleCell.getLock().lock();
        try {
            System.out.println("--- Статистика клітки [0][0] на день " + dayNumber + " ---");
            sampleCell.getAnimals().forEach((type, list) -> {
                System.out.println("  " + type + ": " + list.size() + " шт.");
            });
            System.out.println("  Plants: " + sampleCell.getPlants().size() + " шт.");
        } finally {
            sampleCell.getLock().unlock();
        }
    }

    private void processCell(GameCell cell, int x, int y) {
        cell.getLock().lock();
        try {
            cell.getAnimals().forEach((type, animals) -> {
                for (Animal animal : new java.util.ArrayList<>(animals)) {
                    animal.eat(cell);
                    animal.move(cell, island, x, y);
                    animal.reproduce(cell);
                }
            });
        } finally {
            cell.getLock().unlock();
        }
    }

    public void stop() {
        scheduler.shutdown();
        System.out.println("Симуляция зупинена.");
    }
}