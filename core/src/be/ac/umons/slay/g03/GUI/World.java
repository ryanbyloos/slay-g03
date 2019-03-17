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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class World extends ApplicationAdapter implements InputProcessor {
    private TextureAtlas.AtlasRegion blueHex, greenHex, yellowHex, redHex;
    private TextureAtlas.AtlasRegion soldier0, soldier1, soldier2, soldier3;
    private TextureAtlas.AtlasRegion capital, tree, contour;
    private TextureAtlas.AtlasRegion defenceTower, attackTower, boat, grave, mine;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private TextureAtlas atlas;
    private Cell source, destination;
    private Territory territory;
    private GameState gameState;
    private Loader loader;
    private boolean selected;

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
        int EVEN = 0;
        int ODD = 1;
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == EVEN) ? 1 : 0), cell.getY() * 24);
        } else {
            batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == ODD) ? 1 : 0), cell.getY() * 24);
        }
    }

    private void drawContour(ArrayList<Cell> cells) {
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
                if (cell.getElementOn() != null) {
                    if (cell.getElementOn() instanceof Soldier) {
                        switch (cell.getElementOn().getLevel()) {
                            case 0:
                                drawSprite(parity, soldier0, cell);
                                break;
                            case 1:
                                drawSprite(parity, soldier1, cell);
                                break;
                            case 2:
                                drawSprite(parity, soldier2, cell);
                                break;
                            case 3:
                                drawSprite(parity, soldier3, cell);
                                break;
                        }
                    } else if (cell.getElementOn() instanceof Capital) {
                        drawSprite(parity, capital, cell);
                    } else if (cell.getElementOn() instanceof DefenceTower) {
                        drawSprite(parity, defenceTower, cell);
                    } else if (cell.getElementOn() instanceof AttackTower) {
                        drawSprite(parity, attackTower, cell);
                    } else if (cell.getElementOn() instanceof Grave) {
                        drawSprite(parity, grave, cell);
                    } else if (cell.getElementOn() instanceof Tree) {
                        drawSprite(parity, tree, cell);
                    }
                }
            }
        }
    }


    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
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
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        map = new Map(new ArrayList<>(), new Player("Danial", 1, -1, false, 0, new ArrayList<>()),
                new Player("Alex", 2, -1, false, 0, new ArrayList<>()));
        map.getPlayer1().setTurn(true);
        loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
        Infrastructure.setIsAvailable(true);
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

        setViewport(camera, map);
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(145 / 255f, 145 / 255f, 145 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        if (selected && source.getElementOn() != null)
            drawContour(source.accessibleCell(map));
        if (territory != null)
            drawContour(territory.getCells());
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P){
            gameState.nextTurn();
        }

        else if (keycode == Input.Keys.ESCAPE)
            ScreenHandler.setScreen(ScreenHandler.menu);
        else if(keycode == Input.Keys.J){
            if(map.getPlayer1().isTurn()) {
                try {
                    gameState.undo(map.getPlayer1());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gameState.undo(map.getPlayer2());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(keycode == Input.Keys.L){
            if(map.getPlayer1().isTurn()) {
                try {
                    gameState.redo(map.getPlayer1());
                } catch (ReplayParserException e) {
                    e.printStackTrace();
                }
            }
            else {
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
            if (!selected) {
                int[] pos = getMouseCoord(camera);
                source = map.findCell(pos[0], pos[1]);
                if (source != null && source.getElementOn() instanceof Soldier) {
                    ((Soldier) source.getElementOn()).select();
                    if (source.accessibleCell(map) != null && ((Soldier) source.getElementOn()).select()) {
                        selected = true;
                        territory = null;
                    }
                }
            } else {
                int[] pos = getMouseCoord(camera);
                destination = map.findCell(pos[0], pos[1]);
                ((Soldier) source.getElementOn()).move(source, destination, map);
                try {
                    if(map.getPlayer1().isTurn()){
                        gameState.storeMove(map.getPlayer1());
                    }
                    else {
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
        float d = camera.zoom;
        if (camera.position.x <= 650 && camera.position.x >= 25)
            camera.translate(-x * d, 0);
        else if (camera.position.x > 650)
            camera.position.x = 650;
        else
            camera.position.x = 25;
        if (camera.position.y <= 450 && camera.position.y >= 25)
            camera.translate(0, y * d);
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
        if ((camera.zoom > 0.2 && amount < 0) || (camera.zoom <= 1 && amount > 0))
            camera.zoom += 0.1 * amount;
        return false;
    }
}
