public class Omnivore extends Lifeform implements EdibleByCarnivore {

    @Override
    public void live(Cell home, Cell[] neighbours) {

    }

    @Override
    protected boolean canEat(Lifeform target) {
        return false;
    }

    @Override
    protected boolean canReproduce(Cell[] neighbours) {
        return false;
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
