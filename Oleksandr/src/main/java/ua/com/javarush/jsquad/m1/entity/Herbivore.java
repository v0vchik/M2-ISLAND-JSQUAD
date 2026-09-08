package entity;

import location.GameCell;

public abstract class Herbivore extends Animal {
    public Herbivore() {
        super();
    }

    public abstract void die();

    @Override
    public void eat(GameCell cell) {
        cell.getLock().lock();
        try {
            if (!cell.getPlants().isEmpty()) {
                Plant plant = cell.getPlants().get(0);
                cell.removePlant(plant);

                this.currentSatiety += plant.getWeight();
                if (this.currentSatiety > maxFoodNeeded) {
                    this.currentSatiety = maxFoodNeeded;
                }

            }
        } finally {
            cell.getLock().unlock();
        }
    }
}