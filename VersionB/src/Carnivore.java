import java.awt.*;

public class Carnivore extends Lifeform implements EdibleByOmnivore {

    /** The minimum amount of nearby Carnivores for another Carnivore to reproduce. */
    public static final int MIN_NEARBY_CARNIVORES = 1;

    /** The maximum amount of nearby Carnivores for another Carnivore to reproduce. */
    public static final int MAX_NEARBY_CARNIVORES = 4;

    /** The minimum amount of nearby free space for a Carnivore to reproduce. */
    public static final int MIN_FREE_SPACE = 3;

    /** The minimum amount of nearby food for a Carnivore to reproduce. */
    public static final int MIN_NEARBY_FOOD = 2;

    public Carnivore() {
        setColour(Color.RED);
        setNutritionValue(6);
        setHungerLimit(6);
    }

    @Override
    public void live(Cell home, Cell[] neighbours) {
        // Choose cell
        Cell target = chooseMove(neighbours);

        // Skip if there are no available moves
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
    protected boolean canEat(Lifeform target) {
        return ((target == null) || (target instanceof EdibleByCarnivore));
    }

    @Override
    protected boolean canReproduce(Cell[] neighbours) {
        int numOfCarnivores = 0;
        int amtOfSpace = 0;
        int amtOfFood = 0;

        for (Cell cell : neighbours) {
            if (cell.getLifeform() instanceof Carnivore) {
                numOfCarnivores++;
            } else if (cell.isEmpty()) {
                amtOfSpace++;
            } else if (canEat(cell.getLifeform())) {
                amtOfFood++;
            }
        }

        return numOfCarnivores >= MIN_NEARBY_CARNIVORES && numOfCarnivores <= MAX_NEARBY_CARNIVORES
                && amtOfSpace >= MIN_FREE_SPACE
                && amtOfFood >= MIN_NEARBY_FOOD;
    }

    @Override
    public String toString() {
        return "Carnivore | Hunger: " + hunger;
    }

    @Override
    public Lifeform getNewChild() {
        return new Carnivore();
    }
}
