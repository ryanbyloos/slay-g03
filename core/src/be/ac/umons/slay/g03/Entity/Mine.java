package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Mine extends Infrastructure {

    public Mine(Player owner) {
        super(owner);
        this.creationCost = 20;
        this.maintenanceCost = 0;
    }

}
