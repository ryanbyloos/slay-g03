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
}
