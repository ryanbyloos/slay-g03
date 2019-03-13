package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public abstract class MapElement {
    private int maintenanceCost;
    private int creationCost;
    private Player owner;
    private boolean hasMoved;

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

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public ArrayList<Cell> accessibleCell(Map map, Cell cell){
        return null;
    }
}
