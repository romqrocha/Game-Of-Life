import java.awt.Color;

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
}
