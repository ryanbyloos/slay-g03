package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class Boat extends Infrastructure implements Controlable {

    private int t;
    private int defense;
    private boolean hasMoved;

    private ArrayList<Soldier> soldiers;

    public Boat(int t, int defense, int maintenanceCost, int creationCost, Player player, boolean hasMoved) {
        super(maintenanceCost, creationCost, player);
        this.t = t;
        this.defense = defense;
        this.hasMoved = hasMoved;

    }

    public boolean deploy() {
        return false;
    }

    private ArrayList<Cell> checkLand() {
        return null;
    }

    private void setDefenceLevel() {

    }

    public void capture(Soldier soldier) {
        // TODO : verify the levels.
        this.setOwner(soldier.getOwner());
        this.soldiers = new ArrayList<>();
        this.soldiers.add(soldier);
    }

    public int getT() {
        return t;
    }

    public int getDefense() {
        if (soldiers != null && soldiers.size() != 0)
            for (Soldier soldier : soldiers)
                if (soldier.getLevel() >= this.defense)
                    this.defense = soldier.getLevel();
        return this.defense;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    @Override
    public boolean belongsTo() {
        return false;
    }

    @Override
    public void move(Cell source, Cell destination, Map map) {

    }

    @Override
    public ArrayList<Cell> accessibleCell(Map map, Cell source) {
        return null;
    }


    @Override
    public boolean select() {
        return false;

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
                && this.getOwner().equals(that.getOwner())
                && this.isHasMoved() == that.isHasMoved()
                && this.getSoldiers().equals(that.getSoldiers());
    }
}