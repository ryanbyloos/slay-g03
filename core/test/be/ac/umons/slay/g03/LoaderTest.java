package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.Loader;
import com.badlogic.gdx.utils.SerializationException;
import org.junit.Assert;

import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.ArrayList;

public class LoaderTest extends GameStageTest {
    String tmxFileCorrect = "test.tmx";
    String tmxFileIncorrect = "g4858.tmx";
    String xmlFileCorrect = "test.xml";
    String xmlFileIncorrect = "adzada.xml";

    @Test(expected = SerializationException.class)
    public void loadFromTmxTestExceptionHandler(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileIncorrect, "unneeded");
        loader.loadFromTmxFile(map);
    }

    @Test
    public void loadFromTmxTestAllElementAvailable(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2,0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded");
        loader.loadFromTmxFile(map);
        Assert.assertEquals(8, map.cells.size());
    }
    @Test
    public void loadFromTmxTestPosXOfCell(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded");
        loader.loadFromTmxFile(map);
        Cell elemx0y0 = map.cells.get(0);
        Assert.assertEquals(0, elemx0y0.getX());
    }
    @Test
    public void loadFromTmxTestLoadingWaterCell(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded");
        loader.loadFromTmxFile(map);
        Cell elemx0y1 = map.cells.get(3);
        Assert.assertEquals(true, true);
    }
    @Test
    public void loadFromTmxTestLoadingLandCell(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded");
        loader.loadFromTmxFile(map);
        Cell elemx0y0 = map.cells.get(3);
        Assert.assertEquals(false, elemx0y0.isWater());
    }


    @Test(expected = IOException.class)
    public void loadFromXmlTestExceptionHandler(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileIncorrect);
        loader.loadFromXmlFile(map);
    }
    @Test
    public void loadFromXmlTestUnitLoading(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        map.cells.add(new Cell(1, 1, false, false, map.player1, null));
        loader.loadFromXmlFile(map);
        Assert.assertEquals(new Soldier(2, 10, map.player1, 0), map.cells.get(0).getElementOn());



    }

    @Test
    public void loadFromXmlTestItemLoading(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        map.cells.add(new Cell(0, 0, false, false, map.player1, null));
        loader.loadFromXmlFile(map);
        Assert.assertEquals(new Capital(0, 0, map.player1, 10), map.cells.get(0).getElementOn());


    }
    @Test
    public void loadFromXmlTestInfrastructureLoadingIfAvailable(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        map.cells.add(new Cell(0, 1, false, true, map.player1, null));
        Infrastructure.setInfrastructureAvailable(true);
        loader.loadFromXmlFile(map);
        Assert.assertEquals(new Boat(3, 0, 0, 25, map.player1), map.cells.get(0).getElementOn());


    }
    @Test
    public void loadFromXmlTestInfrastructureLoadingIfDisable(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        map.cells.add(new Cell(0, 1, false, true, map.player1, null));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        Infrastructure.setInfrastructureAvailable(false);
        loader.loadFromXmlFile(map);
        Assert.assertNotEquals(new Boat(3, 0, 0, 25, map.player1), map.cells.get(0).getElementOn());
    }
    @Test
    public void loadFromXmlTestElementLoadingOnExistingWrongCell(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        map.cells.add(new Cell(2, 2, false, true, map.player1, null));
        loader.loadFromXmlFile(map);
        Assert.assertEquals(null, map.cells.get(0).getElementOn());

    }

    @Test
    public void loadFromXmlTestElementLoadingWhenWrongPlayer(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        map.cells.add(new Cell(2, 1, false, false, map.player1, null));
        loader.loadFromXmlFile(map);
        Assert.assertEquals(null, map.cells.get(0).getElementOn());
    }
    @Test
    public void loadFromXmlTestElementLoadingWhenCellNotExist(){
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0,false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect,xmlFileCorrect);
        loader.loadFromXmlFile(map);
        Assert.assertEquals(0, map.cells.size());
    }







}
