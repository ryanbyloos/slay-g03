import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class EntityTest {

    @Test
    public void towerAttack() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<>());
        Cell c = new Cell(0, 0, false, false, player0, new Soldier(player0, 3));
        AttackTower attackTower = new AttackTower(player1, 3);
        attackTower.attack(c);
        Assert.assertNull(c.getElementOn());
    }

    @Test
    public void boatCapture() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player0);
        Soldier soldier = new Soldier(player1, 1);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier);
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        ArrayList<Cell> territory = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player0, player1);
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        soldier.move(cell1,cell2,map);
        Assert.assertEquals(boat.getOwner(), player1);
        Assert.assertEquals(cell1.getElementOn(), null);
        Assert.assertTrue(boat.getSoldiers().contains(soldier));
        Assert.assertEquals(1, boat.getDefence());
    }
    @Test
    public void boatCaptureHight() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player0);
        Soldier soldier1 = new Soldier(player0,2);
        boat.bord(soldier1);
        Soldier soldier = new Soldier(player1, 1);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier);
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        ArrayList<Cell> territory = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player0, player1);
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        soldier.move(cell1,cell2,map);
        Assert.assertEquals(boat.getOwner(), player0);
        Assert.assertEquals(cell1.getElementOn(), soldier);
        Assert.assertFalse(boat.getSoldiers().contains(soldier));
        Assert.assertTrue(boat.getSoldiers().contains(soldier1));
        Assert.assertEquals(2, boat.getDefence());
    }
    @Test
    public void boatMove(){
        Player player = new Player("palyer", 1, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player);
        Cell cell1 = new Cell(0, 0, false, true, null, null);
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player, null);
        boat.move(cell2,cell1,map);
        Assert.assertEquals(cell1.getElementOn(), boat);
        Assert.assertEquals(cell2.getElementOn(), null);
    }
    @Test
    public void boatToMine() {
        Player player = new Player("palyer", 1, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player);
        Cell cell1 = new Cell(0, 0, false, true, null, new Mine(player));
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player, null);
        boat.move(cell2,cell1,map);
        Assert.assertEquals(cell1.getElementOn(), null);
        Assert.assertEquals(cell2.getElementOn(), null);
    }
    @Test
    public void boatBoarding() {
        Player player = new Player("palyer", 1, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player);
        Soldier soldier = new Soldier(player, 1);
        Cell cell1 = new Cell(0, 0, false, false, player, soldier);
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        ArrayList<Cell> territory = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player, null);
        territory.add(cell1);
        player.getTerritories().add(new Territory(territory));
        soldier.move(cell1,cell2,map);
        Assert.assertEquals(cell1.getElementOn(), null);
        Assert.assertTrue(boat.getSoldiers().contains(soldier));
        Assert.assertEquals(1, boat.getDefence());
    }

    @Test
    public void boardDeploy(){
        Player player = new Player("palyer", 1, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(player);
        Soldier soldier = new Soldier(player, 1);
        boat.bord(soldier);
        Cell cell1 = new Cell(0, 0, false, false, null, null);
        Cell cell2 = new Cell(1, 0, false, true, null, boat);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player, null);
        boat.deploy(cell2,cell1,map);
        Assert.assertEquals(cell1.getElementOn(), soldier);
        Assert.assertTrue(!boat.getSoldiers().contains(soldier));
        Assert.assertEquals(boat, cell2.getElementOn());
        Assert.assertTrue(cell1.getOwner().equals(player));
    }

    @Test
    public void simpleMove() {

        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 1);
        Cell cell1 = new Cell(0, 0, false, false, player, soldier);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player.getTerritories().add(new Territory(territory));
        Map map = new Map(mapCell, player, null);
        soldier.move(cell1, cell2, map);
        Assert.assertNull(cell1.getElementOn());
        Assert.assertEquals(soldier, cell2.getElementOn());
        Assert.assertEquals(true, cell2.getElementOn().isHasMoved());


    }

    @Test
    public void attackNullOwnerElement() {
        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 1);
        Cell cell1 = new Cell(0, 0, false, false, player, soldier);
        Cell cell2 = new Cell(1, 0, false, false, null, new Grave(0));
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player.getTerritories().add(new Territory(territory));
        Map map = new Map(mapCell, null, null);
        soldier.move(cell1, cell2, map);
        Assert.assertEquals(soldier, cell2.getElementOn());
        Assert.assertNull(cell1.getElementOn());

    }

    @Test
    public void attackLowerSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldierLow = new Soldier(player1, 1);
        Soldier soldierHigh = new Soldier(player2, 2);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldierLow);
        Cell cell2 = new Cell(1, 0, false, false, player2, soldierHigh);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory2.add(cell2);
        player2.getTerritories().add(new Territory(territory2));
        Map map = new Map(mapCell, player1, player2);
        soldierHigh.move(cell2, cell1, map);
        Assert.assertEquals(soldierHigh, cell1.getElementOn());
        Assert.assertNotEquals(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackHigherSoldier() {
        Soldier soldierLow = new Soldier(new Player("player1", 0, 0, false, 0, new ArrayList<>()), 1);
        Soldier soldierHigh = new Soldier(new Player("player2", 1, 0, false, 0, new ArrayList<>()), 2);
        Cell cell1 = new Cell(0, 0, false, false, null, soldierLow);
        Cell cell2 = new Cell(1, 0, false, false, null, soldierHigh);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldierLow.move(cell1, cell2, map);
        Assert.assertEquals(soldierLow, cell1.getElementOn());
        Assert.assertEquals(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackEqualSoldier() {
        Soldier soldier1 = new Soldier(new Player("player1", 0, 0, false, 0, new ArrayList<>()), 1);
        Soldier soldier2 = new Soldier(new Player("player2", 1, 0, false, 0, new ArrayList<>()), 1);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());
    }

    @Test
    public void attackEqualHighSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 3);
        Soldier soldier2 = new Soldier(player2, 3);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, player2, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory1 = new ArrayList<>();
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory1.add(cell1);
        territory2.add(cell2);
        player1.getTerritories().add(new Territory(territory1));
        player2.getTerritories().add(new Territory(territory2));
        soldier1.move(cell1, cell2, map);
        Assert.assertTrue(cell1.getElementOn() == null ^ cell2.getElementOn() == null);

    }
    @Test
    public void moveToLowerSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldierLow = new Soldier(player1, 1);
        Soldier soldierHigh = new Soldier(player2, 2);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldierLow);
        Cell cell3 = new Cell(2, 0, false, false, player2, soldierHigh);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory2.add(cell3);
        player2.getTerritories().add(new Territory(territory2));
        Map map = new Map(mapCell, player1, player2);
        soldierHigh.move(cell3, cell2, map);
        Assert.assertEquals(soldierHigh, cell2.getElementOn());
        Assert.assertEquals(soldierLow, cell1.getElementOn());
        Assert.assertNull( cell3.getElementOn());

    }

    @Test
    public void movToHigherSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldierLow = new Soldier(player1, 1);
        Soldier soldierHigh = new Soldier(player2, 2);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldierLow);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        Cell cell3 = new Cell(2, 0, false, false, player2, soldierHigh);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory2.add(cell3);
        player2.getTerritories().add(new Territory(territory2));
        soldierLow.move(cell1, cell2, map);
        Assert.assertEquals(soldierLow, cell1.getElementOn());
        Assert.assertEquals(soldierHigh, cell3.getElementOn());
        Assert.assertNull( cell2.getElementOn());

    }

    @Test
    public void moveNextToEqualSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 1);
        Soldier soldier2 = new Soldier(player2, 1);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        Cell cell3 = new Cell(2, 0, false, false, player2, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory2.add(cell3);
        player2.getTerritories().add(new Territory(territory2));
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell2.getElementOn());
        Assert.assertEquals(soldier2, cell3.getElementOn());
        Assert.assertNull(cell1.getElementOn());

    }

    @Test
    public void moveToEqualHighSoldier() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 3);
        Soldier soldier2 = new Soldier(player2, 3);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        Cell cell3 = new Cell(2, 0, false, false, player2, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory1 = new ArrayList<>();
        ArrayList<Cell> territory2 = new ArrayList<>();
        territory1.add(cell1);
        territory2.add(cell3);
        player1.getTerritories().add(new Territory(territory1));
        player2.getTerritories().add(new Territory(territory2));
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell2.getElementOn());
        Assert.assertEquals(soldier2, cell3.getElementOn());
        Assert.assertNull( cell1.getElementOn());

    }
    @Test
    public void mergesoldierSameLevel() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 2);
        Soldier soldier2 = new Soldier(player1, 2);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, player1, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player1, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(3, cell2.getElementOn().getLevel());
        Assert.assertNull(cell1.getElementOn());
    }

    @Test
    public void mergesoldierMaxLevel() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 3);
        Soldier soldier2 = new Soldier(player1, 3);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, player1, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player1, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());

    }

    @Test
    public void mergesoldierDiffLevel() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier1 = new Soldier(player1, 1);
        Soldier soldier2 = new Soldier(player1, 2);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, player1, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, player1, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());

    }

    @Test
    public void simpleAccessibleCell() {
        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 1);
        Cell cell = new Cell(0, 2, false, false, player, soldier);
        Cell cell1 = new Cell(0, 3, false, false, null, null);
        Cell cell2 = new Cell(1, 3, false, false, null, null);
        Cell cell3 = new Cell(1, 2, false, false, null, null);
        Cell cell4 = new Cell(1, 1, false, false, null, null);
        Cell cell5 = new Cell(0, 2, false, true, null, null);
        Cell cell6 = new Cell(0, 4, false, true, null, null);
        Cell cell7 = new Cell(2, 2, false, false, null, null);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell);
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(cell5);
        mapCell.add(cell6);
        mapCell.add(cell7);
        Map map = new Map(mapCell, null, null);
        ArrayList<Cell> accCell = cell.accessibleCell(map);
        Assert.assertEquals(true, accCell.contains(cell1));
        Assert.assertEquals(true, accCell.contains(cell2));
        Assert.assertEquals(true, accCell.contains(cell3));
        Assert.assertEquals(true, accCell.contains(cell4));
        Assert.assertEquals(false, accCell.contains(cell5));
        Assert.assertEquals(false, accCell.contains(cell6));
        Assert.assertEquals(false, accCell.contains(cell7));
    }

    @Test
    public void complexeAccessibleCell() {
        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 1);
        Cell cell = new Cell(0, 2, false, false, player, soldier);
        Cell cell1 = new Cell(0, 3, false, false, null, null);
        Cell cell2 = new Cell(1, 3, false, false, player, null);
        Cell cell3 = new Cell(1, 2, false, false, null, null);
        Cell cell4 = new Cell(1, 1, false, false, null, null);
        Cell cell5 = new Cell(0, 2, false, true, null, null);
        Cell cell6 = new Cell(0, 4, false, false, null, null);
        Cell cell7 = new Cell(2, 2, false, false, null, null);
        Cell cell8 = new Cell(5, 3, false, false, player, null);
        Cell cell9 = new Cell(2, 3, false, false, player, null);
        Cell cell10 = new Cell(3, 3, false, false, player, null);
        Cell cell11 = new Cell(4, 3, false, false, player, null);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell);
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(cell5);
        mapCell.add(cell6);
        mapCell.add(cell7);
        mapCell.add(cell8);
        mapCell.add(cell9);
        mapCell.add(cell10);
        mapCell.add(cell11);
        Map map = new Map(mapCell, null, null);
        ArrayList<Cell> accCell = cell.accessibleCell(map);
        Assert.assertEquals(true, accCell.contains(cell1));
        Assert.assertEquals(true, accCell.contains(cell2));
        Assert.assertEquals(true, accCell.contains(cell3));
        Assert.assertEquals(true, accCell.contains(cell4));
        Assert.assertEquals(false, accCell.contains(cell5));
        Assert.assertEquals(true, accCell.contains(cell6));
        Assert.assertEquals(true, accCell.contains(cell7));
        Assert.assertEquals(false, accCell.contains(cell8));
        Assert.assertEquals(true, accCell.contains(cell9));
        Assert.assertEquals(true, accCell.contains(cell10));
        Assert.assertEquals(true, accCell.contains(cell11));

    }

    @Test
    public void mergeTerritoryTest() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Capital capital1 = new Capital(player1,10);
        Capital capital2 = new Capital(player1,10);
        Cell cell1 = new Cell(6, 6, false, false, player1, soldier);
        Cell cell2 = new Cell(5, 6, false, false, player1,  capital1);
        Cell cell3 = new Cell(7, 6, false, false, player1, null);
        Cell cell4 = new Cell(6, 8, false, false, player1, null);
        Cell cell5 = new Cell(5, 8, false, false, player1, null);
        Cell cell6 = new Cell(7, 8, false, false, player1, capital2);
        Cell dest = new Cell(6, 7, false, false, null, null);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(cell5);
        mapCell.add(cell6);
        mapCell.add(dest);
        Map map = new Map(mapCell, player1, null);
        ArrayList<Cell> territory = new ArrayList<Cell>();
        ArrayList<Cell> territory1 = new ArrayList<Cell>();
        territory.add(cell1);
        territory.add(cell2);
        territory.add(cell3);
        territory1.add(cell4);
        territory1.add(cell5);
        territory1.add(cell6);
        player1.getTerritories().add(new Territory(territory));
        player1.getTerritories().add(new Territory(territory1));
        soldier.move(cell1, dest, map);
        Assert.assertEquals(1, player1.getTerritories().size());
        Assert.assertEquals(7, player1.getTerritories().get(0).getCells().size());
        Assert.assertEquals(cell6.getElementOn(),null);
        Assert.assertEquals(cell2.getElementOn(),capital1);
        Assert.assertEquals(20,capital1.getMoney());
    }

    @Test
    public void doubleSplitTerritoryTest() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Capital capital = new Capital(player2,13);
        Cell cell1 = new Cell(6, 6, false, false, player1, null);
        Cell cell2 = new Cell(5, 6, false, false, player1, null);
        Cell cell3 = new Cell(7, 6, false, false, player1, null);
        Cell cell4 = new Cell(6, 7, false, false, player1, soldier);
        Cell cell5 = new Cell(6, 8, false, false, player2, null);
        Cell cell6 = new Cell(5, 8, false, false, player2, capital);
        Cell cell7 = new Cell(7, 8, false, false, player2, null);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(cell5);
        mapCell.add(cell6);
        mapCell.add(cell7);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<Cell>();
        ArrayList<Cell> territory1 = new ArrayList<Cell>();
        territory.add(cell1);
        territory.add(cell2);
        territory.add(cell3);
        territory.add(cell4);
        territory1.add(cell7);
        territory1.add(cell5);
        territory1.add(cell6);
        player1.getTerritories().add(new Territory(territory));
        player2.getTerritories().add(new Territory(territory1));
        soldier.move(cell4, cell5, map);
        Assert.assertEquals(2, player2.getTerritories().size());
        Assert.assertEquals(1, player2.getTerritories().get(0).getCells().size());
        Assert.assertFalse(territory1.contains(cell5));
        Assert.assertEquals(capital.getMoney(),7);
    }
    @Test
    public void tripleSplitTerritoryTestCas1() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player2, 1);
        Capital capital = new Capital(player1,13);
        Cell cell1 = new Cell(1, 5, false, false, player1, capital);
        Cell dest = new Cell(2, 5, false, false, player1, null);
        Cell cell3 = new Cell(2, 6, false, false, player1, null);
        Cell cell4 = new Cell(2, 4, false, false, player1, null);
        Cell source = new Cell(3, 5, false, false, player2, soldier);
        Cell cell5 = new Cell(4, 5, false, false, player2, null);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(dest);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(source);
        mapCell.add(cell5);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<>();
        ArrayList<Cell> territory1 = new ArrayList<>();
        territory.add(cell1);
        territory.add(dest);
        territory.add(cell3);
        territory.add(cell4);
        territory1.add(source);
        territory1.add(cell5);
        player1.getTerritories().add(new Territory(territory));
        player2.getTerritories().add(new Territory(territory1));
        soldier.move(source, dest, map);
        Assert.assertEquals(3, player1.getTerritories().size());
        Assert.assertEquals(5,capital.getMoney());
    }
    @Test
    public void tripleSplitTerritoryTestCas2() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player2, 1);
        Capital capital = new Capital(player1,13);
        Cell cell1 = new Cell(1, 5, false, false, player1, null);
        Cell dest = new Cell(2, 5, false, false, player1, null);
        Cell cell3 = new Cell(2, 6, false, false, player1, capital);
        Cell cell4 = new Cell(2, 4, false, false, player1, null);
        Cell source = new Cell(3, 5, false, false, player2, soldier);
        Cell cell5 = new Cell(4, 5, false, false, player2, null);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(dest);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(source);
        mapCell.add(cell5);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<>();
        ArrayList<Cell> territory1 = new ArrayList<>();
        territory.add(cell1);
        territory.add(dest);
        territory.add(cell3);
        territory.add(cell4);
        territory1.add(source);
        territory1.add(cell5);
        player1.getTerritories().add(new Territory(territory));
        player2.getTerritories().add(new Territory(territory1));
        soldier.move(source, dest, map);
        Assert.assertEquals(3, player1.getTerritories().size());
        Assert.assertEquals(5,capital.getMoney());
    }
    @Test
    public void tripleSplitTerritoryTestCas3() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player2, 1);
        Capital capital = new Capital(player1,13);
        Cell cell1 = new Cell(1, 5, false, false, player1, null);
        Cell dest = new Cell(2, 5, false, false, player1, null);
        Cell cell3 = new Cell(2, 6, false, false, player1, null);
        Cell cell4 = new Cell(2, 4, false, false, player1, capital);
        Cell source = new Cell(3, 5, false, false, player2, soldier);
        Cell cell5 = new Cell(4, 5, false, false, player2, null);
        ArrayList<Cell> mapCell = new ArrayList<>();
        mapCell.add(cell1);
        mapCell.add(dest);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(source);
        mapCell.add(cell5);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory = new ArrayList<>();
        ArrayList<Cell> territory1 = new ArrayList<>();
        territory.add(cell1);
        territory.add(dest);
        territory.add(cell3);
        territory.add(cell4);
        territory1.add(source);
        territory1.add(cell5);
        player1.getTerritories().add(new Territory(territory));
        player2.getTerritories().add(new Territory(territory1));
        soldier.move(source, dest, map);
        Assert.assertEquals(3, player1.getTerritories().size());
        Assert.assertEquals(5,capital.getMoney());
    }

    @Test
    public void cutTreeNeutral(){
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Capital capital = new Capital(player1,10);
        Cell cell1 = new Cell(1, 5, false, false, player1, soldier);
        Cell cell2 = new Cell(0,5,false,false,player1,capital);
        Cell cellTree = new Cell(2,5,false,false,null, new Tree());
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cellTree);
        Map map = new Map(mapCell, player1, null );
        ArrayList<Cell> territory = new ArrayList<Cell>();
        territory.add(cell1);
        territory.add(cell2);
        player1.getTerritories().add(new Territory(territory));
        soldier.move(cell1,cellTree,map);
        Assert.assertEquals(10,capital.getMoney());
        Assert.assertEquals(soldier,cellTree.getElementOn());
    }
    @Test
    public void cutTreeAlly(){
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Capital capital = new Capital(player1,10);
        Cell cell1 = new Cell(1, 5, false, false, player1, soldier);
        Cell cell2 = new Cell(0,5,false,false,player1,capital);
        Cell cellTree = new Cell(2,5,false,false,player1, new Tree());
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cellTree);
        Map map = new Map(mapCell, player1, null );
        ArrayList<Cell> territory = new ArrayList<Cell>();
        territory.add(cell1);
        territory.add(cell2);
        territory.add(cellTree);
        player1.getTerritories().add(new Territory(territory));
        soldier.move(cell1,cellTree,map);
        Assert.assertEquals(13,capital.getMoney());
        Assert.assertEquals(soldier,cellTree.getElementOn());
    }
    @Test
    public void cutTreeEnnemy(){
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Capital capital = new Capital(player1,10);
        Cell cell1 = new Cell(1, 5, false, false, player1, soldier);
        Cell cell2 = new Cell(0,5,false,false,player1,capital);
        Cell cellTree = new Cell(2,5,false,false,player2, new Tree());
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cellTree);
        Map map = new Map(mapCell, player1, player2 );
        ArrayList<Cell> territory = new ArrayList<Cell>();
        ArrayList<Cell> territory2 = new ArrayList<Cell>();
        territory.add(cell1);
        territory.add(cell2);
        territory2.add(cellTree);
        player1.getTerritories().add(new Territory(territory));
        player2.getTerritories().add(new Territory(territory2));
        soldier.move(cell1,cellTree,map);
        Assert.assertEquals(10  ,capital.getMoney());
        Assert.assertEquals(soldier,cellTree.getElementOn());
    }

    @Test
    public void splitMoneySimple(){
        Capital capital = new Capital(null,30);
        Capital newCapital = new Capital(null,0);
        capital.splitMoney(newCapital,10,4,6);
        Assert.assertEquals(12,capital.getMoney());
        Assert.assertEquals(18,newCapital.getMoney());
        Assert.assertEquals(30, newCapital.getMoney()+capital.getMoney());
    }

    @Test
    public void splitMoneySimpleHalf(){
        Capital capital = new Capital(null,30);
        Capital newCapital = new Capital(null,0);
        capital.splitMoney(newCapital,10,5,5);
        Assert.assertEquals(15,capital.getMoney());
        Assert.assertEquals(15,newCapital.getMoney());
        Assert.assertEquals(30, newCapital.getMoney()+capital.getMoney());
    }

    @Test
    public void splitMoneyComplex() {
        Capital capital = new Capital(null, 31);
        Capital newCapital = new Capital(null, 0);
        capital.splitMoney(newCapital, 10, 4, 6);
        Assert.assertEquals(12, capital.getMoney());
        Assert.assertEquals(19, newCapital.getMoney());
        Assert.assertEquals(31, newCapital.getMoney() + capital.getMoney());
    }
    @Test
    public void splitMoneyComplexx() {
        Capital capital = new Capital(null, 31);
        Capital newCapital = new Capital(null, 0);
        capital.splitMoney(newCapital, 10, 5, 5);
        Assert.assertEquals(16, capital.getMoney());
        Assert.assertEquals(15, newCapital.getMoney());
        Assert.assertEquals(31, newCapital.getMoney() + capital.getMoney());
    }

    @Test
    public void bankrupt(){
        Player player = new Player("player", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 2);
        Capital capital = new Capital(player,3);
        Cell cellSoldier = new Cell(1, 5, false, false, player, soldier);
        Cell cellCapital = new Cell(0,5,false,false,player,capital);
        Cell cellTree = new Cell(2,5,false,false,player, new Tree());
        Cell cellEmpty = new Cell(3,5,false,false,player,null);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cellCapital);
        territory.add(cellSoldier);
        territory.add(cellTree);
        territory.add(cellEmpty);
        player.getTerritories().add(new Territory(territory));
        player.checkTerritory();
        Assert.assertEquals(6,capital.getMoney());
        Assert.assertTrue(cellSoldier.getElementOn() instanceof Grave);
        Assert.assertTrue(cellCapital.getElementOn() instanceof Capital);
        Assert.assertTrue(cellTree.getElementOn() instanceof Tree);
        Assert.assertNull(cellEmpty.getElementOn());
    }
    @Test
    public void noBankrupt(){
        Player player = new Player("player", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player, 2);
        Capital capital = new Capital(player, 13);
        Cell cellSoldier = new Cell(1, 5, false, false, player, soldier);
        Cell cellCapital = new Cell(0,5,false,false,player,capital);
        Cell cellTree = new Cell(2,5,false,false,player, new Tree());
        Cell cellEmpty = new Cell(3,5,false,false,player,null);
        ArrayList<Cell> territory = new ArrayList<>();
        territory.add(cellCapital);
        territory.add(cellSoldier);
        territory.add(cellTree);
        territory.add(cellEmpty);
        player.getTerritories().add(new Territory(territory));
        player.checkTerritory();
        Assert.assertEquals(2,capital.getMoney());
        Assert.assertTrue(cellSoldier.getElementOn() instanceof Soldier);
        Assert.assertTrue(cellCapital.getElementOn() instanceof Capital);
        Assert.assertTrue(cellTree.getElementOn() instanceof Tree);
        Assert.assertNull(cellEmpty.getElementOn());
    }

    @Test
    public void destroyCapital(){
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 1, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(player1, 1);
        Soldier soldier2 = new Soldier(player2, 0);
        Capital capital = new Capital(player1,20);
        Capital capital2 = new Capital(player2,20);
        Cell cell1 = new Cell(6, 6, false, false, player1, soldier);
        Cell cell2 = new Cell(5, 6, false, false, player1, capital);
        Cell cell3 = new Cell(7, 6, false, false, player2, capital2);
        Cell cell4 = new Cell(8, 6, false, false, player2, null);
        Cell cell5 = new Cell(9, 6, false, false, player2, null);
        Cell cell6 = new Cell(10, 6, false, false, player2, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        mapCell.add(cell3);
        mapCell.add(cell4);
        mapCell.add(cell5);
        mapCell.add(cell6);
        Map map = new Map(mapCell, player1, player2);
        ArrayList<Cell> territory1 = new ArrayList<Cell>();
        ArrayList<Cell> territory2 = new ArrayList<Cell>();
        territory1.add(cell1);
        territory1.add(cell2);
        territory2.add(cell3);
        territory2.add(cell4);
        territory2.add(cell5);
        territory2.add(cell6);
        player1.getTerritories().add(new Territory(territory1));
        player2.getTerritories().add(new Territory(territory2));
        soldier.move(cell1, cell3, map);
        Assert.assertEquals(soldier,cell3.getElementOn());
        Assert.assertEquals(21,capital.getMoney());
        Assert.assertEquals(19,player2.getTerritories().get(0).findCapital().getMoney());
    }

}
