import java.awt.Color;
import java.util.function.BiFunction;

public class Herbivore extends Lifeform {

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
    public String toString() {
        return "Herbivore | Hunger: " + hunger;
    }

    @Override
    public void live(Cell home, Cell target) {
        // Update hunger
        int food;
        if (target.isEmpty()) {
            food = -1;
        } else {
            food = target.getLifeform().getNutritionValue();
        }
        hunger = Math.max(hunger - food, 0);

        // Check hunger
        if (hunger > hungerLimit) {
            home.setLifeform(null);
            return;
        }

        // Move
        World.moveLifeform(home, target);
    }
}
