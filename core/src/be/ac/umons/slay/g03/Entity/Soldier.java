package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import java.util.ArrayList;

public class Soldier extends MapElement implements Controlable {
    private int level;
    private boolean hasMoved;
    private ArrayList<Cell> accessibleCells;

    public Soldier(int maintenanceCost, int creationCost, Player owner, int level, boolean hasMoved) {
        super(maintenanceCost, creationCost, owner);
        this.level = level;
        this.hasMoved = hasMoved;
    }

    public boolean attack(Cell cellAttacker, Cell cellDefender) {
        int levelAttacker = cellAttacker.getElementOn().getLevel();
        int levelDefender = cellDefender.getElementOn().getLevel();


        if (levelAttacker != 3) {
            if (levelAttacker > levelDefender) {
                cellDefender.setElementOn(null);
                return true;
            }
        } else if (levelDefender == 3) {
            cellAttacker.setElementOn(null);
            cellDefender.setElementOn(null);

            return false;

        } else {
            cellDefender.setElementOn(null);
            return true;
        }
        return false;
    }

    public boolean merge(Cell himself,Cell allySoldier) {

        if ((this.level == allySoldier.getElementOn().getLevel()) && this.level != 3){

            Soldier upSoldier = null;

            switch (this.level){
                case 0: upSoldier = new Soldier(5,20,this.getOwner(),1,true); break;
                case 1: upSoldier = new Soldier(14,40,this.getOwner(),2,true); break;
                case 2: upSoldier = new Soldier(41,80,this.getOwner(),3,true); break;
            }

            allySoldier.setElementOn(upSoldier);
            himself.setElementOn(null);
            return true;
        }

        return false;

    }

    private void checkNewTerritory() {

    }

    @Override
    public int getLevel() {
        return level;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }


    @Override
    public boolean belongsTo() {
        return getOwner().isTurn();
    }

    @Override
    public void move(Cell source, Cell destination) {
        if (destination.getElementOn() == null && accessibleCells.contains(destination)) {
            destination.setElementOn(source.getElementOn());
            source.setElementOn(null);
            destination.setOwner(getOwner());
        }
        if (destination.findTerritory(source.getOwner()) == null) {
            source.findTerritory(source.getOwner()).addCell(destination);
        }

    }
    @Override
    public ArrayList<Cell> accessibleCell(Map map, Cell himself) {
        ArrayList<Cell> accessibleCell = new ArrayList<>();
        ArrayList<Cell> adjacentCell = new ArrayList<>();
        int dist = 3;
        accessibleCell.addAll(adjacentCell(himself, map));
        while( dist > 0){
            for (Cell cell: accessibleCell) {
                if(cell.getOwner()!=null && cell.getOwner().equals(himself.getOwner())){
                    adjacentCell.addAll(adjacentCell(cell, map));
                }
            }
            for (Cell cellAdj : adjacentCell) {
                if (!accessibleCell.contains(cellAdj)){
                    accessibleCell.add(cellAdj);
                }
            }
            dist--;
        }
        return accessibleCell;
    }

    private ArrayList<Cell> adjacentCell(Cell himself, Map map){
        ArrayList<Cell> adjacentCell = new ArrayList<>();

        int x = himself.getX();
        int y = himself.getY();
        if (map.getHeigth()%2 ==  y%2) {

            addCell(adjacentCell, map.findCell(x,y-1));
            addCell(adjacentCell,map.findCell(x+1,y-1));
            addCell(adjacentCell,map.findCell(x-1,y));
            addCell(adjacentCell,map.findCell(x+1,y));
            addCell(adjacentCell,map.findCell(x,y+1));
            addCell(adjacentCell,map.findCell(x+1,y+1));

        }
        else {

            addCell(adjacentCell,map.findCell(x-1,y-1));
            addCell(adjacentCell,map.findCell(x,y-1));
            addCell(adjacentCell,map.findCell(x-1,y));
            addCell(adjacentCell,map.findCell(x+1,y));
            addCell(adjacentCell,map.findCell(x-1,y+1));
            addCell(adjacentCell,map.findCell(x,y+1));


        }
        return adjacentCell;
    }

    private void addCell(ArrayList<Cell> aList,Cell cell){
        if (cell != null && !cell.isWater()){
            aList.add(cell);
        }

    }



    @Override
    public void select(Map map, Cell source) {
        if (belongsTo() && !isHasMoved()) {
            this.accessibleCells = accessibleCell(map, source);
        }
    }

    public ArrayList<Cell> getAccessibleCells() {
        return accessibleCells;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Soldier)) {
            return false;
        }
        Soldier that = (Soldier) other;
        return this.getMaintenanceCost() == that.getMaintenanceCost()
                && this.getCreationCost() == that.getCreationCost()
                && this.getLevel() == that.getLevel()
                && this.getOwner().equals(that.getOwner())
                && this.isHasMoved() == that.isHasMoved();
    }
}
