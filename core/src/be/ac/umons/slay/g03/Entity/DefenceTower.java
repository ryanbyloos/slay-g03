package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class DefenceTower extends Infrastructure {
    private int level;

    public DefenceTower(int maintenanceCost, int creationCost, Player player, int level) {
        super(maintenanceCost, creationCost, player);
        this.level = level;
    }

    public void levelUp() {

    }

    public boolean belongsTo() {
        return false;
    }

    public int getLevel() {
        return level;
    }
}