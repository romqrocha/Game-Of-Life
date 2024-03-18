public class Carnivore extends Lifeform implements EdibleByOmnivore {

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
        return "Carnivore | Hunger: " + hunger;
    }

    @Override
    public Lifeform getNewChild() {
        return new Carnivore();
    }
}
