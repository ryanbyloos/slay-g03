package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public abstract class MapElement {
    private int maintenanceCost;
    private int creationCost;
    private Player owner;

    public MapElement(int maintenanceCost, int creationCost, Player owner) {
        this.maintenanceCost = maintenanceCost;
        this.creationCost = creationCost;
        this.owner = owner;
    }





}
