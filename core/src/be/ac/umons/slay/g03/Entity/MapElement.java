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

    public int getMaintenanceCost() {
        return this.maintenanceCost;
    }

    public int getCreationCost() {
        return this.creationCost;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    public int getLevel(){
        return -1;
    }


}
