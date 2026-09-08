package location;

import config.SettingsLoader;

public class Island {
    private GameCell[][] grid;
    private int width;
    private int height;

    public Island() {

        this.width = SettingsLoader.getSettings().getIsland().getWidth();
        this.height = SettingsLoader.getSettings().getIsland().getHeight();

        grid = new GameCell[width][height];

        // проходимся по масиву і кладемо туди пусті клітини
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GameCell(i, j);
            }
        }
    }

    public GameCell[][] getGrid() {
        return grid;
    }
}


