package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

public abstract class MapElement {
    int maintenanceCost;
    int creationCost;
    private Player owner;
    private boolean hasMoved;

    public MapElement(Player owner) {
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

    public int getLevel() {
        return -1;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    protected void checkNewTerritory(Map map, Cell newCell, Cell oldCell) {

    }

}
