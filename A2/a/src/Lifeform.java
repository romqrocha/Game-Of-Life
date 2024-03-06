import java.awt.Color;
import java.util.ArrayList;

public abstract class Lifeform {

    /** The colour representing this Lifeform. */
    protected Color colour;

    /** How nutritious this Lifeform is. */
    protected int nutritionValue;

    /** The value at which this Lifeform dies of hunger. */
    protected int hungerLimit;

    /** How hungry this Lifeform currently is. */
    protected int hunger;

    /**
     * Chooses one Cell to move to, out of an array of neighbouring cells.
     * Lifeforms can only move to cells that are "edible", as specified by
     * their <code>canEat()</code> method.
     * @param neighbours The options that this Lifeform can choose from.
     * @return The Cell that this Lifeform chose to move to.
     */
    public Cell chooseMove(Cell[] neighbours) {
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        for (Cell cell : neighbours) {
            if (this.canEat(cell.getLifeform())) {
                possibleMoves.add(cell);
            }
        }

        Cell chosenCell;
        if (possibleMoves.isEmpty()) {
            chosenCell = null;
        } else {
            int chosenIndex = RandomGenerator.nextNumber(possibleMoves.size());
            chosenCell = possibleMoves.get(chosenIndex);
        }

        return chosenCell;
    }

    /**
     * Checks if this Lifeform can eat the given Lifeform.
     * @param target The Lifeform that may or may not be edible by this Lifeform.
     * @return True if the given Lifeform is edible by this Lifeform.
     */
    protected abstract boolean canEat(Lifeform target);

    /**
     * Returns the colour of this Lifeform.
     * @return The colour of this Lifeform.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Changes the colour of this Lifeform.
     * @param colour The colour that this Lifeform should be.
     */
    protected void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Gets the nutrition value of this Lifeform. When it is eaten, the predator's
     * hunger value is subtracted by this Lifeform's nutrition value.
     * @return The nutrition value of this Lifeform.
     */
    public int getNutritionValue() {
        return nutritionValue;
    }

    /**
     * Sets how nutritious this Lifeform is. When it is eaten, the
     * predator's hunger value is subtracted by this Lifeform's nutrition value.
     * @param value How nutritious this Lifeform should be.
     */
    protected void setNutritionValue(int value) {
        this.nutritionValue = value;
    }

    /**
     * Gets the value at which this Lifeform dies of hunger.<br>
     * A value of -1 implies that this Lifeform does not die of hunger.
     * @return The value at which this Lifeform dies of hunger.
     */
    public int getHungerLimit() {
        return hungerLimit;
    }

    /**
     * Sets the integer value at which a Lifeform should die of hunger.<br>
     * Set it to -1 for Lifeforms that do not die of hunger.
     * @param value The integer value at which a Lifeform should die of hunger.
     */
    protected void setHungerLimit(int value) {
        this.hungerLimit = value;
    }

    /**
     * Returns a String representation of this Lifeform.<br>
     * The format should be: "Name" or "Name | Hunger: X"<br>
     * Example 1: "Herbivore | Hunger: 0"<br>
     * Example 2: "Plant"
     * @return A String representation of this Lifeform.
     */
    @Override
    public abstract String toString();
}