package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.GameHandler.Loader;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;

public class LoaderTest extends GameTest  {
    String tmxFileCorrect = "test.tmx";
    String tmxFileIncorrect = "g4858.tmx";
    Loader loader = new Loader(tmxFileCorrect, "unneeded");
    Loader uncorrectLoader = new Loader(tmxFileIncorrect, "unneeded");

    Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 0, false, 0, new ArrayList<Territory>()),
            new Player("Alex", 0, false, 0, new ArrayList<Territory>()));

    @Test
    public void loadFromTmxTestLoading(){
        loader.loadFromTmxFile(map);
        Cell elem00 = map.cells.get(0);
        Assert.assertEquals(elem00.getX() , 0);
        /*Assert.assertEquals(elem00.getY() , 0);
        Assert.assertEquals(elem00.getElementOn(), null);
        Assert.assertEquals(elem00.isWater() , false);
        Cell elem01 = map.cells.get(1);
        Assert.assertEquals(elem01.getX() , 0);
        Assert.assertEquals(elem01.getY() , 1);
        Assert.assertEquals(elem01.getElementOn(), null);
        Assert.assertEquals(elem00.isWater() , true);

        Cell elem22 = map.cells.get(7);
        Assert.assertEquals(elem22.getX() , 2);
        Assert.assertEquals(elem22.getY() , 2);
        Assert.assertEquals(elem01.getElementOn(), null);
        Assert.assertEquals(elem00.isWater() , true);*/
    }

}
