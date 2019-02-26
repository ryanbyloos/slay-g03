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

    public boolean attack(Cell cellAttacker, Cell cellDefender) {
        int levelAttacker = cellAttacker.getElementOn().getLevel();
        int levelDefender = cellDefender.getElementOn().getLevel();


        if (levelAttacker != 3) {
            if (levelAttacker > levelDefender){
                cellDefender.setElementOn(null);
                return true;
            }
        }
        else if (levelDefender == 3){
            cellAttacker.setElementOn(null);
            cellDefender.setElementOn(null);

            return false;

        }

        else {
            cellDefender.setElementOn(null);
            return true;
        }
        return false;
    }

    private void merge(Soldier allySoldier) {

    }

    private void checkNewTerritory() {

    }
    @Override
    public int getLevel() {
        return level;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }


    @Override
    public boolean belongsTo() {
        return false;
    }

    @Override
    public void move(Cell cell) {
        /*if (cell.getElementOn().getClass() == Soldier.class) {
            attack((Soldier) cell.getElementOn());
        }*/
    }

    @Override
    public ArrayList<Cell> accessibleCell() {
        return null;
    }

    @Override
    public void select() {

    }
}
