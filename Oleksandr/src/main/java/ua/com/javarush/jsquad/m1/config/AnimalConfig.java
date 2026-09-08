package config;

public class AnimalConfig {
    private double weight;
    private int maxPerCell;
    private int speed;
    private double maxFoodNeeded;

    public double getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }

    public double getMaxFoodNeeded() {
        return maxFoodNeeded;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMaxPerCell(int maxPerCell) {
        this.maxPerCell = maxPerCell;
    }

    public void setMaxFoodNeeded(double maxFoodNeeded) {
        this.maxFoodNeeded = maxFoodNeeded;
    }

}