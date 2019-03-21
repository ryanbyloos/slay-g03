package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Territory;

public class States {
    private boolean territorySelected;
    private boolean soldierSelected;
    private boolean boatSelected;

    public boolean isBoatSelected() {
        return boatSelected;
    }

    public void setBoatSelected(boolean boatSelected) {
        this.boatSelected = boatSelected;
    }

    public boolean isAttackTowerSelected() {
        return attackTowerSelected;
    }

    public void setAttackTowerSelected(boolean attackTowerSelected) {
        this.attackTowerSelected = attackTowerSelected;
    }

    private boolean attackTowerSelected;
    private boolean creationMode;
    private Cell destination;
    private Cell source;
    private Territory territory;
    public boolean isTerritorySelected() {
        return territorySelected;
    }

    public void setTerritorySelected(boolean territorySelected) {
        this.territorySelected = territorySelected;
    }

    public boolean isSoldierSelected() {
        return soldierSelected;
    }

    public void setSoldierSelected(boolean soldierSelected) {
        this.soldierSelected = soldierSelected;
    }

    public boolean isCreationMode() {
        return creationMode;
    }

    public void setCreationMode(boolean creationMode) {
        this.creationMode = creationMode;
    }

    public Cell getDestination() {
        return destination;
    }

    public void setDestination(Cell destination) {
        this.destination = destination;
    }

    public Cell getSource() {
        return source;
    }

    public void setSource(Cell source) {
        this.source = source;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    @Override
    public String toString() {
        return "[creationMode][territorySelected][soldierSelected][boatSelected]" + "["  + creationMode+ "]"+"["+ territorySelected+ "]"  + "["+ soldierSelected+ "]"  +"["+ boatSelected+ "]";
    }

}
