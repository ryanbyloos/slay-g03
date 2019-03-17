import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.ReplayParserException;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameStateTest extends GameStageTest {
    @Test
    public void undoTest() {
        Map map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader("test1.tmx", "test.xml", "wow");
        try {
            loader.loadFromTmxFile(map, false);
            loader.loadFromXmlFile(map, false);
            GameState gameState = new GameState(map, loader, 0, null);
            gameState.saveReplay();
            gameState.storeTurn();
            gameState.storeMove(map.getPlayer1());
            map.getPlayer1().setMoveNumber(1);
            map.getPlayer1().setMaxMoveNumber(1);
            map.findCell(1, 1).setElementOn(null);
            gameState.storeMove(map.getPlayer1());
            gameState.undo(map.getPlayer1());
            Assert.assertNotEquals(null, map.findCell(1, 1).getElementOn());
        } catch (ReplayParserException e) {
            e.printStackTrace();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }


    }
}
