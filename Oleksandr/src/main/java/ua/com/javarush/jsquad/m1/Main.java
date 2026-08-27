package ua.com.javarush.jsquad.m1;


import config.SettingsLoader;
import engine.Simulation;
import location.GameCell;
import location.Island;
import engine.IslandPopulator;


public class Main {
    public static void main(String[] args) {
        // Мій JSON
        SettingsLoader.loadSettings();


        Island island = new Island();


        IslandPopulator populator = new IslandPopulator();
        populator.populate(island);

        // Просто тест
        GameCell cell = island.getGrid()[0][0];

        System.out.println("Тварин в клітині [0][0]:");
        cell.getAnimals().forEach((type, list) -> {
            System.out.println(type + ": " + list.size() + " шт.");
        });

        System.out.println("Plants: " + cell.getPlants().size() + " шт.");
        Simulation engine = new Simulation(island);
        engine.start();

    }
}