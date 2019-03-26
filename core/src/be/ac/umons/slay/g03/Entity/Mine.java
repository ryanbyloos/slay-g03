package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Mine extends Infrastructure {
    private boolean visible;

    public Mine(Player owner) {
        super(owner);
        this.visible = true;
        this.creationCost = 15;
        this.maintenanceCost = 0;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setInvisible() {
        this.visible = false;
    }
}
