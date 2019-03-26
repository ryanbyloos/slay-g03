package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Territory;

import java.util.ArrayList;

public class States {
    private boolean over;
    private boolean territorySelected;
    private boolean soldierSelected;
    private boolean boatSelected;
    private boolean attackTowerSelected;
    private boolean creationMode;
    private boolean upgradeMode;
    private Cell destination;
    private Cell source;
    private Territory territory;
    private ArrayList<Cell> creatableCells;

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

    public boolean isSelectionMode() {
        return isAttackTowerSelected() || isSoldierSelected() || isBoatSelected();
    }

    public void reset() {
        setTerritorySelected(false);
        setSoldierSelected(false);
        setAttackTowerSelected(false);
        setCreationMode(false);
        setTerritory(null);
        setUpgradeMode(false);
        setCreatableCells(null);


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
        return "[creationMode][territorySelected][soldierSelected][boatSelected][upgradeMode]" + "[" + creationMode + "]" + "[" + territorySelected + "]" + "[" + soldierSelected + "]" + "[" + boatSelected + "]" + "[" + upgradeMode + "]";
    }

    public boolean isUpgradeMode() {
        return upgradeMode;
    }

    public void setUpgradeMode(boolean upgradeMode) {
        this.upgradeMode = upgradeMode;
    }

    public ArrayList<Cell> getCreatableCells() {
        return creatableCells;
    }

    public void setCreatableCells(ArrayList<Cell> creatableCells) {
        this.creatableCells = creatableCells;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
