package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe instanciant un Territory, contenat une ArrayList de Cells et l'argent gagne ce tour
 * Possède egalement des methodes pour gerer la faillite et trouver sa capital
 */
public class Territory {
    private ArrayList<Cell> cells;
    private int gainThisTurn;

    public Territory(ArrayList<Cell> cells) {
        this.cells = cells;
        this.gainThisTurn = gain();
    }

    /**
     * calcule l'argent genere par le Territory
     * @return l'argent genere par le Territory
     */
    public int gain() {
        int gain = 0;
        for (Cell cell : cells
        ) {
            if (!(cell.getElementOn() instanceof Tree)) gain++;
        }
        return gain;
    }

    /**
     * verifie si le Territory est en faillite, et si oui l'applique
     */
    public void checkcost() {

        if (gain() + findCapital().getMoney() >= cost()) {
            findCapital().addMoney(gain() - cost());
            gainThisTurn = gain() - cost();
        } else {
            findCapital().addMoney(gain());
            bankrupt();
            gainThisTurn = gain();
        }


    }

    /**
     * calcule le cout du Territory
     * @return le cout du Territory
     */
    public int cost() {
        int cost = 0;
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() != null) {
                cost = cost + cell.getElementOn().getMaintenanceCost();
            }
        }
        return cost;
    }

    private void bankrupt() {
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() instanceof Soldier) cell.setElementOn(new Grave(cell.getElementOn().getLevel()));
            if (cell.getElementOn() instanceof AttackTower || cell.getElementOn() instanceof DefenceTower) cell.setElementOn(null);
        }
    }

    /**
     * trouve la Capitle du Territory
     * @return la Capitle du Territory
     */
    public Capital findCapital() {
        for (Cell cell : cells
        ) {
            if (cell.getElementOn() instanceof Capital) return (Capital) cell.getElementOn();
        }
        return null;
    }

    /**
     * supprime la capital du Territory
     * @param map
     * @return l'argent de la Capital detruite
     */
    public int removeCapital(Map map) {
        int money = findCapital().getMoney();
        map.findCell(findCapital()).setElementOn(null);
        return money;
    }

    /**
     * place une Capital aléatoirement sur le Territory
     */
    public void placeCapital() {
        ArrayList<Cell> cellPossible = new ArrayList<>();
        ArrayList<Cell> cellPossibleToDelete = new ArrayList<>();
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
        for (Cell cell : cells
                ) {
            if (!(cell.getElementOn() instanceof Capital)) cellPossibleToDelete.add(cell);
        }
        if ((cellPossibleToDelete.size() != 0)) {
            int index = new Random().nextInt(cellPossibleToDelete.size());
            cellPossibleToDelete.get(index).setElementOn(new Capital(cells.get(0).getOwner(), 0));
        }
    }

    /** trouve les Cells possible pour creer une unite terrestre
     * @return une ArrayList des Cells possible pour creer une unite terrestre
     */
    public ArrayList<Cell> accesibleCellToCreateUnit(){
        ArrayList<Cell> accesibleCellToCreateUnit = new ArrayList<>();
        accesibleCellToCreateUnit.addAll(cells);
        for (Cell cell : cells) {
            if (cell.getElementOn() != null) accesibleCellToCreateUnit.remove(cell);
        }

        return accesibleCellToCreateUnit;
    }

    /** trouve les Cells possible pour creer une unite navale
     * @param map
     * @return une ArrayList des Cells possible pour creer une unite navale
     */
    public ArrayList<Cell> getCellsForCreateWaterUnit(Map map) {
        ArrayList<Cell> cellsForCreateWaterUnit= new ArrayList<>();
        for (Cell cell: cells
                ) {
            for (Cell celladj:cell.adjacentCell(map,cell,true)
                    ) {
                if(!cellsForCreateWaterUnit.contains(celladj) && celladj.getElementOn() == null) cellsForCreateWaterUnit.add(celladj);
            }
        }
        return cellsForCreateWaterUnit;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
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

    public int getGainThisTurn() {
        return gainThisTurn;
    }


}
