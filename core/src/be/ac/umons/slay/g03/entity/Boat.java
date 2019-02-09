package be.ac.umons.slay.g03.entity;

public class Boat {

    private int t;
    private int defense;
    private boolean hasMoved;

    public Boat(int t, int defense) {
        this.t = t;
        this.defense = defense;
        hasMoved = false;
    }

    public boolean deploy(){

    }

   private Cell checkLand(){

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
}