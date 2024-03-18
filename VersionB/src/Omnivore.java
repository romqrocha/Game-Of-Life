import java.awt.*;

public class Omnivore extends Lifeform implements EdibleByCarnivore {

    /** The minimum amount of nearby Omnivores for another Omnivore to reproduce. */
    public static final int MIN_NEARBY_OMNIVORES = 1;

    /** The maximum amount of nearby Omnivores for another Omnivore to reproduce. */
    public static final int MAX_NEARBY_OMNIVORES = 2;

    /** The minimum amount of nearby free space for an Omnivore to reproduce. */
    public static final int MIN_FREE_SPACE = 3;

    /** The minimum amount of nearby food for an Omnivore to reproduce. */
    public static final int EXACT_NEARBY_FOOD = 1;

    public Omnivore () {
        setColour(Color.BLUE);
        setNutritionValue(8);
        setHungerLimit(4);
    }

    @Override
    public void live(Cell home, Cell[] neighbours) {
        // Choose cell
        Cell target = chooseMove(neighbours);

        // Exit if there are no available moves
        if (target == null) {
            return;
        }

        // Eat target if it is not empty
        boolean foundFood = !target.isEmpty();
        if (foundFood) {
            int food = target.getLifeform().getNutritionValue();
            hunger = Math.max(hunger - food, 0);
        }

        if (canReproduce(neighbours)) {
            // Clone into the target cell
            World.cloneLifeform(home, target);
        } else {
            // Move into the target cell
            World.moveLifeform(home, target);
        }

        // update home cell
        home = target;

        // Check hunger, die if above limit
        if (hunger > hungerLimit) {
            home.setLifeform(null);
        }
    }

    @Override
    protected boolean canEat(Lifeform target) {
        return ((target == null) || (target instanceof EdibleByOmnivore));
    }

    @Override
    protected boolean canReproduce(Cell[] neighbours) {
        int numOfOmnivores = 0;
        int amtOfSpace = 0;
        int amtOfFood = 0;

        for (Cell cell : neighbours) {
            if (cell.getLifeform() instanceof Omnivore) {
                numOfOmnivores++;
            } else if (cell.isEmpty()) {
                amtOfSpace++;
            } else if (canEat(cell.getLifeform())) {
                amtOfFood++;
            }
        }

        return numOfOmnivores >= MIN_NEARBY_OMNIVORES && numOfOmnivores <= MAX_NEARBY_OMNIVORES
                && amtOfSpace >= MIN_FREE_SPACE
                && amtOfFood == EXACT_NEARBY_FOOD;
    }

    @Override
    public String toString() {
        return "Omnivore | Hunger: " + hunger;
    }

    @Override
    public Lifeform getNewChild() {
        return new Omnivore();
    }
}
