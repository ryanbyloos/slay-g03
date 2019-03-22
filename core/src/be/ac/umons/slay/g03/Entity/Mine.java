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

    public boolean checkExplosion() {
        return false;
    }

    private boolean explode(Boat boat) {
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setInvisible() {
        this.visible = false;
    }
}
