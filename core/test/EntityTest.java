import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.AttackTower;
import be.ac.umons.slay.g03.Entity.Boat;
import be.ac.umons.slay.g03.Entity.Soldier;
import be.ac.umons.slay.g03.Entity.Tree;
import org.junit.*;


import java.util.ArrayList;

public class EntityTest {

    @Test
    public void towerAttack() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<>());
        Cell c = new Cell(0, 0, false, false, player0, new Soldier(100, 100, player0, 3, false));
        AttackTower attackTower = new AttackTower(100, 100, player1, 3);
        attackTower.attack(c);
        Assert.assertNull(c.getElementOn());
    }

    @Test
    public void boatCapture() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(0, 0, 0, 0, player0, false);
        boat.setSoldiers(new ArrayList<Soldier>());
        boat.getSoldiers().add(new Soldier(0, 0, player0, 0, false));
        boat.capture(new Soldier(0, 0, player1, 1, false));
        Assert.assertEquals(boat.getOwner(), player1);
    }

    @Test
    public void setBoatDefence() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<>());
        Boat boat = new Boat(0, 0, 0, 0, player0, false);
        Assert.assertEquals(boat.getDefense(), 0);
        boat.setSoldiers(new ArrayList<>());
        Assert.assertEquals(boat.getDefense(), 0);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 1, false));
        Assert.assertEquals(boat.getDefense(), 1);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 2, false));
        Assert.assertEquals(boat.getDefense(), 2);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 3, false));
        Assert.assertEquals(boat.getDefense(), 3);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 2, false));
        Assert.assertEquals(boat.getDefense(), 3);
    }

    @Test
    public void simpleMove() {

        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(0, 0, player, 1, false);
        Cell cell1 = new Cell(0, 0, false, false, player, soldier);
        Cell cell2 = new Cell(1, 0, false, false, null, null);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<Cell>();
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
        Soldier soldier = new Soldier(0, 0, player, 1, false);
        Tree tree = new Tree(0, 0, null);
        Cell cell1 = new Cell(0, 0, false, false, player, soldier);
        Cell cell2 = new Cell(1, 0, false, false, null, tree);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<Cell>();
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
        Soldier soldierLow = new Soldier(0, 0, player1, 1, false);
        Soldier soldierHigh = new Soldier(0, 0, player2, 2, false);
        Cell cell1 = new Cell(0, 0, false, false, player1, soldierLow);
        Cell cell2 = new Cell(1, 0, false, false, player2, soldierHigh);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        ArrayList<Cell> territory = new ArrayList<Cell>();
        territory.add(cell1);
        player1.getTerritories().add(new Territory(territory));
        ArrayList<Cell> territory2 = new ArrayList<Cell>();
        territory2.add(cell2);
        player2.getTerritories().add(new Territory(territory2));
        Map map = new Map(mapCell, player1, player2);
        soldierHigh.move(cell2, cell1, map);
        Assert.assertEquals(soldierHigh, cell1.getElementOn());
        Assert.assertNotEquals(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackHigherSoldier() {
        Soldier soldierLow = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 1, false);
        Soldier soldierHigh = new Soldier(0, 0, new Player("player2", 1, 0, false, 0, new ArrayList<>()), 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldierLow);
        Cell cell2 = new Cell(1, 0, false, false, null, soldierHigh);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldierLow.move(cell1, cell2, map);
        Assert.assertEquals(soldierLow, cell1.getElementOn());
        Assert.assertEquals(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackEqualSoldier() {
        Soldier soldier1 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 1, false);
        Soldier soldier2 = new Soldier(0, 0, new Player("player2", 1, 0, false, 0, new ArrayList<>()), 1, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());
    }

    @Test
    public void attackEqualHighSoldier() {
        Soldier soldier1 = new Soldier(0, 0, new Player("player1", 1, 0, false, 0, new ArrayList<>()), 3, false);
        Soldier soldier2 = new Soldier(0, 0, new Player("player2", 2, 0, false, 0, new ArrayList<>()), 3, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertNull(cell1.getElementOn());
        Assert.assertNull(cell2.getElementOn());
    }

    @Test
    public void mergesoldierSameLevel() {
        Soldier soldier1 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 2, false);
        Soldier soldier2 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(3, cell2.getElementOn().getLevel());
        Assert.assertNull(cell1.getElementOn());
    }

    @Test
    public void mergesoldierMaxLevel() {
        Soldier soldier1 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 3, false);
        Soldier soldier2 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 3, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());

    }

    @Test
    public void mergesoldierDiffLevel() {
        Soldier soldier1 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 1, false);
        Soldier soldier2 = new Soldier(0, 0, new Player("player1", 0, 0, false, 0, new ArrayList<>()), 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(1, 0, false, false, null, soldier2);
        ArrayList<Cell> mapCell = new ArrayList<Cell>();
        mapCell.add(cell1);
        mapCell.add(cell2);
        Map map = new Map(mapCell, null, null);
        soldier1.move(cell1, cell2, map);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());

    }

    @Test
    public void simpleAccessibleCell() {
        Player player = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(0, 0, player, 1, false);
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
        Soldier soldier = new Soldier(0, 0, player, 1, false);
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
        Soldier soldier = new Soldier(0, 0, player1, 1, false);
        Cell cell1 = new Cell(6, 6, false, false, player1, soldier);
        Cell cell2 = new Cell(5, 6, false, false, player1, null);
        Cell cell3 = new Cell(7, 6, false, false, player1, null);
        Cell cell4 = new Cell(6, 8, false, false, player1, null);
        Cell cell5 = new Cell(5, 8, false, false, player1, null);
        Cell cell6 = new Cell(7, 8, false, false, player1, null);
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
    }

    @Test
    public void splitTerritoryTest() {
        Player player1 = new Player("player1", 0, 0, false, 0, new ArrayList<>());
        Player player2 = new Player("player2", 0, 0, false, 0, new ArrayList<>());
        Soldier soldier = new Soldier(0, 0, player1, 1, false);
        Cell cell1 = new Cell(6, 6, false, false, player1, null);
        Cell cell2 = new Cell(5, 6, false, false, player1, null);
        Cell cell3 = new Cell(7, 6, false, false, player1, null);
        Cell cell4 = new Cell(6, 7, false, false, player1, soldier);
        Cell cell5 = new Cell(6, 8, false, false, player2, null);
        Cell cell6 = new Cell(5, 8, false, false, player2, null);
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
    }


}
