import java.awt.Color;
import java.util.function.Function;

public class World {

    /** The cells that make up this World. */
    private Cell[][] cells;

    /** The height of this World, in cells. */
    private int height;

    /** The length of this World, in cells. */
    private int length;

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
                seed = RandomGenerator.nextNumber(100); // 0-99
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
        Cell currCell;
        Cell[] currNeighbours;
        Lifeform currLife;
        Cell chosenCell;

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < length; c++) {
                currCell = cells[r][c];

                currLife = currCell.getLifeform();
                if (currLife == null || !currLife.canAct()) {
                    continue;
                }
                System.out.println(currLife); //

                currNeighbours = getAdjacentCells(currCell.getId());
                chosenCell = currLife.chooseMove(currNeighbours);

                if (chosenCell == null) {
                    System.out.println("stays home"); //
                } else {
                    System.out.println("eats " + chosenCell.getLifeform() + " " + chosenCell.getId()); //
                    currLife.live(currCell, chosenCell);
                }

                currLife.setCanAct(false);
            }
        }

        finalizeTurn();
    }

    /**
     * Finalizes the current turn by allowing all existing Lifeforms to act
     * again.
     */
    private void finalizeTurn() {
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

        System.out.println("at [" + row + "][" + col + "]"); //

        Cell[] adjacent = new Cell[8];

        int i = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (row + dr < 0 || row + dr >= height || col + dc < 0 || col + dc >= length) {
                    adjacent[i++] = new Cell(new Void());
                } else if (dr == 0 && dc == 0) {
                    continue;
                } else {
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
     * Clones a Lifeform from one Cell to another. Cloning implies reproduction.
     * @param cell1 The Cell containing the Lifeform that should be cloned.
     * @param cell2 The Cell where the clone should appear.
     */
    private static void cloneLifeform(Cell cell1, Cell cell2) {
        cell2.setLifeform(cell1.getLifeform());
    }

}
