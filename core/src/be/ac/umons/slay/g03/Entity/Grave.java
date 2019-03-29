package be.ac.umons.slay.g03.Entity;

/**
 * Classe instanciant une Grave, contenant son level
 */
public class Grave extends MapElement {
    private int level;

    public Grave(int level) {
        super(null);
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
