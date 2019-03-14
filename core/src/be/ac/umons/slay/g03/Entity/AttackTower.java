package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class AttackTower extends DefenceTower {

    public AttackTower(int maintenanceCost, int creationCost, Player player, int level) {
        super(maintenanceCost, creationCost, player, level);
    }

    @Override
    public boolean belongsTo() {
        return super.belongsTo();
    }

    public ArrayList<Cell> reachableCells() {
        return null;
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

}
