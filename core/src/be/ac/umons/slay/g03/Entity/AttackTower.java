package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class AttackTower extends Infrastructure{
    private int level;
    private boolean hasAttack;
    public AttackTower(Player owner, int level) {
        super(owner);
        this.level = level;
        switch (level) {
            case 0:
                this.creationCost = 20;
                this.maintenanceCost = 2;
                break;
            case 1:
                this.creationCost = 40;
                this.maintenanceCost = 6;
                break;
            case 2:
                this.creationCost = 80;
                this.maintenanceCost = 12;
                break;
            case 3:
                this.creationCost = 160;
                this.maintenanceCost = 36;
                break;
        }
    }



    public ArrayList<Cell> reachableCells() {
        return null;
    }

    public boolean select() {
        return getOwner().isTurn() && !hasAttack;
    }
    @Override
    public int getLevel() {
        return this.level;
    }

    public void attack(Cell cell) {
        if (cell.getElementOn() instanceof Soldier) {
            if (((cell.getElementOn()).getLevel() < this.getLevel()
                    || this.getLevel() == 3 && (cell.getElementOn()).getLevel() == 3)
                    && !cell.getElementOn().getOwner().equals(this.getOwner())) {
                cell.setElementOn(null);
            }
        }
    }

    public void setHasAttack(boolean hasAttack) {
        this.hasAttack = hasAttack;
    }
}
