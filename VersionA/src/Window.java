import javax.swing.*;
import java.awt.*;

/**
 * This class defines the window to serve as the graphics for the Game of Life.
 * Two important methods are the constructor, which initializes a window for 
 * playing the game, and updateColours(Color[][]), which should be used to 
 * update the display after a turn has passed.
 */
public class Window extends JFrame {

    /** The length and height of one cell, in pixels. */
    public static final int CELL_SIZE = 25;

    /** The length of the grid, in cells. */
    private final int gridLength;

    /** The height of the grid, in cells. */
    private final int gridHeight;

    /** The JPanel object defining the more detailed functions of this Window. */
    private final GridPanel gridPanel;

    /**
     * Creates a Window for the Game of Life to be observed.
     * @param gridLength The length of the Game's grid, in cells.
     * @param gridHeight The height of the Game's grid, in cells.
     */
    public Window(final int gridLength, final int gridHeight) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("The Game of Life");

        this.gridLength = gridLength;
        this.gridHeight = gridHeight;
        this.gridPanel = new GridPanel();
        add(gridPanel);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates the display by passing an updated array of colours, which
     * should accurately represent the World's Lifeforms and their positions
     * in this Window.
     * @param colours An updated array of colours.
     */
    public void updateColours(Color[][] colours) {
        gridPanel.setCellColours(colours);
        gridPanel.repaint();
    }

    /**
     * This inner class defines the more detailed functions of Window, such as
     * how to update the colours of each Cell and how to draw the game's grid.
     */
    private class GridPanel extends JPanel {

        /** The array containing the colours of all Cells in this Window. */
        private Color[][] cellColours;

        /**
         * Simple constructor that initializes all colours in cellColours
         * to white.
         */
        private GridPanel() {
            cellColours = new Color[gridHeight][gridLength];
            for (int row = 0; row < gridHeight; row++) {
                for (int col = 0; col < gridLength; col++) {
                    cellColours[row][col] = Color.WHITE;
                }
            }
        }

        /**
         * Sets the array representing colours of all Cells in this Window.
         * @param colours An array to represent the colours of all Cells in
         *                this Window.
         */
        private void setCellColours(Color[][] colours) {
            if (colours.length == gridHeight && colours[0].length == gridLength) {
                cellColours = colours;
            } else {
                throw new IllegalArgumentException("Invalid dimensions for colors array.");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Calculate the total size of the grid
            int totalWidth = CELL_SIZE * gridLength;
            int totalHeight = CELL_SIZE * gridHeight;

            // Draw the grid lines to create cells
            g.setColor(Color.BLACK);
            for (int i = 0; i <= gridLength; i++) {
                int x = i * CELL_SIZE;
                g.drawLine(x, 0, x, totalHeight);
            }
            for (int i = 0; i <= gridHeight; i++) {
                int y = i * CELL_SIZE;
                g.drawLine(0, y, totalWidth, y);
            }

            // Fill each cell with a colour
            for (int row = 0; row < gridHeight; row++) {
                for (int col = 0; col < gridLength; col++) {
                    Color colour = cellColours[row][col];
                    g.setColor(colour);
                    g.fillRect(col * CELL_SIZE + 1, row * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CELL_SIZE * gridLength, CELL_SIZE * gridHeight);
        }
    }
}