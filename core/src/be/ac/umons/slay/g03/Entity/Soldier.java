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

    private boolean mergeSoldier(Cell source, Cell destination) {

        if ((this.level == destination.getElementOn().getLevel()) && this.level != 3) {

            Soldier newSoldier = null;

            switch (this.level) {
                case 0:
                    newSoldier = new Soldier(5, 20, this.getOwner(), 1, false);
                    break;
                case 1:
                    newSoldier = new Soldier(14, 40, this.getOwner(), 2, false);
                    break;
                case 2:
                    newSoldier = new Soldier(41, 80, this.getOwner(), 3, false);
                    break;
            }
            destination.setElementOn(newSoldier);
            source.setElementOn(null);
            return true;
        }
        return false;
    }

    @Override
    protected void checkNewTerritory(Map map, Cell newCell, Cell oldCell) {
        Territory oldTerritoryCell = newCell.findTerritory();
        oldCell.findTerritory().addCell(newCell);
        mergeTerritory(map, newCell, oldCell);
        if (newCell.getOwner() != null && !newCell.equals(oldCell.getOwner())) {
            oldTerritoryCell.getCells().remove(newCell);
            newCell.setOwner(oldCell.getOwner());
            splitTerritory(map, newCell);
        }

    }

    private void mergeTerritory(Map map, Cell newCell, Cell oldCell) {
        ArrayList<Cell> cellToTest = newCell.adjacentCell(map, newCell);
        cellToTest.remove(oldCell);
        for (Cell cell : cellToTest) {
            if (cell.getOwner() != null && cell.getOwner().equals(oldCell.getOwner())) {
                if (!cell.findTerritory().equals(oldCell.findTerritory())) {
                    Territory territoryToDelete = cell.findTerritory();
                    ArrayList<Cell> territory = new ArrayList<>(cell.findTerritory().getCells());
                    oldCell.findTerritory().getCells().addAll(territory);
                    cell.getOwner().removeTerritory(territoryToDelete);
                    for (Cell resetCell : cell.findTerritory().getCells()) {
                        resetCell.setChecked(true);
                    }
                }
            }
        }
    }

    private void splitTerritory(Map map, Cell newCell) {
        ArrayList<Cell> cellToTest = newCell.adjacentCell(map, newCell);
        for (Cell cellMark : cellToTest) {
            if (cellMark.getOwner() != null && !cellMark.getOwner().equals(newCell.getOwner())) {
                Territory territoryMark = new Territory(new ArrayList<>());
                territoryMark = cellMark.createTerritory(map, cellMark.isChecked(), territoryMark);
                ArrayList<Cell> lastCellToTest = newCell.adjacentCell(map, newCell);
                lastCellToTest.remove(cellMark);
                for (Cell cell : lastCellToTest) {
                    if (cell.getOwner() != null && cell.getOwner().equals(cellMark.getOwner())) {
                        Territory territory = new Territory(new ArrayList<>());
                        territory = cell.createTerritory(map, cell.isChecked(), territory);
                        if (!territoryMark.equals(territory)) {
                            cellMark.getOwner().removeTerritory(cellMark.findTerritory());
                            cellMark.getOwner().getTerritories().add(territory);
                            cellMark.getOwner().getTerritories().add(territoryMark);
                            return;
                        }
                    }
                }
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
                        mergeSoldier(source, destination);
                    } else {
                        if (attack(source, destination)) {
                            move(source, destination, map);
                        }

                    }
                }

            } else {
                source.getElementOn().checkNewTerritory(map, destination, source);
                destination.setOwner(getOwner());
                destination.setElementOn(source.getElementOn());
                source.setElementOn(null);
                destination.getElementOn().setHasMoved(true);
                destination.setChecked(source.isChecked());
            }
        }
    }


    @Override
    public boolean select() {
        return belongsTo() && !isHasMoved();

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
