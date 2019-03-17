package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.Capital;

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

    public Capital findCapital(){
        for (Cell cell: cells
             ) {
            if(cell.getElementOn() instanceof Capital) return (Capital)cell.getElementOn();
        }
        return null;
    }

    public int removeCapital(Map map){
        int money = findCapital().getMoney();
        map.findCell(findCapital()).setElementOn(null);
        return money;
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

    @Override
    public String toString() {
        String info = "Territory : \n";
        for (Cell c : cells) {
            info += c.toString();
        }
        return info;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Territory)) {
            return false;
        }
        Territory that = (Territory) other;
        if (this.getCells().size() != that.getCells().size()) return false;
        for (int i = 0; i < that.getCells().size(); i++) {
            if (!this.getCells().contains(that.getCells().get(i))) {
                return false;
            }
        }
        return true;
    }
}
