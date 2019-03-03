

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Entity.*;
import org.junit.Assert;

import org.junit.Test;


public class SoldierTest{



    @Test
    public void attackLowerSoldier(){
        Soldier soldierLow = new Soldier(0,0, null, 1, false);
        Soldier soldierHigh = new Soldier(0,0, null, 2, false);
        Cell cell1 = new Cell(0,0,false,false,null, soldierLow);
        Cell cell2 = new Cell(0,0,false,false,null, soldierHigh);
        soldierHigh.attack(cell2,cell1);
        Assert.assertSame(soldierHigh, cell2.getElementOn());
        Assert.assertNotSame(soldierLow,cell1.getElementOn());

    }

    @Test
    public void attackHigherSoldier(){
        Soldier soldierLow = new Soldier(0,0, null, 1, false);
        Soldier soldierHigh = new Soldier(0,0, null, 2, false);
        Cell cell1 = new Cell(0,0,false,false,null, soldierLow);
        Cell cell2 = new Cell(0,0,false,false,null, soldierHigh);
        soldierLow.attack(cell1,cell2);
        Assert.assertSame(soldierLow, cell1.getElementOn());
        Assert.assertSame(soldierHigh, cell2.getElementOn());

    }

    @Test
    public void attackEqualSoldier(){
        Soldier soldier1 = new Soldier(0,0, null, 1, false);
        Soldier soldier2 = new Soldier(0,0, null, 1, false);
        Cell cell1 = new Cell(0,0,false,false,null, soldier1);
        Cell cell2 = new Cell(0,0,false,false,null, soldier2);
        soldier1.attack(cell1,cell2);
        Assert.assertSame(soldier1, cell1.getElementOn());
        Assert.assertSame(soldier2, cell2.getElementOn());
    }

     @Test
    public void attackEqualHighSoldier(){
         Soldier soldier1 = new Soldier(0,0, null, 3, false);
         Soldier soldier2 = new Soldier(0,0, null, 3, false);
         Cell cell1 = new Cell(0,0,false,false,null, soldier1);
         Cell cell2 = new Cell(0,0,false,false,null, soldier2);
         soldier1.attack(cell1,cell2);
         Assert.assertNotEquals(soldier1,cell1.getElementOn());
         Assert.assertNotEquals(soldier2, cell2.getElementOn());
     }


}