import java.awt.*;

public class World {

    /** The cells that make up this World. */
    private final Cell[][] cells;

    /** The height of this World, in cells. */
    private final int height;

    /** The length of this World, in cells. */
    private final int length;

    /**
     * Instantiates a World with random lifeforms.
     * @param height The height of this World, in cells.
     * @param length The length of this World, in cells.
     */
    public World(final int height, final int length) {
        this.height = height;
        this.length = length;

        cells = new Cell[height][length];

        int seed;
        int id;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < length; c++) {
                seed = 1 + RandomGenerator.nextNumber(100); // 1-100
                id = r * length + c;
                cells[r][c] = new Cell(seed, id);
            }
        }
    }

    /**
     * Returns a 2D array of colours, which represents all living lifeforms.
     * The array is in order of Cell IDs.
     * @return A 2D array of colours, representing all living lifeforms.
     */
    public Color[][] getColours() {
        Color[][] colours = new Color[height][length];
        Lifeform lifeform;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < length; c++) {
                lifeform = cells[r][c].getLifeform();
                if (lifeform != null) {
                    colours[r][c] = cells[r][c].getLifeform().getColour();
                } else {
                    colours[r][c] = Color.WHITE;
                }
            }
        }
        return colours;
    }

    /**
     * Iterates through all cells in this World and calls the
     * <code>live()</code> method on any existing lifeforms, causing them
     * to take their usual actions.
     */
    public void progress() {
        refreshActions();

        Cell currCell;
        Lifeform currLife;
        Cell[] currNeighbours;

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < length; c++) {
                currCell = cells[r][c];
                currLife = currCell.getLifeform();
                if (currLife == null || !currLife.canAct()) {
                    continue;
                }

                if (currLife.getHungerLimit() != -1) {
                    currLife.increaseHunger();
                }

                currNeighbours = getAdjacentCells(currCell.getId());
                currLife.live(currCell, currNeighbours);

                currLife.setCanAct(false);
            }
        }
    }

    /**
     * Allow all Lifeforms in this World to act again.
     */
    private void refreshActions() {
        Lifeform currLife;

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < length; c++) {
                currLife = cells[r][c].getLifeform();

                if (currLife != null) {
                    currLife.setCanAct(true);
                }
            }
        }
    }


    /**
     * Given a cell ID, returns all 8 cells around that cell.
     * @param cellId The ID of the target cell.
     * @return An array of the 8 cells around the given cell.
     */
    private Cell[] getAdjacentCells(int cellId) {
        int row = cellId / length;
        int col = cellId % length;

        Cell[] adjacent = new Cell[8];

        int i = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (row + dr < 0 || row + dr >= height || col + dc < 0 || col + dc >= length) {
                    adjacent[i++] = new Cell(new Void());
                } else if (!(dr == 0 && dc == 0)) {
                    adjacent[i++] = cells[row + dr][col + dc];
                }
            }
        }

        return adjacent;
    }

    /**
     * Moves a Lifeform from one Cell to another. Moving into a Cell implies
     * eating any Lifeforms that were previously there. However, Lifeforms
     * should create their own methods to manage hunger when moving.
     * @param cell1 The Cell containing the Lifeform that should be moved.
     * @param cell2 The Cell that the Lifeform will move to.
     */
    public static void moveLifeform(Cell cell1, Cell cell2) {
        cell2.setLifeform(cell1.getLifeform());
        cell1.setLifeform(null);
    }

    /**
     * Clones a Lifeform from one Cell to another. Since cloning implies
     * reproduction, the clone will have a new reference and fresh attributes.
     * @param cell1 The Cell containing the Lifeform that should be cloned.
     * @param cell2 The Cell where the clone should appear.
     */
    public static void cloneLifeform(Cell cell1, Cell cell2) {
        cell2.setLifeform(cell1.getLifeform().getNewChild());
    }

}
