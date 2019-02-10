package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class Soldier extends MapElement implements Controlable {
    private int level;
    private boolean hasMoved;

    public Soldier(int maintenanceCost, int creationCost, Player owner, int level) {
        super(maintenanceCost, creationCost, owner);
        this.level = level;
        hasMoved = false;
    }

    private void attack(Soldier ennemySoldier){

    }
    private void merge(Soldier allySoldier){

    }
    private void checkNewTerritory(){

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
}
