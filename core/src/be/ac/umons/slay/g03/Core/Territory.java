package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.Capital;
import be.ac.umons.slay.g03.Entity.Grave;
import be.ac.umons.slay.g03.Entity.Soldier;
import be.ac.umons.slay.g03.Entity.Tree;

import java.util.ArrayList;
import java.util.Random;

public class Territory {
    private ArrayList<Cell> cells;
    private int gainThisTurn;

    public int gain() {
        int gain = 0;
        for (Cell cell : cells
        ) {
            if (!(cell.getElementOn() instanceof Tree)) gain++;
        }
        return gain;
    }

    public Territory(ArrayList<Cell> cells) {

        this.cells = cells;
    }

    public void checkcost() {
        int cost = 0;
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() != null) {
                cost = cost + cell.getElementOn().getMaintenanceCost();
            }
        }

        if (gain() + findCapital().getMoney() >= cost) {
            findCapital().addMoney(gain() - cost);
            gainThisTurn = gain() - cost;
        } else {
            findCapital().addMoney(gain());
            bankrupt();
            gainThisTurn = gain();
        }


    }

    private void bankrupt() {
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() instanceof Soldier) cell.setElementOn(new Grave(cell.getElementOn().getLevel()));
        }
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

    public void placeCapital() {
        ArrayList<Cell> cellPossible = new ArrayList<>();
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() == null) {
                cellPossible.add(cell);
            }
        }
        if (cellPossible.size() != 0) {
            int index = new Random().nextInt(cellPossible.size());
            cellPossible.get(index).setElementOn(new Capital(cellPossible.get(0).getOwner(), 0));
            return;
        }
        int index = new Random().nextInt(cells.size());
        cells.get(index).setElementOn(new Capital(cells.get(0).getOwner(), 0));
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

    public int getGainThisTurn() {
        return gainThisTurn;
    }
}
