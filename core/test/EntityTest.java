import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.AttackTower;
import be.ac.umons.slay.g03.Entity.Soldier;
import org.junit.*;


import java.util.ArrayList;

public class EntityTest {

    @Test
    public void towerAttack() {
        Player player = new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>());
        Player player1 = new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>());
        Cell c = new Cell(0, 0, false, false, player, new Soldier(100, 100, player, 4));
        AttackTower attackTower = new AttackTower(100, 100, player1, 4);
        attackTower.attack(c);
        Assert.assertNull(c.getElementOn());
    }
}
