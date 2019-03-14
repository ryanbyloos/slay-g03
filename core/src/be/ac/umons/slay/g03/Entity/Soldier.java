package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;

import java.util.ArrayList;

public class Soldier extends MapElement implements Controlable {
    private int level;


    public Soldier(int maintenanceCost, int creationCost, Player owner, int level, boolean hasMoved) {
        super(maintenanceCost, creationCost, owner);
        this.level = level;
        this.setHasMoved(hasMoved);
    }

    private boolean attack(Cell cellAttacker, Cell cellDefender) {
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

    private boolean merge(Cell himself, Cell allySoldier) {

        if ((this.level == allySoldier.getElementOn().getLevel()) && this.level != 3) {

            Soldier upSoldier = null;

            switch (this.level) {
                case 0:
                    upSoldier = new Soldier(5, 20, this.getOwner(), 1, false);
                    break;
                case 1:
                    upSoldier = new Soldier(14, 40, this.getOwner(), 2, false);
                    break;
                case 2:
                    upSoldier = new Soldier(41, 80, this.getOwner(), 3, false);
                    break;
            }

            allySoldier.setElementOn(upSoldier);
            himself.setElementOn(null);
            return true;
        }

        return false;

    }

    @Override
    protected void checkNewTerritory(Map map, Cell newCell, Cell oldCell) {
        newCell.setOwner(getOwner());
        oldCell.findTerritory(oldCell.getOwner()).addCell(newCell);
        Merge(map, newCell, oldCell);
        if (newCell.getOwner() != null && !newCell.getOwner().equals(oldCell.getOwner())) {
            split(map, newCell, oldCell);
        }

    }

    private void Merge(Map map, Cell newCell, Cell oldCell) {
        ArrayList<Cell> cellToTest = newCell.adjacentCell(map, newCell);
        cellToTest.remove(oldCell);
        for (Cell cell : cellToTest
        ) {
            if (cell.getOwner() != null && cell.getOwner().equals(newCell.getOwner())) {
                if (!cell.findTerritory(cell.getOwner()).equals(newCell.findTerritory(cell.getOwner()))) {
                    Territory territoryToDelete = cell.findTerritory(cell.getOwner());
                    ArrayList<Cell> territory = new ArrayList<>();
                    territory.addAll(cell.findTerritory(cell.getOwner()).getCells());
                    newCell.findTerritory(newCell.getOwner()).getCells().addAll(territory);
                    cell.getOwner().removeTerritory(territoryToDelete);
                }
            }
        }

    }

    private void split(Map map, Cell newCell, Cell oldCell) {
        ArrayList<Cell> cellToTest = newCell.adjacentCell(map, newCell);
        for (Cell cell : cellToTest
        ) {
            if (!cell.getOwner().equals(oldCell.getOwner())) {

            }
        }
    }

    @Override
    public int getLevel() {
        return level;
    }


    @Override
    public boolean belongsTo() {
        return getOwner().isTurn();
    }

    @Override
    public void move(Cell source, Cell destination, Map map) {
        if (source.accessibleCell(map).contains(destination)) {

            if (destination.getElementOn() != null) {
                if (destination.getElementOn().getOwner() == null) {

                    destination.setElementOn(null);
                    move(source, destination, map);

                } else if (destination.getElementOn() instanceof Soldier) {
                    if (destination.getElementOn().getOwner().equals(this.getOwner())) {
                        merge(source, destination);
                    } else {
                        if (attack(source, destination)) {
                            move(source, destination, map);
                        }

                    }
                }

            } else {
                source.getElementOn().checkNewTerritory(map, destination, source);
                destination.setElementOn(source.getElementOn());
                source.setElementOn(null);
                destination.getElementOn().setHasMoved(true);
            }
        }
    }


    @Override
    public boolean select() {
        if (belongsTo() && !isHasMoved()) return true;

        return false;
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
