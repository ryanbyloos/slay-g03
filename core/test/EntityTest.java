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
        Cell c = new Cell(0, 0, false, false, player0, new Soldier(100, 100, player0, 3));
        AttackTower attackTower = new AttackTower(100, 100, player1, 3);
        attackTower.attack(c);
        Assert.assertNull(c.getElementOn());
    }

    @Test
    public void boatCapture() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>());
        Boat boat = new Boat(0, 0, 0, 0, player0);
        boat.setSoldiers(new ArrayList<Soldier>());
        boat.getSoldiers().add(new Soldier(0, 0, player0, 0));
        boat.capture(new Soldier(0, 0, player1, 1));
        Assert.assertEquals(boat.getOwner(), player1);
    }

    @Test
    public void setBoatDefence() {
        Player player0 = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Boat boat = new Boat(0, 0, 0, 0, player0);
        Assert.assertEquals(boat.getDefense(), 0);
        boat.setSoldiers(new ArrayList<Soldier>());
        Assert.assertEquals(boat.getDefense(), 0);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 1));
        Assert.assertEquals(boat.getDefense(), 1);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 2));
        Assert.assertEquals(boat.getDefense(), 2);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 3));
        Assert.assertEquals(boat.getDefense(), 3);
        boat.getSoldiers().add(new Soldier(0, 0, player0, 2));
        Assert.assertEquals(boat.getDefense(), 3);
    }


}
