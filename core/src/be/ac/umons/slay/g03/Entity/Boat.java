package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class Boat extends Infrastructure implements Controlable {

    private int t;
    private int defense;
    private boolean hasMoved;

    public Boat(int t, int defense, int maintenanceCost, int creationCost, Player player) {
        super(maintenanceCost, creationCost, player);
        this.t = t;
        this.defense = defense;
        hasMoved = false;

    }

    public boolean deploy(){
        return false;
    }

    private ArrayList<Cell> checkLand(){
        return null;
    }

    private void setDefenceLevel(){

    }

    public void capture(Soldier soldier) {

    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean belongsTo() {
        return false;
    }

    @Override
    public void move(Cell cell) {

    }

    @Override
    public ArrayList<Cell> accessibleCell() {
        return null;
    }

    @Override
    public void select() {

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Boat)) {
            return false;
        }
        Boat that = (Boat) other;
        return this.getCreationCost() == (that.getCreationCost())
                && this.getMaintenanceCost() == (that.getMaintenanceCost())
                && this.getT() == that.getT()
                && this.getDefense() == that.getDefense()
                && this.getOwner().equals(that.getOwner());
    }
}