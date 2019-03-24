package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;

import java.util.ArrayList;
import java.util.Random;

public class Soldier extends MapElement implements Controlable {
    private int level;

    public Soldier(Player owner, int level, boolean hasMoved) {
        super(owner);
        this.level = level;
        this.setHasMoved(hasMoved);
        switch (level) {
            case 0:
                this.creationCost = 10;
                this.maintenanceCost = 2;
                break;
            case 1:
                this.creationCost = 20;
                this.maintenanceCost = 5;
                break;
            case 2:
                this.creationCost = 40;
                this.maintenanceCost = 14;
                break;
            case 3:
                this.creationCost = 80;
                this.maintenanceCost = 41;
                break;
        }
    }

    private boolean attack(Cell source, Cell destination) {
        int sourceLevel = source.getElementOn().getLevel();
        int destinationLevel = destination.getElementOn().getLevel();


        if (sourceLevel != 3) {
            if (sourceLevel > destinationLevel) {
                destination.setElementOn(null);
                return true;
            }
        } else if (destinationLevel == 3) {
            int rand = new Random().nextInt(2);
            if (rand == 1) {
                destination.setElementOn(null);
                return true;
            } else source.setElementOn(null);

            return false;

        } else {
            destination.setElementOn(null);
            return true;
        }
        return false;
    }

    private boolean mergeSoldier(Cell source, Cell destination) {

        if ((this.level == destination.getElementOn().getLevel()) && this.level != 3) {

            Soldier newSoldier = null;

            switch (this.level) {
                case 0:
                    newSoldier = new Soldier(this.getOwner(), 1, false);
                    break;
                case 1:
                    newSoldier = new Soldier(this.getOwner(), 2, false);
                    break;
                case 2:
                    newSoldier = new Soldier(this.getOwner(), 3, false);
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
        for (Cell cell : cellToTest
        ) {
            if (cell.getOwner() != null && cell.getOwner().equals(oldCell.getOwner())) {
                if (!cell.findTerritory().equals(oldCell.findTerritory())) {
                    if (oldCell.findTerritory().getCells().size() - 1 < cell.findTerritory().getCells().size()) {
                        cell.findTerritory().findCapital().addMoney(oldCell.findTerritory().removeCapital(map));
                    } else oldCell.findTerritory().findCapital().addMoney(cell.findTerritory().removeCapital(map));
                    Territory territoryToDelete = cell.findTerritory();
                    ArrayList<Cell> territory = new ArrayList<>(territoryToDelete.getCells());
                    oldCell.findTerritory().getCells().addAll(territory);
                    cell.getOwner().removeTerritory(territoryToDelete);
                    for (Cell resetCell : cell.findTerritory().getCells()
                    ) {
                        resetCell.setChecked(true);
                    }
                }
            }
        }

    }

    private void splitTerritory(Map map, Cell newCell) {
        ArrayList<Cell> cellToTest = newCell.adjacentCell(map, newCell);
        Cell cellAlreadyChecked = null;
        Cell oldCellMark = null;
        Cell oldCellMarkFirst = null;
        boolean twoNewCapital = false;
        int oldTerritoryCell = 0;
        for (Cell cellMark : cellToTest) {
            if (cellMark.getOwner() != null && !cellMark.getOwner().equals(newCell.getOwner())) {
                Territory territoryMark = new Territory(new ArrayList<>());
                territoryMark = cellMark.createTerritory(map, cellMark.isChecked(), territoryMark);
                ArrayList<Cell> lastCellToTest = newCell.adjacentCell(map, newCell);
                lastCellToTest.remove(cellMark);
                if (cellAlreadyChecked != null) {
                    lastCellToTest.remove(oldCellMark);
                    oldCellMarkFirst = oldCellMark;
                }
                oldCellMark = cellMark;
                for (Cell cell : lastCellToTest) {
                    if (cell.getOwner() != null && cell.getOwner().equals(cellMark.getOwner())) {
                        Territory territory = new Territory(new ArrayList<>());
                        territory = cell.createTerritory(map, cell.isChecked(), territory);
                        if (!territoryMark.equals(territory)) {
                            int cellTot = cellMark.findTerritory().getCells().size();
                            if (cellAlreadyChecked == null) oldTerritoryCell = cellTot;
                            cellMark.getOwner().removeTerritory(cellMark.findTerritory());
                            if (!cellMark.getOwner().getTerritories().contains(territory))
                                cellMark.getOwner().getTerritories().add(territory);
                            cellMark.getOwner().getTerritories().add(territoryMark);
                            if (cellAlreadyChecked == null) {
                                if (cellMark.findTerritory().findCapital() != null) {
                                    cell.findTerritory().placeCapital();
                                    cellMark.findTerritory().findCapital().splitMoney(cell.findTerritory().findCapital(), cellTot, cellMark.findTerritory().getCells().size(), cell.findTerritory().getCells().size());

                                } else if (cell.findTerritory().findCapital() != null) {
                                    cellMark.findTerritory().placeCapital();
                                    cell.findTerritory().findCapital().splitMoney(cellMark.findTerritory().findCapital(), cellTot, cell.findTerritory().getCells().size(), cellMark.findTerritory().getCells().size());
                                } else {
                                    twoNewCapital = true;
                                    cellMark.findTerritory().placeCapital();
                                    cell.findTerritory().placeCapital();
                                }
                            } else {
                                if (!twoNewCapital) {
                                    cell.findTerritory().placeCapital();
                                    cell.findTerritory().findCapital().addMoneyThirdCapital(cellAlreadyChecked.findTerritory().findCapital(), oldCellMarkFirst.findTerritory().findCapital());

                                } else {
                                    cell.findTerritory().findCapital().splitMoney(oldCellMark.findTerritory().findCapital(), oldTerritoryCell, cell.findTerritory().getCells().size(), oldCellMark.findTerritory().getCells().size());
                                    oldCellMarkFirst.findTerritory().findCapital().addMoneyThirdCapital(cell.findTerritory().findCapital(), oldCellMark.findTerritory().findCapital());

                                }
                            }
                            if (cellAlreadyChecked != null) return;
                            cellAlreadyChecked = cell;
                            break;
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
    public void levelUp() {
        this.level++;
        switch (this.getLevel()) {
            case 1:
                this.creationCost = 20;
                this.maintenanceCost = 5;
                break;
            case 2:
                this.creationCost = 40;
                this.maintenanceCost = 14;
                break;
            case 3:
                this.creationCost = 80;
                this.maintenanceCost = 41;
                break;
            default:
                this.creationCost = 80;
                this.maintenanceCost = 41;
                this.level--;
                break;
        }
    }

    @Override
    public boolean belongsTo() {
        return getOwner().isTurn();
    }

    @Override
    public void move(Cell source, Cell destination, Map map) {
        if (source.accessibleCell(map).contains(destination) && (source.getElementOn().getLevel() >= levelDefender(map, source, destination))) {
            if (destination.getElementOn() != null) {
                if (destination.getElementOn().getOwner() == null) {
                    if (destination.getElementOn() instanceof Tree && destination.getOwner() != null && destination.getOwner().equals(source.getOwner())) {
                        source.findTerritory().findCapital().addMoney(3);
                    }
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
                } else if (destination.getElementOn() instanceof Capital && !destination.getOwner().equals(source.getOwner())) {
                    destroyCapital(destination, source);
                    move(source, destination, map);
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

    private int levelDefender(Map map, Cell source, Cell destination) {
        ArrayList<Cell> adjacentCell = destination.adjacentCell(map, destination);
        int levelDefender = -1;
        Player player = source.getOwner();
        for (Cell defenderCell : adjacentCell) {
            if ((defenderCell.getElementOn() instanceof Soldier || defenderCell.getElementOn() instanceof Capital) && defenderCell.getElementOn().getOwner() != player) {
                if (levelDefender == -1) {
                    levelDefender = defenderCell.getElementOn().getLevel();
                } else if (levelDefender < defenderCell.getElementOn().getLevel()) {
                    levelDefender = defenderCell.getElementOn().getLevel();
                }
            }
        }
        return levelDefender;
    }

    private void destroyCapital(Cell cellCapital, Cell attacker) {
        int steal = (cellCapital.findTerritory().gain() - cellCapital.findTerritory().cost()) / 2;
        int money = cellCapital.findTerritory().findCapital().getMoney();
        attacker.findTerritory().findCapital().addMoney(steal);
        cellCapital.findTerritory().placeCapital();
        cellCapital.setElementOn(null);
        try {
            cellCapital.findTerritory().findCapital().setMoney(money - steal);

        }catch (NullPointerException e){

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

    @Override
    public String toString() {
        return super.toString() + " level : " + level + " owner : " + getOwner();
    }
}
