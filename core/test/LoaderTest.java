

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.utils.SerializationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class LoaderTest extends GameStageTest {
    String tmxFileCorrect = "test.tmx";
    String tmxFileIncorrect = "g4858.tmx";
    String xmlFileCorrect = "test.xml";
    String xmlFileIncorrect = "adzada.xml";

    @Test(expected = SerializationException.class)
    public void loadFromTmxTestExceptionHandler() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileIncorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
    }

    @Test
    public void loadFromTmxTestAllElementAvailable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(8, map.cells.size());
    }

    @Test
    public void loadFromTmxTestPosXOfCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Cell elemx0y0 = map.cells.get(0);
        Assert.assertEquals(0, elemx0y0.getX());
    }

    @Test
    public void loadFromTmxTestLoadingWaterCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Cell elemx0y1 = map.findCell(0, 1);
        Assert.assertTrue(elemx0y1.isWater());
    }

    @Test
    public void loadFromTmxTestLoadingLandCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Cell elemx0y0 = map.findCell(0, 0);
        Assert.assertFalse(elemx0y0.isWater());
    }

    @Test(expected = WrongFormatException.class)
    public void loadFromXmlTestExceptionHandler() throws WrongFormatException {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileIncorrect, "");
        loader.loadFromXmlFile(map);
    }

    @Test
    public void loadFromXmlTestUnitLoading() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.cells.add(new Cell(1, 1, false, false, map.player1, null));
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {

        }
        Assert.assertEquals(new Soldier(2, 10, map.player1, 0), map.cells.get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestItemLoading() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.cells.add(new Cell(0, 0, false, false, map.player1, null));
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(new Capital(0, 0, map.player1, 10), map.cells.get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestInfrastructureLoadingIfAvailable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.cells.add(new Cell(0, 1, false, true, map.player1, null));
        Infrastructure.setInfrastructureAvailable(true);
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(new Boat(3, 0, 0, 25, map.player1), map.cells.get(0).getElementOn());


    }

    @Test
    public void loadFromXmlTestInfrastructureLoadingIfDisable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        map.cells.add(new Cell(0, 1, false, true, map.player1, null));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        Infrastructure.setInfrastructureAvailable(false);
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertNotEquals(new Boat(3, 0, 0, 25, map.player1), map.cells.get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestElementLoadingOnExistingWrongCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.cells.add(new Cell(2, 2, false, true, map.player1, null));
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertNull(map.cells.get(0).getElementOn());

    }

    @Test
    public void loadFromXmlTestElementLoadingWhenWrongPlayer() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.cells.add(new Cell(2, 1, false, false, map.player1, null));
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertNull(map.cells.get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestElementLoadingWhenCellNotExist() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        try {
            loader.loadFromXmlFile(map);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(0, map.cells.size());
    }
}
