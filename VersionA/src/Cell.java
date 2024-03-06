public class Cell {

    /** The standard ID for inaccessible cells. */
    public static final int INACCESSIBLE_ID = -1;

    /** The spawn rate of herbivores (%). */
    public static final int SPAWN_RATE_HERBIVORE = 15;

    /** The spawn rate of plants (%). */
    public static final int SPAWN_RATE_PLANT = 20;
    
    /** The lifeform occupying this Cell. */
    private Lifeform life;

    /** This Cell's ID. */
    private final int id;

    /** 
     * Constructs a cell, assigning an ID and deciding which Lifeform will 
     * occupy it, if any.
     * @param seed Determines which Lifeform will occupy this Cell upon creation.
     * @param id The ID of this Cell.
     */
    public Cell(int seed, int id) {
        this.id = id;
        this.life = chooseLifeform(seed); // seed is 0 to 99
    }

    /**
     * Constructs a cell by passing only the lifeform that it should contain.
     * Instantiating a cell in this way makes it inaccessible in the game
     * board, as the cell's ID is set to INACCESSIBLE_ID.
     * @param life The lifeform that this Cell should contain.
     */
    public Cell(Lifeform life) {
        this.id = INACCESSIBLE_ID;
        this.life = life;
    }

    /**
     * Returns the lifeform occupying this Cell.
     * @return The lifeform occupying this Cell.
     */
    public Lifeform getLifeform() {
        return life;
    }

    /**
     * Changes the lifeform occupying this Cell.
     * @param life The lifeform that should occupy this Cell.
     */
    public void setLifeform(Lifeform life) {
        this.life = life;
    }
    
    /** 
     * Returns the ID of this Cell.
     * @return the ID of this Cell.
     */
    public int getId() {
        return id;
    }

    /**
     * Helper method to choose a lifeform based on a random seed value.
     * @return A lifeform.
     */
    private Lifeform chooseLifeform(int seed) {
        Lifeform lifeform;
        if (seed >= (100 - SPAWN_RATE_HERBIVORE)) {
            lifeform = new Herbivore();
        } else if (seed >= (100 - SPAWN_RATE_HERBIVORE - SPAWN_RATE_PLANT)) {
            lifeform = new Plant();
        } else {
            lifeform = null;
        }
        return lifeform;
    }

    /**
     * Returns true if there is no Lifeform in this Cell.
     * @return True if there is no Lifeform in this Cell.
     */
    public boolean isEmpty() {
        return life == null;
    }
}
