package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Territory;

import java.util.ArrayList;

public class States {
    private boolean boatCreation;
    private boolean mineCreation;
    private boolean otherCreation;
    private boolean territorySelected;
    private boolean soldierSelected;
    private boolean boatSelected;
    private boolean attackTowerSelected;
    private boolean defenceTowerSelected;
    private boolean upgradeAble;
    private boolean over;
    private Cell hold;
    private ArrayList<Cell> displayCells;
    private ArrayList<Cell> creatableCells;
    private Territory territoryLoaded;

    public boolean isOtherCreation() {
        return otherCreation;
    }

    public void setOtherCreation(boolean otherCreation) {
        this.otherCreation = otherCreation;
    }
    public boolean isEverythingFalse(){
        return !(boatCreation || mineCreation || otherCreation || territorySelected || soldierSelected || boatSelected || attackTowerSelected
                || defenceTowerSelected || upgradeAble);
    }
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


    public boolean isSelectionMode() {
        return isAttackTowerSelected() || isSoldierSelected() || isBoatSelected();
    }

    public void reset() {
        setBoatCreation(false);
        setMineCreation(false);
        setOtherCreation(false);
        setTerritorySelected(false);
        setBoatSelected(false);
        setAttackTowerSelected(false);
        setDefenceTowerSelected(false);
        setUpgradeAble(false);
    }
    public Cell getHold() {
        return hold;
    }

    public void setHold(Cell hold) {
        this.hold = hold;
    }

    public ArrayList<Cell> getDisplayCells() {
        return displayCells;
    }

    public void setDisplayCells(ArrayList<Cell> displayCells) {
        this.displayCells = displayCells;
    }

    @Override
    public String toString() {
        return "[selectionMode][creationMode][territorySelected][upgradeMode][soldierCreation][boatCreation][mineCreation][SOURCE][DESTINATION]" +
                "["+ isSelectionMode()+"]" +
                "[" + territorySelected + "]" +
                "[" + otherCreation + "]" +
                "[" + boatCreation + "]" +
                "[" + mineCreation + "]" +
                "[" + hold + "]";
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isMineCreation() {
        return mineCreation;
    }

    public void setMineCreation(boolean mineCreation) {
        this.mineCreation = mineCreation;
    }




    public boolean isBoatCreation() {
        return boatCreation;
    }

    public void setBoatCreation(boolean boatCreation) {
        this.boatCreation = boatCreation;
    }

    public boolean isDefenceTowerSelected() {
        return defenceTowerSelected;
    }

    public void setDefenceTowerSelected(boolean defenceTowerSelected) {
        this.defenceTowerSelected = defenceTowerSelected;
    }

    public boolean isUpgradeAble() {
        return upgradeAble;
    }

    public void setUpgradeAble(boolean upgradeAble) {
        this.upgradeAble = upgradeAble;
    }

    public Territory getTerritoryLoaded() {
        return territoryLoaded;
    }

    public void setTerritoryLoaded(Territory territoryLoaded) {
        this.territoryLoaded = territoryLoaded;
    }
}
