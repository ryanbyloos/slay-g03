package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.*;

import java.util.ArrayList;
import java.util.Random;


/**
 * Classe qui instancie une cellule de coordonee (x,y).
 * Elle contient egalement : un boolean water qui dit si la cellule est de l'eau ou non,
 * son proprietaire , l'element present dessus
 * ainsi qu'un boolean qui permet de creer un Territory a partir d'une Cell grace a la methode createTerritory()
 * (ce boolean doit etre identique pour toutes les Cells d'un meme Territory).
 * Elle possede toutes les methodes qui touchent aux Cells.
 */
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


    /**
     * @return Le Territory qui contient la Cell qui a lance la methode
     */
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

    /** place un arbre sur la Cell avec une probabilite de 1/100 + (n * log10(n+1)) / 100 ou n est le nombre d'arbres adjacent
     * @param map
     */
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

    /**
     * augmente le level de la Tower sur la cell si elle peut etre upgrade et si la capital a assez d'argent,
     * retire egalement le cout de creation si l'upgrade s'est effectue
     */
    public void levelUpTower() {
        int lv = elementOn.getLevel();
        int money = findTerritory().findCapital().getMoney();
        if (money >= elementOn.getCreationCost() * 2) {
            if (elementOn instanceof AttackTower && lv < 3)
                elementOn = new AttackTower(elementOn.getOwner(), elementOn.getLevel() + 1);
            else if (elementOn instanceof DefenceTower && lv < 3)
                elementOn = new DefenceTower(elementOn.getOwner(), elementOn.getLevel() + 1);
            findTerritory().findCapital().addMoney(-elementOn.getCreationCost());
        }
    }

    /**
     * @param map
     * @return Une arraylist qui contient toute les Cells accesible par le move() d'un soldat
     */
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


    /**
     * @param map
     * @return Une arraylist qui contient toute les Cells atteignable par l'AttackTower en fonction de son level
     */
    public ArrayList<Cell> towerRange(Map map) {
        ArrayList<Cell> accessibleCell = new ArrayList<>(adjacentCell(map, this, true));
        accessibleCell.addAll(adjacentCell(map, this, false));
        ArrayList<Cell> adjacentCell = new ArrayList<>();
        for (int dist = getElementOn().getLevel(); dist > 0; dist--) {
            for (Cell cell : accessibleCell) {
                adjacentCell.addAll(adjacentCell(map, cell, false));
                adjacentCell.addAll(adjacentCell(map, cell, true));
            }
            for (Cell cellAdj : adjacentCell) {
                if (!accessibleCell.contains(cellAdj)) {
                    accessibleCell.add(cellAdj);
                }
            }
        }
        ArrayList<Cell> cellsToDelete = new ArrayList<>();
        for (Cell cell : accessibleCell
        ) {
            if (cell.getOwner() != null && cell.getOwner().equals(owner)) cellsToDelete.add(cell);
        }
        accessibleCell.removeAll(cellsToDelete);
        return accessibleCell;
    }

    /** creer une Capital apres un deploy qui cree un nouveau territoire
     * @param map
     * @param player
     * @return La Cell ou la Capital a ete cree
     */
    public Cell createConqueratorCapital(Map map, Player player){
        Territory territory = new Territory(new ArrayList<>());
        territory.addCell(this);
        ArrayList<Cell> cellForCapital = adjacentCell(map,this,false);

        for (Cell cell: cellForCapital
                ) {
            if(cell.getElementOn()== null){
                cell.setElementOn(new Capital(player,10));
                cell.setOwner(player);
                territory.addCell(cell);
                player.getTerritories().add(territory);
                return cell;
            }
        }
        setElementOn(new Capital(player,10));
        player.getTerritories().add(territory);
        return this;
    }

    /**
     * @param map
     * @param cell Cell dont on veut voir les Cells adjacentes
     * @param water boolen qui defini si la methode doit retourner des Cells d'eau ou de terre
     * @return Une ArrayList qui contient toutes les Cells adjacentes a la cell mise en parametre
     */
    public ArrayList<Cell> adjacentCell(Map map, Cell cell, boolean water) {
        ArrayList<Cell> adjacentCell = new ArrayList<>();

        int x = cell.getX();
        int y = cell.getY();
        int p = (map.getHeight() % 2 == y % 2) ? 0 : 1;

        addCell(adjacentCell, map.findCell(x - p, y - 1), water);
        addCell(adjacentCell, map.findCell(x + 1 - p, y - 1), water);
        addCell(adjacentCell, map.findCell(x - 1, y), water);
        addCell(adjacentCell, map.findCell(x + 1, y), water);
        addCell(adjacentCell, map.findCell(x, y + 1), water);
        addCell(adjacentCell, map.findCell(x + 1 - (2 * p), y + 1), water);

        return adjacentCell;
    }

    private void addCell(ArrayList<Cell> cells, Cell cell, boolean water) {
        if (!water && cell != null && (!cell.isWater() || cell.getElementOn() instanceof Boat)) cells.add(cell);
        if (water && cell != null && cell.isWater()) cells.add(cell);
    }

    /** cree un Territory a partir d'une Cell
     * @param map
     * @param mark boolean qui represente l'attribut checked initial de la Cell qui lance la methode
     * @param territory
     * @return le Territory cree
     */
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
            return "Cell[x][y][owner][checked][elementOn] : " + "[" + x + "]" + "[" + y + "]" + "[" + owner + "]" + "[" + checked + "]" + "[" + elementOn + "]\n";
        else
            return "Cell[x][y][owner][checked] : " + "[" + x + "]" + "[" + y + "]" + "[" + owner + "]" + "[" + checked + "]\n";
    }


}
