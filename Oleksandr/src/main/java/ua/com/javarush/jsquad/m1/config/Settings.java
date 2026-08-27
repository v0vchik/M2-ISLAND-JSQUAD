package config;
import java.util.Map;

public class Settings {

    private IslandConfig island;

    private Map<String, AnimalConfig> characteristics;


    private Map<String, Map<String, Integer>> probabilities;


    public IslandConfig getIsland() { return island; }
    public void setIsland(IslandConfig island) { this.island = island; }

    public Map<String, AnimalConfig> getCharacteristics() { return characteristics; }
    public void setCharacteristics(Map<String, AnimalConfig> characteristics) { this.characteristics = characteristics; }

    public Map<String, Map<String, Integer>> getProbabilities() { return probabilities; }
    public void setProbabilities(Map<String, Map<String, Integer>> probabilities) { this.probabilities = probabilities; }
}