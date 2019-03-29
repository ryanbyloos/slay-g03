package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

/**
 * Classe instanciant une Mine
 */
public class Mine extends Infrastructure {

    public Mine(Player owner) {
        super(owner);
        this.creationCost = 20;
        this.maintenanceCost = 0;
    }

}
