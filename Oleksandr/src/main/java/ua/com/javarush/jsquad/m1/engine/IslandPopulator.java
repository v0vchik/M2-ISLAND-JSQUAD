package engine;


import entity.*;
import config.AnimalConfig;
import config.SettingsLoader;
import location.GameCell;
import location.Island;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class IslandPopulator {

    public void populate(Island island) {
        GameCell[][] grid = island.getGrid();

        Map<String, AnimalConfig> characteristics = SettingsLoader.getSettings().getCharacteristics();

        // Цикл для наповнення острову
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                GameCell cell = grid[x][y];


                for (Map.Entry<String, AnimalConfig> entry : characteristics.entrySet()) {
                    String type = entry.getKey();


                    int maxPerCell = entry.getValue().getMaxPerCell();
                    int randomCount = ThreadLocalRandom.current().nextInt((maxPerCell / 2) + 1);

                    for (int i = 0; i < randomCount; i++) {
                        if (type.equals("Plant")) {
                            cell.addPlant(new Plant());
                        } else {
                            Animal animal = createAnimalByName(type);
                            if (animal != null) {
                                cell.addAnimalSafe(animal);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Острів заселений!");
    }

    //що було те було
   private Animal createAnimalByName(String className) {
       switch (className) {
           case "Wolf": return new Wolf();
           case "Boa": return new Boa();
           case "Fox": return new Fox();
           case "Bear": return new Bear();
           case "Eagle": return new Eagle();
           case "Horse": return new Horse();
           case "Deer": return new Deer();
           case "Rabbit": return new Rabbit();
           case "Mouse": return new Mouse();
           case "Goat": return new Goat();
           case "Sheep": return new Sheep();
           case "Boar": return new Boar();
           case "Buffalo": return new Buffalo();
           case "Duck": return new Duck();
           case "Caterpillar": return new Caterpillar();

           default:
               System.err.println("Невідомий тип тварини: " + className);
               return null;
       }
   }
}