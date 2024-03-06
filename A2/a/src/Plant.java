import java.awt.Color;

public class Plant extends Lifeform implements EdibleByHerbivore {

    public Plant() {
        setColour(Color.GREEN);
        setNutritionValue(5);
        setHungerLimit(-1);
    }

    @Override
    protected boolean canEat(Lifeform target) {
        return false;
    }

    @Override
    public String toString() {
        return "Plant";
    }

    @Override
    public void live(Cell home, Cell target) {

    }
}
