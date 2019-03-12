import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.AttackTower;
import be.ac.umons.slay.g03.Entity.Boat;
import be.ac.umons.slay.g03.Entity.Soldier;
import org.junit.*;


import java.util.ArrayList;

public class EntityTest {

    @Test
    public void towerAttack() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>());
        Cell c = new Cell(0, 0, false, false, player0, new Soldier(100, 100, player0, 3, false));
        AttackTower attackTower = new AttackTower(100, 100, player1, 3);
        attackTower.attack(c);
        Assert.assertNull(c.getElementOn());
    }

    @Test
    public void boatCapture() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>());
        Boat boat = new Boat(0, 0, 0, 0, player0, false);
        boat.setSoldiers(new ArrayList<Soldier>());
        boat.getSoldiers().add(new Soldier(0, 0, player0, 0, false));
        boat.capture(new Soldier(0, 0, player1, 1, false));
        Assert.assertEquals(boat.getOwner(), player1);
    }

    @Test
    public void setBoatDefence() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Boat boat = new Boat(0, 0, 0, 0, player0, false);
        Assert.assertEquals(boat.getDefense(), 0);
        boat.setSoldiers(new ArrayList<Soldier>());
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
    public void attackLowerSoldier() {
        Soldier soldierLow = new Soldier(0, 0, null, 1, false);
        Soldier soldierHigh = new Soldier(0, 0, null, 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldierLow);
        Cell cell2 = new Cell(0, 0, false, false, null, soldierHigh);
        soldierHigh.attack(cell2, cell1);
        Assert.assertSame(soldierHigh, cell2.getElementOn());
        Assert.assertNotSame(soldierLow, cell1.getElementOn());

    }

    @Test
    public void attackHigherSoldier() {
        Soldier soldierLow = new Soldier(0, 0, null, 1, false);
        Soldier soldierHigh = new Soldier(0, 0, null, 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldierLow);
        Cell cell2 = new Cell(0, 0, false, false, null, soldierHigh);
        soldierLow.attack(cell1, cell2);
        Assert.assertSame(soldierLow, cell1.getElementOn());
        Assert.assertSame(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackEqualSoldier() {
        Soldier soldier1 = new Soldier(0, 0, null, 1, false);
        Soldier soldier2 = new Soldier(0, 0, null, 1, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(0, 0, false, false, null, soldier2);
        soldier1.attack(cell1, cell2);
        Assert.assertSame(soldier1, cell1.getElementOn());
        Assert.assertSame(soldier2, cell2.getElementOn());
    }

    @Test
    public void attackEqualHighSoldier() {
        Soldier soldier1 = new Soldier(0, 0, null, 3, false);
        Soldier soldier2 = new Soldier(0, 0, null, 3, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(0, 0, false, false, null, soldier2);
        soldier1.attack(cell1, cell2);
        Assert.assertNotEquals(soldier1, cell1.getElementOn());
        Assert.assertNotEquals(soldier2, cell2.getElementOn());
    }

    @Test
    public void mergesoldierSameLevel(){
        Soldier soldier1 = new Soldier(0, 0, null, 2, false);
        Soldier soldier2 = new Soldier(0, 0, null, 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(0, 0, false, false, null, soldier2);
        soldier1.merge(cell1,cell2);
        Assert.assertEquals(3,cell2.getElementOn().getLevel());
        Assert.assertNull(cell1.getElementOn());
    }

    @Test
    public void mergesoldierMaxLevel(){
        Soldier soldier1 = new Soldier(0, 0, null, 3, false);
        Soldier soldier2 = new Soldier(0, 0, null, 3, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(0, 0, false, false, null, soldier2);
        soldier1.merge(cell1,cell2);
        Assert.assertSame(soldier1, cell1.getElementOn());
        Assert.assertSame(soldier2, cell2.getElementOn());

    }@Test
    public void mergesoldierDiffLevel(){
        Soldier soldier1 = new Soldier(0, 0, null, 1, false);
        Soldier soldier2 = new Soldier(0, 0, null, 2, false);
        Cell cell1 = new Cell(0, 0, false, false, null, soldier1);
        Cell cell2 = new Cell(0, 0, false, false, null, soldier2);
        soldier1.merge(cell1,cell2);
        Assert.assertSame(soldier1, cell1.getElementOn());
        Assert.assertSame(soldier2, cell2.getElementOn());

    }


}
