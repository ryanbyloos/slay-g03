package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.MapElement;

public class Cell {
    private int y;
    private int x;
    private boolean checked;
    private boolean water;
    private Player owner;
    private MapElement elementOn;
    public Cell(int x, int y, boolean checked, boolean water, Player owner, MapElement elementOn){
        this.x = x;
        this.y = y;
        this.checked = checked;
        this.water = water;
        this.owner = owner;
        this.elementOn = elementOn;
    }

    public void kill(){

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public MapElement getElementOn() {
        return elementOn;
    }

    public void setElementOn(MapElement elementOn) {
        this.elementOn = elementOn;
    }
}
