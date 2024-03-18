import java.awt.*;

public class Herbivore extends Lifeform implements EdibleByOmnivore, EdibleByCarnivore {

    /** The minimum amount of nearby Herbivores for another Herbivore to reproduce. */
    public static final int MIN_NEARBY_HERBIVORES = 1;

    /** The maximum amount of nearby Herbivores for another Herbivore to reproduce. **/
    public static final int MAX_NEARBY_HERBIVORES = 3;

    /** The minimum amount of nearby free space for a Herbivore to reproduce. */
    public static final int MIN_FREE_SPACE = 1;

    /** The minimum amount of nearby food for a Herbivore to reproduce. */
    public static final int MIN_NEARBY_FOOD = 2;

    public Herbivore() {
        setColour(Color.YELLOW);
        setNutritionValue(3);
        setHungerLimit(5);
    }

    @Override
    protected boolean canEat(Lifeform target) {
        return ((target == null) || (target instanceof EdibleByHerbivore));
    }

    @Override
    protected boolean canReproduce(Cell[] neighbours) {
        int numOfHerbivores = 0;
        int amtOfSpace = 0;
        int amtOfFood = 0;

        for (Cell cell : neighbours) {
            if (cell.getLifeform() instanceof Herbivore) {
                numOfHerbivores++;
            } else if (cell.isEmpty()) {
                amtOfSpace++;
            } else if (canEat(cell.getLifeform())) {
                amtOfFood++;
            }
        }

        return numOfHerbivores >= MIN_NEARBY_HERBIVORES && numOfHerbivores <= MAX_NEARBY_HERBIVORES
                && amtOfSpace >= MIN_FREE_SPACE
                && amtOfFood >= MIN_NEARBY_FOOD;
    }

    @Override
    public void live(Cell home, Cell[] neighbours) {
        // Choose cell
        Cell target = chooseMove(neighbours);

        // Exit if there are no available moves
        if (target != null) {
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
        }

        // Check hunger, die if above limit
        if (hunger > hungerLimit) {
            home.setLifeform(null);
        }
    }

    @Override
    public Herbivore getNewChild() {
        return new Herbivore();
    }

    @Override
    public String toString() {
        return "Herbivore | Hunger: " + hunger;
    }
}
