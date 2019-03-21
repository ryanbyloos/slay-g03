package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.ReplayParserException;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class World extends ApplicationAdapter implements InputProcessor {
    TextureAtlas.AtlasRegion soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    private TextureAtlas.AtlasRegion blueHex, greenHex, yellowHex, redHex, tree, grave, contour, capital;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private TextureAtlas atlas;
    private Cell source, destination;
    private Territory territory;
    private GameState gameState;
    private Loader loader;
    private boolean selected;
    boolean creationMode = false;

    private int[] getMouseCoord(OrthographicCamera camera) {
        Vector3 vector = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        vector.x -= 32;
        vector.y -= 16;
        double ratio = 2;
        double x = vector.x / (16 * ratio);
        double y = vector.y / (16 * ratio);
        double tmp = Math.floor(x + (ratio * y) + 1);
        double r = Math.floor((Math.floor(2 * x + 1) + tmp) / 3);
        double q = Math.floor((tmp + Math.floor(-x + (ratio * y) + 1)) / 3);
        r -= (int) q >> 1;
        return new int[]{(int) r, (int) q};
    }

    private void setViewport(OrthographicCamera camera, Map map) {
        int mapH = map.getHeight();
        int width = (map.getWidth() * 32) + 16;
        int height = mapH * 24 + 8 + 32 * (mapH % 2);
        camera.setToOrtho(false, width, height);
    }

    private void drawSprite(int parity, TextureAtlas.AtlasRegion sprite, Cell cell) {
        drawSprite(parity, 0, sprite, cell);
    }

    private void drawSprite(int parity, int offset, TextureAtlas.AtlasRegion sprite, Cell cell) {
        int EVEN = 0;
        int ODD = 1;
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == EVEN) ? 1 : 0), cell.getY() * 24 + offset);
        } else {
            batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == ODD) ? 1 : 0), cell.getY() * 24 + offset);
        }
    }

    private void drawHighlights(ArrayList<Cell> cells) {
        for (Cell cell : cells)
            drawSprite((map.getHeight() % 2), contour, cell);
    }

    private void draw() {
        int parity = map.getHeight() % 2;
        for (int i = 0; i < map.getCells().size(); i++) {
            Cell cell = map.getCells().get(i);
            if (cell.isWater()) {
                drawSprite(parity, blueHex, cell);
                if (cell.getElementOn() != null) {
                    if (cell.getElementOn() instanceof Boat) {
                        drawSprite(parity, boat, cell);
                    } else if (cell.getElementOn() instanceof Mine) {
                        drawSprite(parity, mine, cell);
                    }
                }
            } else {
                if (cell.getOwner() == null) drawSprite(parity, greenHex, cell);
                else if (cell.getOwner() == map.getPlayer1()) drawSprite(parity, yellowHex, cell);
                else drawSprite(parity, redHex, cell);
                if (selected && source.getElementOn() != null)
                    drawHighlights(source.accessibleCell(map));
                if (territory != null)
                    drawHighlights(territory.getCells());
            }
        }
        for (int i = 0; i < map.getCells().size(); i++) {
            Cell cell = map.getCells().get(i);
            if (cell.getElementOn() != null) {
                if (cell.getElementOn() instanceof Soldier) {
                    switch (cell.getElementOn().getLevel()) {
                        case 0:
                            drawSprite(parity, 10, soldier0, cell);
                            break;
                        case 1:
                            drawSprite(parity, 10, soldier1, cell);
                            break;
                        case 2:
                            drawSprite(parity, 10, soldier2, cell);
                            break;
                        case 3:
                            drawSprite(parity, 10, soldier3, cell);
                            break;
                    }
                } else if (cell.getElementOn() instanceof Capital) {
                    drawSprite(parity, 4, capital, cell);
                } else if (cell.getElementOn() instanceof DefenceTower) {
                    drawSprite(parity, 10, defenceTower, cell);
                } else if (cell.getElementOn() instanceof AttackTower) {
                    drawSprite(parity, 10, attackTower, cell);
                } else if (cell.getElementOn() instanceof Grave) {
                    drawSprite(parity, 10, grave, cell);
                } else if (cell.getElementOn() instanceof Tree) {
                    drawSprite(parity, 12, tree, cell);
                }
            }
        }
    }

    private void initTextures(TextureAtlas atlas) {
        greenHex = atlas.findRegion("tile000");
        blueHex = atlas.findRegion("tile001");
        yellowHex = atlas.findRegion("tile002");
        redHex = atlas.findRegion("tile003");
        soldier0 = atlas.findRegion("tile004");
        soldier1 = atlas.findRegion("tile005");
        soldier2 = atlas.findRegion("tile006");
        soldier3 = atlas.findRegion("tile007");
        defenceTower = atlas.findRegion("tile008");
        attackTower = atlas.findRegion("tile009");
        boat = atlas.findRegion("tile010");
        capital = atlas.findRegion("tile011");
        mine = atlas.findRegion("tile012");
        tree = atlas.findRegion("tile013");
        grave = atlas.findRegion("tile014");
        contour = atlas.findRegion("tile015");
    }

    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        initTextures(atlas);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        map = new Map(new ArrayList<>(), new Player("Danial", 1, -1, false, 0, new ArrayList<>()),
                new Player("Alex", 2, -1, false, 0, new ArrayList<>()));
        map.getPlayer1().setTurn(true);
        map.getPlayer1().setMaxMoveNumber(-1);
        map.getPlayer2().setMaxMoveNumber(-1);
        loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
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
        } catch (ReplayParserException e) {
            e.printStackTrace();
        }
        Infrastructure.setAvailability(ScreenHandler.game.prefs.getBoolean("infrastructures"));
        setViewport(camera, map);
    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
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
        } else if (keycode == Input.Keys.R) {
            System.out.println(map.getPlayer1().getTerritories());
        } else if (keycode == Input.Keys.ESCAPE)
            ScreenHandler.setScreen(ScreenHandler.home);
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
            if (creationMode) {
                destination = map.findCell(pos[0], pos[1]);
                if (destination != null && destination.getElementOn() == null) {
                    if (destination.getOwner() == map.getPlayer1())
                        destination.setElementOn(new Soldier(5, 20, map.getPlayer1(), 0, false));
                    else if (destination.getOwner() == map.getPlayer2())
                        destination.setElementOn(new Soldier(5, 20, map.getPlayer2(), 0, false));
                }
                try {
                    gameState.storeMove(destination.getOwner());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
                destination = null;
                this.creationMode = false;
            } else if (!selected) {
                source = map.findCell(pos[0], pos[1]);
                if (source != null && source.getElementOn() instanceof Soldier) {
                    ((Soldier) source.getElementOn()).select();
                    if (source.accessibleCell(map) != null && ((Soldier) source.getElementOn()).select()) {
                        selected = true;
                        territory = null;
                    }
                }
            } else {
                destination = map.findCell(pos[0], pos[1]);
                ((Soldier) source.getElementOn()).move(source, destination, map);
                try {
                    if (map.getPlayer1().isTurn()) {
                        gameState.storeMove(map.getPlayer1());
                    } else {
                        gameState.storeMove(map.getPlayer2());
                    }

                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
                selected = false;
            }
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
        float z = camera.zoom;
        if (camera.position.x <= 650 && camera.position.x >= 25)
            camera.translate(-x * z, 0);
        else if (camera.position.x > 650)
            camera.position.x = 650;
        else
            camera.position.x = 25;
        if (camera.position.y <= 450 && camera.position.y >= 25)
            camera.translate(0, y * z);
        else if (camera.position.y > 450)
            camera.position.y = 450;
        else
            camera.position.y = 25;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (screenX < map.getWidth() * 32 && (map.getHeight() * 32 - screenY) < map.getHeight() * 32) {
            int[] pos = getMouseCoord(camera);
            Cell cell = map.findCell(pos[0], pos[1]);
            if (cell != null && cell.getOwner() != null && !selected) {
                territory = cell.findTerritory();
            } else {
                territory = null;
            }
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((camera.zoom > 0.2 && amount < 0) || (camera.zoom <= 1.2 && amount > 0))
            camera.zoom += 0.1 * amount;
        return false;
    }
}
