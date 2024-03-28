import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game {

    /** The length of the world, in cells. */
    public static final int WORLD_LENGTH = 30;

    /** The height of the world, in cells. */
    public static final int WORLD_HEIGHT = 30;

    /** The Window being used to display this Game. */
    private final Window window;

    /** The World being used to run this Game. */
    private final World world;

    /** A mouse listener that only listens for clicks. */
    private MouseListener clickListener;

    /**
     * Starts a new Game.<br>
     * First, a new World is initialized.<br>
     * Then, a new Window is initialized that displays the starting colours
     * of the World.
     */
    public Game() {
        this.world = new World(WORLD_LENGTH, WORLD_HEIGHT);
        this.window = new Window(WORLD_LENGTH, WORLD_HEIGHT);

        createClickListener();
        window.addMouseListener(clickListener);

        window.updateColours(world.getColours());
        window.setVisible(true);
    }

    /**
     * Creates a click listener for this Game.
     */
    private void createClickListener() {
        clickListener = new MouseListener() {
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                nextTurn();
            }
        };
    }

    /** Simulates the next turn. */
    public void nextTurn() {
        world.progress();
        window.updateColours(world.getColours());
    }

}
