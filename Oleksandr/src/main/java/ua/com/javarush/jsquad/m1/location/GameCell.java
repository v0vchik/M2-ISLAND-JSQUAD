package location;

import entity.Animal;
import entity.Plant;
import config.SettingsLoader;
import config.AnimalConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class GameCell {

    private int x;
    private int y;
    private final List<Plant> plants;

    private final Lock lock = new ReentrantLock();

    private Map<String, List<Animal>> animals;
    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.animals = new ConcurrentHashMap<>();
        this.plants = new CopyOnWriteArrayList<>();
    }

    public boolean addAnimalSafe(Animal animal) {
        String type = animal.getClass().getSimpleName();

        AnimalConfig config = SettingsLoader.getSettings().getCharacteristics().get(type);

        if (config == null) return false;

        int maxPerCell = config.getMaxPerCell();

        lock.lock();

        try {
            animals.putIfAbsent(type, new CopyOnWriteArrayList<>());
            List<Animal> currentAnimalsOfType = animals.get(type);


            if (currentAnimalsOfType.size() < maxPerCell) {
                currentAnimalsOfType.add(animal);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
    public void removeAnimal(Animal animal) {
        String type = animal.getClass().getSimpleName();
        List<Animal> currentAnimalsOfType = animals.get(type);
        if (currentAnimalsOfType != null) {
            currentAnimalsOfType.remove(animal);
        }
    }

    public Map<String, List<Animal>> getAnimals() {
        return animals;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        lock.lock();
        try {
            plants.add(plant);
        } finally {
            lock.unlock();
        }
    }

    public void removePlant(Plant plant) {
        lock.lock();
        try {
            plants.remove(plant);
        } finally {
            lock.unlock();
        }
    }

    public ReentrantLock getLock() {
        return (ReentrantLock) lock;
    }
}

