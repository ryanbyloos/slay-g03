package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.*;

import java.util.ArrayList;
import java.util.Random;


public class Cell {

    private int y;
    private int x;
    private boolean checked;
    private boolean water;
    private Player owner;
    private MapElement elementOn;

    public Cell(int x, int y, boolean checked, boolean water, Player owner, MapElement elementOn) {
        this.x = x;
        this.y = y;
        this.checked = checked;
        this.water = water;
        this.owner = owner;
        this.elementOn = elementOn;
    }

    public void kill() {
        elementOn = new Grave(elementOn.getLevel());

    }

    public Territory findTerritory() {
        if (owner != null) {
            for (Territory territory : owner.getTerritories()) {
                for (Cell cell : territory.getCells()) {
                    if (cell.getX() == x && cell.getY() == y) {
                        return territory;
                    }
                }
            }
        }

        return null;
    }

    public void spwanTree(Map map) {
        if (elementOn == null && !water) {
            int tree = 0;
            for (Cell cell : adjacentCell(map, this, false)
            ) {
                if (cell.getElementOn() instanceof Tree) tree++;
            }
            float random = new Random().nextInt(100);
            if (random / 100 < 1 / 100 + (tree * Math.log10(tree + 1)) / 100) {
                elementOn = new Tree();
            }
        }
    }

    public void levelUpTower(){
        if(elementOn instanceof  AttackTower && elementOn.getLevel()<3) elementOn = new AttackTower(elementOn.getOwner(),elementOn.getLevel()+1);

        else if( elementOn instanceof DefenceTower && elementOn.getLevel()<3) elementOn = new DefenceTower(elementOn.getOwner(),elementOn.getLevel()+1);
    }

    public ArrayList<Cell> accessibleCell(Map map) {
        ArrayList<Cell> adjacentCell = new ArrayList<>();
        ArrayList<Cell> accessibleCell = new ArrayList<>(adjacentCell(map, this, false));

        for (int dist = 3; dist > 0; dist--) {
            for (Cell cell : accessibleCell) {
                if (cell.getOwner() != null && cell.getOwner().equals(this.getOwner())) {
                    adjacentCell.addAll(adjacentCell(map, cell, false));
                }
            }
            for (Cell cellAdj : adjacentCell) {
                if (!accessibleCell.contains(cellAdj)) {
                    accessibleCell.add(cellAdj);
                }
            }
        }
        accessibleCell.remove(this);
        return accessibleCell;
    }

    public ArrayList<Cell> adjacentCell(Map map, Cell himself, boolean water) {
        ArrayList<Cell> adjacentCell = new ArrayList<>();

        int x = himself.getX();
        int y = himself.getY();
        int p = (map.getHeight() % 2 == y % 2) ? 0 : 1;

        addCell(adjacentCell, map.findCell(x - p, y - 1),water);
        addCell(adjacentCell, map.findCell(x + 1 - p, y - 1),water);
        addCell(adjacentCell, map.findCell(x - 1, y),water);
        addCell(adjacentCell, map.findCell(x + 1, y),water);
        addCell(adjacentCell, map.findCell(x, y + 1),water);
        addCell(adjacentCell, map.findCell(x + 1 - (2 * p), y + 1),water);

        return adjacentCell;
    }

    private void addCell(ArrayList<Cell> cells, Cell cell, boolean water) {
        if (!water && cell != null && (!cell.isWater() || cell.getElementOn() instanceof Boat)) cells.add(cell);
        if(water && cell != null && cell.isWater()) cells.add(cell);
    }

    public Territory createTerritory(Map map, Boolean mark, Territory territory) {
        this.setChecked(!this.isChecked());
        territory.addCell(this);
        for (Cell cellAdj : this.adjacentCell(map, this, false)) {
            if (this.getOwner() != null && cellAdj.getOwner() != null && cellAdj.getOwner().equals(this.getOwner()) && cellAdj.isChecked() == mark) {
                cellAdj.createTerritory(map, mark, territory);
            }
        }
        return territory;
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

    public void setX(int x) {
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

    @Override
    public String toString() {
        if (owner != null)
            return "Cell[x][y][owner][checked][elementOn] : " + "[" + x + "]" + "[" + y + "]" + "[" + owner.getClass().getSimpleName() + "]" + "[" + checked + "]" + "[" + elementOn + "]\n";
        else
            return "Cell[x][y][owner][checked] : " + "[" + x + "]" + "[" + y + "]" + "[" + owner + "]" + "[" + checked + "]\n";
    }


}
