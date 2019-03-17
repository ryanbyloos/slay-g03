package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Grave extends MapElement {

    private int level;

    public Grave(Player owner, MapElement soldier) {
        super(0, 0, owner);
        level = soldier.getLevel();

    }
}
