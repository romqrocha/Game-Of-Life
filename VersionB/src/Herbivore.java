import java.awt.*;

/**
 * A herbivore moves around the game board and eats plants.
 */
public class Herbivore extends Lifeform implements EdibleByOmnivore, EdibleByCarnivore {

    public Herbivore() {
        setColour(Color.YELLOW);
        setNutritionValue(0);
        setHungerLimit(5);
    }

    @Override
    protected boolean canEat(Lifeform target) {
        return ((target == null) || (target instanceof EdibleByHerbivore));
    }

    @Override
    protected boolean canReproduce(Cell[] neighbours) {
        return false;
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

        // Move into the target cell
        World.moveLifeform(home, target);
        home = target;

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
