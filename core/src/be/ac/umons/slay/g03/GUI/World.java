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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import javafx.scene.input.MouseButton;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.util.ArrayList;

public class World extends ApplicationAdapter {
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
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private TextureAtlas atlas;
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

    private void drawSpriteEven(Batch batch, TextureAtlas.AtlasRegion sprite, Cell cell) {
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, (cell.getX()) * 32 + 16, (cell.getY() * 32) - (cell.getY() * 8));
        } else {
            batch.draw(sprite, cell.getX() * 32, (cell.getY() * 32) - (cell.getY() * 8));
        }
    }

    private void drawSpriteOdd(Batch batch, TextureAtlas.AtlasRegion sprite, Cell cell) {
        if (cell.getY() % 2 == 0) {
            batch.draw(sprite, cell.getX() * 32, (cell.getY() * 32) - (cell.getY() * 8));
        } else {
            batch.draw(sprite, (cell.getX()) * 32 + 16, (cell.getY() * 32) - (cell.getY() * 8));
        }
    }


    private void draw(Batch batch) {
        for (int i = 0; i < map.cells.size(); i++) {
            Cell cell = map.cells.get(i);
            if (map.getHeigth() % 2 == 0) {
                if (cell.isWater()) {
                    drawSpriteEven(batch, blueImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Boat) {
                            drawSpriteEven(batch, boat, cell);
                        } else if (cell.getElementOn() instanceof Mine) {
                            drawSpriteEven(batch, mine, cell);
                        }
                    }
                } else {
                    if (cell.getOwner() == null) drawSpriteEven(batch, greenImage, cell);
                    else if (cell.getOwner() == map.player1) drawSpriteEven(batch, yellowImage, cell);
                    else drawSpriteEven(batch, redImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Soldier) {
                            switch (cell.getElementOn().getLevel()) {
                                case 0:
                                    drawSpriteEven(batch, soldier0, cell);
                                    break;
                                case 1:
                                    drawSpriteEven(batch, soldier1, cell);
                                    break;
                                case 2:
                                    drawSpriteEven(batch, soldier2, cell);
                                    break;
                                case 3:
                                    drawSpriteEven(batch, soldier3, cell);
                                    break;
                                default:
                                    break;
                            }
                        } else if (cell.getElementOn() instanceof Capital) {
                            drawSpriteEven(batch, capital, cell);
                        } else if (cell.getElementOn() instanceof DefenceTower) {
                            drawSpriteEven(batch, defenceTower, cell);
                        } else if (cell.getElementOn() instanceof AttackTower) {
                            drawSpriteEven(batch, attackTower, cell);
                        } else if (cell.getElementOn() instanceof Grave) {
                            drawSpriteEven(batch, grave, cell);
                        } else if (cell.getElementOn() instanceof Tree) {
                            drawSpriteEven(batch, tree, cell);
                        }
                    }
                }


            } else {
                if (cell.isWater()) {
                    drawSpriteOdd(batch, blueImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Boat) {
                            drawSpriteOdd(batch, boat, cell);
                        } else if (cell.getElementOn() instanceof Mine) {
                            drawSpriteOdd(batch, mine, cell);
                        }
                    }
                } else {
                    if (cell.getOwner() == null)
                        drawSpriteOdd(batch, greenImage, cell);
                    else if (cell.getOwner() == map.player1) drawSpriteOdd(batch, yellowImage, cell);
                    else drawSpriteOdd(batch, redImage, cell);
                    if (cell.getElementOn() != null) {
                        if (cell.getElementOn() instanceof Soldier) {
                            switch (cell.getElementOn().getLevel()) {
                                case 0:
                                    drawSpriteOdd(batch, soldier0, cell);
                                    break;
                                case 1:
                                    drawSpriteOdd(batch, soldier1, cell);
                                    break;
                                case 2:
                                    drawSpriteOdd(batch, soldier2, cell);
                                    break;
                                case 3:
                                    drawSpriteOdd(batch, soldier3, cell);
                                    break;
                                default:
                                    break;
                            }
                        } else if (cell.getElementOn() instanceof Capital) {
                            drawSpriteOdd(batch, capital, cell);
                        } else if (cell.getElementOn() instanceof DefenceTower) {
                            drawSpriteOdd(batch, defenceTower, cell);
                        } else if (cell.getElementOn() instanceof AttackTower) {
                            drawSpriteOdd(batch, attackTower, cell);
                        } else if (cell.getElementOn() instanceof Grave) {
                            drawSpriteOdd(batch, grave, cell);
                        } else if (cell.getElementOn() instanceof Tree) {
                            drawSpriteOdd(batch, tree, cell);
                        }
                    }
                }
            }
        }
    }
    private Vector3 getMouseLoc(){
        Vector3 mouseLocation = new Vector3();
        mouseLocation.x = Gdx.input.getX();
        mouseLocation.y = Gdx.input.getY();
        //mouseLocation.y = Gdx.graphics.getHeight() - Gdx.input.getY()-16;
        return camera.unproject(mouseLocation);
    }


    private int[] pixelToOffset(double x, double y){
        //double q = (2./3 * x - 1./3 * y) / 32;
        double q = (Math.sqrt(3)/3 * x - 1./3 * y) / 32;
        double r = (2./3 * y) / 32;
        Cube cube = new Cube(q, -q-r, r);
        cube.round();
        return cube.toOddCoor();
    }
    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        blueImage = atlas.findRegion("sprite-1");
        greenImage = atlas.findRegion("sprite-land");
        redImage = atlas.findRegion("sprite-3");
        yellowImage = atlas.findRegion("sprite-2");
        soldier0 = atlas.findRegion("sprite-4");
        soldier1 = atlas.findRegion("sprite-5");
        soldier2 = atlas.findRegion("sprite-6");
        soldier3 = atlas.findRegion("sprite-7");
        capital = atlas.findRegion("sprite-11");
        tree = atlas.findRegion("sprite-13");
        defenceTower = atlas.findRegion("sprite-8");
        attackTower = atlas.findRegion("sprite-9");
        boat = atlas.findRegion("sprite-10");
        grave = atlas.findRegion("sprite-14");
        mine = atlas.findRegion("sprite-12");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0, false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2, 0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
        Infrastructure.setIsAvailable(true);
        try {
            loader.load(map, false);
        }
        catch (WrongFormatException e) {
            e.printStackTrace();
        }
        setViewport(camera, map);
    }



    @Override
    public void render() {
        Gdx.gl.glClearColor(145 / 255f, 145 / 255f, 145 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            //Vector3 vector = getMouseLoc();
            //int pos[] = pixelToOffset( vector.x, vector.y);
            int pos[] = pixelToOffset( Gdx.input.getX(),Gdx.input.getY());

        }
        batch.begin();
        draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
