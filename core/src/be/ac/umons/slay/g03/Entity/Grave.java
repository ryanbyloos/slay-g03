package be.ac.umons.slay.g03.Entity;
public class Grave extends MapElement {
    private int level;
    public Grave(int level) {
        super(0, 0, null);
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
