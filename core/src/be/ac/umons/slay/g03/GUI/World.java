package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
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
    private TextureAtlas.AtlasRegion blueImage;
    private TextureAtlas.AtlasRegion greenImage;
    private TextureAtlas.AtlasRegion yellowImage;
    private TextureAtlas.AtlasRegion redImage;
    private TextureAtlas.AtlasRegion soldier0;
    private TextureAtlas.AtlasRegion soldier1;
    private TextureAtlas.AtlasRegion soldier2;
    private TextureAtlas.AtlasRegion soldier3;
    private TextureAtlas.AtlasRegion capital;
    private TextureAtlas.AtlasRegion tree;
    private TextureAtlas.AtlasRegion defenceTower;
    private TextureAtlas.AtlasRegion attackTower;
    private TextureAtlas.AtlasRegion boat;
    private TextureAtlas.AtlasRegion grave;
    private TextureAtlas.AtlasRegion mine;
    private TextureAtlas.AtlasRegion contour;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private TextureAtlas atlas;
    private Cell source;
    private Cell dest;
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
        double temp = Math.floor(x + (ratio * y) + 1);
        double r = Math.floor((Math.floor(2 * x + 1) + temp) / 3);
        double q = Math.floor((temp + Math.floor(-x + (ratio * y) + 1)) / 3);
        r -= (int) q / 2;
        return new int[]{(int) r, (int) q};
    }

    private void setViewport(OrthographicCamera camera, Map map) {

        int width, heigth;
        width = (map.getWidth() * 32) + 16;

        int half = map.getHeigth() / 2;
        if (map.getHeigth() % 2 == 0) {
            heigth = half * 32 + half * 16 + 8;
        } else {
            heigth = half * 32 + half * 16 + 40;
        }
        camera.setToOrtho(false, width, heigth);
    }

    private void drawSpriteEven(TextureAtlas.AtlasRegion sprite, Cell cell) {
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, (cell.getX()) * 32 + 16, (cell.getY() * 32) - (cell.getY() * 8));
        } else {
            batch.draw(sprite, cell.getX() * 32, (cell.getY() * 32) - (cell.getY() * 8));
        }
    }

    private void drawSpriteOdd(TextureAtlas.AtlasRegion sprite, Cell cell) {
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, cell.getX() * 32, (cell.getY() * 32) - (cell.getY() * 8));
        } else {
            batch.draw(sprite, (cell.getX()) * 32 + 16, (cell.getY() * 32) - (cell.getY() * 8));
        }
    }

    private void drawContour(ArrayList<Cell> cells) {
        for (int i = 0; i < cells.size(); i++) {
            if (map.getHeigth() % 2 == 0) {
                drawSpriteEven(contour, cells.get(i));
            } else {
                drawSpriteOdd(contour, cells.get(i));
            }
        }
    }

    private void draw() {
        for (int i = 0; i < map.cells.size(); i++) {
            Cell cell = map.cells.get(i);
            if (map.getHeigth() % 2 == 0) {
                if (cell.isWater()) {
                    drawSpriteEven(blueImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Boat) {
                            drawSpriteEven(boat, cell);
                        } else if (cell.getElementOn() instanceof Mine) {
                            drawSpriteEven(mine, cell);
                        }
                    }
                } else {
                    if (cell.getOwner() == null) drawSpriteEven(greenImage, cell);
                    else if (cell.getOwner() == map.player1) drawSpriteEven(yellowImage, cell);
                    else drawSpriteEven(redImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Soldier) {
                            switch (cell.getElementOn().getLevel()) {
                                case 0:
                                    drawSpriteEven(soldier0, cell);
                                    break;
                                case 1:
                                    drawSpriteEven(soldier1, cell);
                                    break;
                                case 2:
                                    drawSpriteEven(soldier2, cell);
                                    break;
                                case 3:
                                    drawSpriteEven(soldier3, cell);
                                    break;
                                default:
                                    break;
                            }
                        } else if (cell.getElementOn() instanceof Capital) {
                            drawSpriteEven(capital, cell);
                        } else if (cell.getElementOn() instanceof DefenceTower) {
                            drawSpriteEven(defenceTower, cell);
                        } else if (cell.getElementOn() instanceof AttackTower) {
                            drawSpriteEven(attackTower, cell);
                        } else if (cell.getElementOn() instanceof Grave) {
                            drawSpriteEven(grave, cell);
                        } else if (cell.getElementOn() instanceof Tree) {
                            drawSpriteEven(tree, cell);
                        }
                    }
                }


            } else {
                if (cell.isWater()) {
                    drawSpriteOdd(blueImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Boat) {
                            drawSpriteOdd(boat, cell);
                        } else if (cell.getElementOn() instanceof Mine) {
                            drawSpriteOdd(mine, cell);
                        }
                    }
                } else {
                    if (cell.getOwner() == null)
                        drawSpriteOdd(greenImage, cell);
                    else if (cell.getOwner() == map.player1) drawSpriteOdd(yellowImage, cell);
                    else drawSpriteOdd(redImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Soldier) {
                            switch (cell.getElementOn().getLevel()) {
                                case 0:
                                    drawSpriteOdd(soldier0, cell);
                                    break;
                                case 1:
                                    drawSpriteOdd(soldier1, cell);
                                    break;
                                case 2:
                                    drawSpriteOdd(soldier2, cell);
                                    break;
                                case 3:
                                    drawSpriteOdd(soldier3, cell);
                                    break;
                                default:
                                    break;
                            }
                        } else if (cell.getElementOn() instanceof Capital) {
                            drawSpriteOdd(capital, cell);
                        } else if (cell.getElementOn() instanceof DefenceTower) {
                            drawSpriteOdd(defenceTower, cell);
                        } else if (cell.getElementOn() instanceof AttackTower) {
                            drawSpriteOdd(attackTower, cell);
                        } else if (cell.getElementOn() instanceof Grave) {
                            drawSpriteOdd(grave, cell);
                        } else if (cell.getElementOn() instanceof Tree) {
                            drawSpriteOdd(tree, cell);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        greenImage = atlas.findRegion("tile000");
        blueImage = atlas.findRegion("tile001");
        yellowImage = atlas.findRegion("tile002");
        redImage = atlas.findRegion("tile003");
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
        map = new Map(new ArrayList<>(), new Player("Danial", 1, 0, false, 0, new ArrayList<>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<>()));
        map.player1.setTurn(true);
        loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
        Infrastructure.setIsAvailable(true);
        try {
            loader.load(map, false);
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        gameState = new GameState(map, loader, 0, "osef");
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
        if (selected && source.getElementOn() != null) {
            drawContour(source.accessibleCell(map));
        }
        if (territory != null) {
            drawContour(territory.getCells());
        }
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

                int pos[] = getMouseCoord(camera);
                source = map.findCell(pos[0], pos[1]);
                if (source != null && source.getElementOn() instanceof Soldier) {

                    ((Soldier) source.getElementOn()).select();

                    if (source.accessibleCell(map) != null && ((Soldier) source.getElementOn()).select()) {
                        selected = true;
                        territory = null;
                    }

                }
            } else {
                int pos[] = getMouseCoord(camera);
                dest = map.findCell(pos[0], pos[1]);
                ((Soldier) source.getElementOn()).move(source, dest, map);
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
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (screenX < map.getWidth() * 32 && (map.getHeigth() * 32 - screenY) < map.getHeigth() * 32) {
            int pos[] = getMouseCoord(camera);
            Cell cell = map.findCell(pos[0], pos[1]);
            if (cell != null && cell.getOwner() != null && !selected) {
                territory = cell.findTerritory(cell.getOwner());
            } else {
                territory = null;
            }
        }

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
