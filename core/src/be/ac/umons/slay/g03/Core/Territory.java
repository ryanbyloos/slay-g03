package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Territory {
    private ArrayList<Cell> cells;

    public int gain() {
        return 0;
    }

    public Territory(ArrayList<Cell> cells) {

        this.cells = cells;
    }

    private int checkcost() {
        return 0;
    }

    private void bankrupt() {

    }

    public boolean addCell(Cell cell) {
        cells.add(cell);
        return true;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}
