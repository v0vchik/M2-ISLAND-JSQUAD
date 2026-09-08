package entity;

import location.GameCell;

public abstract class Carnivore extends Animal {
    public Carnivore() {
        super();
    }

    @Override
    public void eat(GameCell cell) {
        cell.getLock().lock();
        try {
            var animalsMap = cell.getAnimals();

            for (var entry : animalsMap.entrySet()) {
                String preyType = entry.getKey();
                var preyList = entry.getValue();

                if (!preyList.isEmpty() && !preyType.equals(this.getClass().getSimpleName())) {
                    Animal prey = preyList.get(0);
                    preyList.remove(prey);

                    this.currentSatiety += prey.weight;
                    break;
                }
            }
        } finally {
            cell.getLock().unlock();
        }
    }

    public abstract void die();
}