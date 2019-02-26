package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import org.junit.Assert;
import org.junit.Test;

public class SoldierTest{
    @beforeClass
    public static void init(){
        SPlayer player1 = new Player("P1", 1, 0, false, 0, new ArrayList<Territory>());
        Player player2 = new Player("P2", 2, 0, false, 0, new ArrayList<Territory>());
    }

    @test
    public void attackLowerSoldier(){
        Soldier soldierLow = new Soldier(0,0, player1, 2);
        Soldier soldierHigh = new Soldier(0,0, player1, 3);
        soldierHigh.attack(soldierHigh,soldierLow);
        Assert.assertNull(soldierLow);
        Assert.assertNotNull(soldierHigh);

    }

    @test
    public void attackHigherSoldier(){
        Soldier soldierLow = new Soldier(0,0, player1, 2);
        Soldier soldierHigh = new Soldier(0,0, player1, 3);
        soldierLow.attack(soldierLow,soldierHigh);
        Assert.assertNotNull(soldierLow);
        Assert.assertNotNull(soldierHigh);

    }

    @test
    public void attackEqualSoldier(){
        Soldier soldier1 = new Soldier(0,0, player1, 3);
        Soldier soldier2 = new Soldier(0,0, player1, 3);
        soldier1.attack(soldier1,soldier2);
        Assert.assertNotNull(soldier1);
        Assert.assertNotNull(soldier2);
    }

     @test
    public void attackEqualHighSoldier(){
         Soldier soldier1 = new Soldier(0,0, player1, 4);
         Soldier soldier2 = new Soldier(0,0, player1, 4);
         soldier1.attack(soldier1,soldier2);
         Assert.assertNull(soldier1);
         Assert.assertNull(soldier2);
     }


}