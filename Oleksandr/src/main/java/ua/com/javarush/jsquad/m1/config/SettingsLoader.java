package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class SettingsLoader {
    private static Settings settings;

    // Метод для JSON
    public static void loadSettings() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            settings = mapper.readValue(new File("Oleksandr/src/main/java/ua/com/javarush/jsquad/m1/settings.json"), Settings.class);
            System.out.println("Налаштування завантаженні!");
        } catch (IOException e) {
            System.err.println("ПОмилка: " + e.getMessage());
        }
    }

    // Метод для налаштування
    public static Settings getSettings() {
        return settings;
    }
}