public class Main {

    /** The Game controlled by this Main class. */
    private static Game game;

    /**
     * Entry point of the game.
     * @param args Unused.
     */
    public static void main(String[] args) {
        createGame();
    }

    /** Creates a Game object. */
    private static void createGame() {
        game = new Game();
    }
}
