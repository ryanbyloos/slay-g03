package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class DefenceTower extends Infrastructure {
    private int level;

    public DefenceTower(Player owner, int level) {
        super(owner);
        this.level = level;
        switch (level) {
            case 0:
                this.creationCost = 10;
                this.maintenanceCost = 1;
                break;
            case 1:
                this.creationCost = 20;
                this.maintenanceCost = 3;
                break;
            case 2:
                this.creationCost = 40;
                this.maintenanceCost = 7;
                break;
            case 3:
                this.creationCost = 80;
                this.maintenanceCost = 20;
                break;
        }
    }

    public void levelUp() {
        if (this.level >= 0 && this.level < 3)
            level++;
    }

    public boolean belongsTo() {
        return false;
    }

    public int getLevel() {
        return this.level;
    }
}