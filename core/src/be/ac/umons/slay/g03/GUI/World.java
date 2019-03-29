package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.ReplayParserException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class World extends MapRenderer implements InputProcessor {

    Loader loader;

    public World(Map map, Loader loader) {
        this.map = map;
        this.loader = loader;
    }

    @Override
    public void create() {
        super.create();
        map.getPlayer1().setTurn(true);
        map.getPlayer1().setMaxMoveNumber(-1);
        map.getPlayer2().setMaxMoveNumber(-1);
//        loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
//        loader = new Loader("g3_4.tmx", "g3_5.xml", "The Star");
//        loader = new Loader("g3_6.tmx", "g3_7.xml", "The River");
//        loader = new Loader("g3_8.tmx", "g3_9.xml", "The Void");
//        loader = new Loader("g3_10.tmx", "g3_11.xml", "The Gate");


        gameState = new GameState(map, loader, -1, null);
        try {
            gameState.saveReplay();
            gameState.storeTurn();
            gameState.storeMove(map.getPlayer1());
            gameState.save();
        } catch (TransformerException | SAXException | ParserConfigurationException | IOException | ReplayParserException e) {
            e.printStackTrace();
        }

        Infrastructure.setAvailability(Slay.game.preferences.getBoolean("infrastructures"));
        setViewport(camera, map);
    }

    @Override
    public void render() {

        if (gameState.getStates().isOver()) {
            String winner;
            String loser;
            int turn = gameState.getTurnPlayed();
            if (map.getPlayer1().isOver()) {
                winner = map.getPlayer2().getName();
                loser = map.getPlayer1().getName();
            } else {
                winner = map.getPlayer1().getName();
                loser = map.getPlayer2().getName();
            }
            Slay.victoryScreen = new VictoryScreen(winner, loser, turn);
            Slay.setScreen(Slay.victoryScreen);
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
        } else if (keycode == Input.Keys.ESCAPE) {
            Slay.setScreen(Slay.home);
        } else if (keycode == Input.Keys.R) {
            System.out.println(gameState.getStates());
        } else if (keycode == Input.Keys.J) {
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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((camera.zoom > 0.6 && amount < 0) || (camera.zoom <= 1.2 && amount > 0))
            camera.zoom += 0.1 * amount;
        return false;
    }
}
