package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

public class Boat extends Infrastructure implements Controlable {

    private int t = 5;
    private int defence;
    private boolean hasMoved;

    private ArrayList<Soldier> soldiers = new ArrayList<>();

    public Boat(Player player, boolean hasMoved) {
        super(player);
        this.hasMoved = hasMoved;
        this.defence = 0;
        if (soldiers != null && soldiers.size() != 0) {
            for (Soldier s : soldiers) {
                if (s.getLevel() >= defence)
                    defence = s.getLevel();
            }
        }
        this.maintenanceCost = 0;
        this.creationCost = 25;
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

    public int getDefence() {
        if (soldiers != null && soldiers.size() != 0)
            for (Soldier soldier : soldiers)
                if (soldier.getLevel() >= this.defence)
                    this.defence = soldier.getLevel();
        return this.defence;
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
                && this.getDefence() == that.getDefence()
                && this.getOwner().equals(that.getOwner())
                && this.isHasMoved() == that.isHasMoved()
                && this.getSoldiers().equals(that.getSoldiers());
    }
}