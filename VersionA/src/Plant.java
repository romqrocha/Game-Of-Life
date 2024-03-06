import java.awt.Color;

/**
 * A plant cannot move, but can reproduce under the right conditions.
 */
public class Plant extends Lifeform implements EdibleByHerbivore {

    /** The minimum amount of nearby Plants for another Plant to reproduce. */
    public static final int MIN_NEARBY_PLANTS = 1;

    /** The minimum amount of nearby free space for a Plant to reproduce. */
    public static final int MIN_FREE_SPACE = 5;

    public Plant() {
        setColour(Color.GREEN);
        setNutritionValue(5);
        setHungerLimit(-1);
    }

    @Override
    public boolean canEat(Lifeform target) {
        return target == null;
    }

    /**
     * Given the neighbours of this Plant, returns true if it can reproduce.
     * @param neighbours The neighbouring Cells of this Plant.
     * @return True if this Plant can reproduce.
     */
    public boolean canReproduce(Cell[] neighbours) {
        int numOfPlants = 0;
        int amtOfSpace = 0;
        for (Cell cell : neighbours) {
            if (cell.getLifeform() instanceof Plant) {
                numOfPlants++;
            } else if (cell.isEmpty()) {
                amtOfSpace++;
            }
        }

        return numOfPlants >= MIN_NEARBY_PLANTS && amtOfSpace >= MIN_FREE_SPACE;
    }

    @Override
    public void live(Cell home, Cell[] neighbours) {
        // If it can reproduce, clone self to nearby cell
        if (canReproduce(neighbours)) {
            Cell target = chooseMove(neighbours);
            World.cloneLifeform(home, target);
        }
    }

    @Override
    public Plant getNewChild() {
        return new Plant();
    }

    @Override
    public String toString() {
        return "Plant";
    }
}
