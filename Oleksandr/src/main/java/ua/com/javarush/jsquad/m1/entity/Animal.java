package entity;

import location.GameCell;
import location.Island;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    protected double weight;
    private int maxPerCell;
    private int speed;
    protected double maxFoodNeeded;
    protected double currentSatiety;

    // Конструктор
    public Animal() {
    }


        public abstract void eat(GameCell cell);

    public void move(GameCell currentCell, Island island, int currentX, int currentY) {
        GameCell[][] grid = island.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;


        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int directionIndex = ThreadLocalRandom.current().nextInt(dx.length);

        int newX = currentX + dx[directionIndex];
        int newY = currentY + dy[directionIndex];

        if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
            GameCell targetCell = grid[newX][newY];

            GameCell firstLock = (currentX < newX || (currentX == newX && currentY < newY)) ? currentCell : targetCell;
            GameCell secondLock = (firstLock == currentCell) ? targetCell : currentCell;

            firstLock.getLock().lock();
            secondLock.getLock().lock();
            try {
                String animalType = this.getClass().getSimpleName();
                long currentCountInTarget = targetCell.getAnimals().getOrDefault(animalType, new java.util.ArrayList<>()).size();

                if (currentCountInTarget < this.maxPerCell) {
                    currentCell.getAnimals().get(animalType).remove(this);

                    targetCell.getAnimals().computeIfAbsent(animalType, k -> new java.util.concurrent.CopyOnWriteArrayList<>()).add(this);

                    System.out.println(animalType + " перемістився з [" + currentX + "][" + currentY + "] в [" + newX + "][" + newY + "]");
                }
            } finally {
                secondLock.getLock().unlock();
                firstLock.getLock().unlock();
            }
        }
    }

    public void reproduce(GameCell cell) {
        cell.getLock().lock();
        try {
            String animalType = this.getClass().getSimpleName();
            var sameSpeciesList = cell.getAnimals().get(animalType);

            if (sameSpeciesList != null && sameSpeciesList.size() >= 2) {
                // Перевірка на максимальну кількість в клітині
                if (sameSpeciesList.size() < this.maxPerCell) {
                    try {
                        Animal baby = this.getClass().getDeclaredConstructor().newInstance();

                        sameSpeciesList.add(baby);

                        System.out.println("Родився новий " + animalType + " в клітинці!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            cell.getLock().unlock();
        }
    }

        public double getWeight() {
            return weight;
        }

        public int getMaxPerCell() {
            return maxPerCell;
        }

        public int getSpeed() {
            return  speed;
        }

        public double getMaxFoodNeeded() {
            return maxFoodNeeded;
        }

        public double getCurrentSatiety() {
            return currentSatiety;
        }

        public void setWeight(double weight){
            this.weight = weight;
        }

        public void setMaxPerCell(int maxPerCell){
            this.maxPerCell = maxPerCell;
        }

        public void setSpeed(int speed){
            this.speed = speed;
        }

        public void setMaxFoodNeeded(double maxFoodNeeded){
            this.maxFoodNeeded = maxFoodNeeded;
        }

        public void setCurrentSatiety(double currentSatiety){
            this.currentSatiety = currentSatiety;
        }


    }
