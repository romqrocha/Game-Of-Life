import java.awt.Color;

/**
 * A Lifeform representing an inaccessible part of the game board.<br>
 * Ordinarily, nothing should call Void's <code>live()</code> method.
 */
public class Void extends Lifeform {

    public Void() {
        setColour(Color.BLACK);
        setNutritionValue(0);
        setHungerLimit(-1);
    }

    @Override
    public void live(Cell home, Cell target) {

    }

    @Override
    protected boolean canEat(Lifeform target) {
        return false;
    }

    @Override
    public String toString() {
        return "Void";
    }

    @Override
    public Void getNewChild() {
        return new Void();
    }
}
