package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class AttackTower extends DefenceTower {

    public AttackTower(int maintenanceCost, int creationCost, Player player) {
        super(maintenanceCost, creationCost, player);
    }

    @Override
    public boolean belongsTo() {
        return super.belongsTo();
    }

    public ArrayList<Cell> attackAbleCells(){
        return null;
    }

    public void attack(Cell cell){

    }
}
