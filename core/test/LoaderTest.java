import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.Boat;
import be.ac.umons.slay.g03.Entity.Capital;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.Entity.Soldier;
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
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileIncorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
    }

    @Test
    public void loadFromTmxTestAllElementAvailable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(8, map.getCells().size());
    }

    @Test
    public void loadFromTmxTestPosXOfCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, "unneeded", "");
        try {
            loader.loadFromTmxFile(map, false);
        } catch (WrongFormatException e) {
        }
        Cell elemx0y0 = map.getCells().get(0);
        Assert.assertEquals(0, elemx0y0.getX());
    }

    @Test
    public void loadFromTmxTestLoadingWaterCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
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
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
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
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileIncorrect, "");
        loader.loadFromXmlFile(map, false);
    }

    @Test
    public void loadFromXmlTestUnitLoading() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.getCells().add(new Cell(1, 1, false, false, map.getPlayer1(), null));
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {

        }
        Assert.assertEquals(new Soldier(map.getPlayer1(), 0), map.getCells().get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestItemLoading() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.getCells().add(new Cell(0, 0, false, false, map.getPlayer1(), null));
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(new Capital(map.getPlayer1(), 10), map.getCells().get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestInfrastructureLoadingIfAvailable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<>()),
                new Player("Alex", 2, 0, false, new ArrayList<>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.getCells().add(new Cell(0, 1, false, true, map.getPlayer1(), null));
        Infrastructure.setAvailability(true);
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Boat boat = new Boat(map.getPlayer1());
        ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
        soldiers.add(new Soldier(map.getPlayer1(), 1));
        boat.setSoldiers(soldiers);
        Assert.assertEquals(boat, map.getCells().get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestInfrastructureLoadingIfDisable() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        map.getCells().add(new Cell(0, 1, false, true, map.getPlayer1(), null));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        Infrastructure.setAvailability(false);
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertNotEquals(new Boat(map.getPlayer1()), map.getCells().get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestElementLoadingOnExistingWrongCell() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.getCells().add(new Cell(2, 2, false, true, map.getPlayer1(), null));
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertNull(map.getCells().get(0).getElementOn());

    }

    @Test
    public void loadFromXmlTestElementLoadingWhenWrongPlayer() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        map.getCells().add(new Cell(2, 1, false, false, map.getPlayer1(), null));
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertNull(map.getCells().get(0).getElementOn());
    }

    @Test
    public void loadFromXmlTestElementLoadingWhenCellNotExist() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, new ArrayList<Territory>()));
        Loader loader = new Loader(tmxFileCorrect, xmlFileCorrect, "");
        try {
            loader.loadFromXmlFile(map, false);
        } catch (WrongFormatException e) {
        }
        Assert.assertEquals(0, map.getCells().size());
    }
}
