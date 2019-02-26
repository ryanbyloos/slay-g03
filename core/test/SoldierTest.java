

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;

public class SoldierTest{



    @Test
    public void attackLowerSoldier(){
        Soldier soldierLow = new Soldier(0,0, null, 1);
        Soldier soldierHigh = new Soldier(0,0, null, 2);
        Cell cell1 = new Cell(0,0,false,false,null, soldierLow);
        Cell cell2 = new Cell(0,0,false,false,null, soldierHigh);
        soldierHigh.attack(cell2,cell1);
        Assert.assertEquals(soldierHigh, cell2.getElementOn());
        Assert.assertNotEquals(soldierLow,cell1.getElementOn());

    }

    @Test
    public void attackHigherSoldier(){
        Soldier soldierLow = new Soldier(0,0, null, 1);
        Soldier soldierHigh = new Soldier(0,0, null, 2);
        Cell cell1 = new Cell(0,0,false,false,null, soldierLow);
        Cell cell2 = new Cell(0,0,false,false,null, soldierHigh);
        soldierLow.attack(cell1,cell2);
        Assert.assertEquals(soldierLow, cell1.getElementOn());
        Assert.assertEquals(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackEqualSoldier(){
        Soldier soldier1 = new Soldier(0,0, null, 1);
        Soldier soldier2 = new Soldier(0,0, null, 1);
        Cell cell1 = new Cell(0,0,false,false,null, soldier1);
        Cell cell2 = new Cell(0,0,false,false,null, soldier2);
        soldier1.attack(cell1,cell2);
        Assert.assertEquals(soldier1, cell1.getElementOn());
        Assert.assertEquals(soldier2, cell2.getElementOn());
    }

     @Test
    public void attackEqualHighSoldier(){
         Soldier soldier1 = new Soldier(0,0, null, 3);
         Soldier soldier2 = new Soldier(0,0, null, 3);
         Cell cell1 = new Cell(0,0,false,false,null, soldier1);
         Cell cell2 = new Cell(0,0,false,false,null, soldier2);
         soldier1.attack(cell1,cell2);
         Assert.assertNotEquals(soldier1,cell1.getElementOn());
         Assert.assertNotEquals(soldier2, cell2.getElementOn());
     }


}