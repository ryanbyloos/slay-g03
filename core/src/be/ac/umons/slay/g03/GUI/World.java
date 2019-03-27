package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.ReplayParserException;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

public class World extends MapRenderer implements InputProcessor {

    @Override
    public void create() {
        super.create();
        map = new Map(new ArrayList<>(), new Player("Yellow", 1, -1, false, new ArrayList<>()),
                new Player("Red", 2, -1, false, new ArrayList<>()));
        map.getPlayer1().setTurn(true);
        map.getPlayer1().setMaxMoveNumber(-1);
        map.getPlayer2().setMaxMoveNumber(-1);
        loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
//        loader = new Loader("g3_4.tmx", "g3_5.xml", "The Star");
//        loader = new Loader("g3_6.tmx", "g3_7.xml", "The River");
//        loader = new Loader("g3_8.tmx", "g3_9.xml", "The Void");
//        loader = new Loader("g3_10.tmx", "g3_11.xml", "The Gate");

        try {
            loader.load(map, false);
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        gameState = new GameState(map, loader, -1, null);
        try {

            gameState.saveReplay();
            gameState.storeTurn();
            gameState.storeMove(map.getPlayer1());
            gameState.save();
        }  catch (TransformerException | SAXException| ParserConfigurationException | IOException  | ReplayParserException e) {
            e.printStackTrace();
        }
//        Infrastructure.setAvailability(Slay.game.prefs.getBoolean("infrastructures"));
        setViewport(camera, map);
    }

    @Override
    public void render() {
        if(gameState.getStates().isOver()){
            Slay.setScreen(Slay.home);
        }
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw(map);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P) {
            gameState.nextTurn();
        } else if (keycode == Input.Keys.ESCAPE){
            Slay.setScreen(Slay.home);
        }
        else if(keycode == Input.Keys.R){
        }

        else if (keycode == Input.Keys.J) {
            if (map.getPlayer1().isTurn()) {
                try {
                    gameState.undo(map.getPlayer1());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    gameState.undo(map.getPlayer2());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            }
        } else if (keycode == Input.Keys.L) {
            if (map.getPlayer1().isTurn()) {
                try {
                    gameState.redo(map.getPlayer1());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    gameState.redo(map.getPlayer2());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            int[] pos = getMouseCoord(camera);
            gameState.handle(pos[0], pos[1]);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();
        float w = map.getWidth() * 32;
        float h = map.getHeight() * 32;
        float z = camera.zoom;
        if (camera.position.x <= w - (32 / z) && camera.position.x >= 32)
            camera.translate(-x * z, 0);
        else if (camera.position.x > w - (32 / z))
            camera.position.x = w - (32 / z);
        else
            camera.position.x = 32;
        if (camera.position.y <= h - (128 / z) && camera.position.y >= 32)
            camera.translate(0, y * z);
        else if (camera.position.y > h - (128 / z))
            camera.position.y = h - (128 / z);
        else
            camera.position.y = 32;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (!gameState.getStates().isTerritorySelected() && screenX < Slay.w && (Slay.h - screenY) < Slay.h) {
            int[] pos = getMouseCoord(camera);
            Cell cell = map.findCell(pos[0], pos[1]);
            if (cell != null && cell.getOwner() != null && !(gameState.getStates().isSoldierSelected() || gameState.getStates().isBoatSelected() || gameState.getStates().isAttackTowerSelected())) {
                gameState.getStates().setTerritory(cell.findTerritory());
            } else {
                gameState.getStates().setTerritory(null);
            }
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((camera.zoom > 0.6 && amount < 0) || (camera.zoom <= 1.2 && amount > 0))
            camera.zoom += 0.1 * amount;
        return false;
    }
}
