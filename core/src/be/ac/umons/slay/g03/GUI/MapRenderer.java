package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class MapRenderer extends Game {

    Map map;
    TextureAtlas.AtlasRegion soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    SpriteBatch batch;
    TextureAtlas.AtlasRegion blueHex, greenHex, yellowHex, redHex, tree, grave, contour, capital;
    OrthographicCamera camera;
    TextureAtlas atlas;
    Loader loader;
    GameState gameState;

    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        initTextures(atlas);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    void draw(Map map) {
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
                if (gameState != null) {
                    if ((gameState.getStates().isSelectionMode()) && gameState.getStates().getSource().getElementOn() != null)
                        drawHighlights(gameState.getStates().getSource().accessibleCell(map));
                    if (gameState.getStates().getTerritory() != null)
                        drawHighlights(gameState.getStates().getTerritory().getCells());
                    if (gameState.getStates().isCreationMode() && gameState.getStates().getCreatableCells() != null) {
                        drawHighlights(gameState.getStates().getCreatableCells());
                    }
                }
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
                } else if (cell.getElementOn() instanceof AttackTower) {
                    drawSprite(parity, 10, attackTower, cell);
                } else if (cell.getElementOn() instanceof DefenceTower) {
                    drawSprite(parity, 10, defenceTower, cell);
                } else if (cell.getElementOn() instanceof Grave) {
                    drawSprite(parity, 10, grave, cell);
                } else if (cell.getElementOn() instanceof Tree) {
                    drawSprite(parity, 12, tree, cell);
                }
            }
        }
    }

    int[] getMouseCoord(OrthographicCamera camera) {
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

    void setViewport(OrthographicCamera camera, Map map) {
        int mapH = map.getHeight();
        int width = (map.getWidth() * 32) + 16;
        int height = mapH * 24 + 8 + 32 * (mapH % 2);
        camera.setToOrtho(false, width, height);
    }

    void drawSprite(int parity, TextureAtlas.AtlasRegion sprite, Cell cell) {
        drawSprite(parity, 0, sprite, cell);
    }

    void drawSprite(int parity, int offset, TextureAtlas.AtlasRegion sprite, Cell cell) {
        int EVEN = 0;
        int ODD = 1;
        if (cell.getY() % 2 == 0) {
            this.batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == EVEN) ? 1 : 0), cell.getY() * 24 + offset);
        } else {
            this.batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == ODD) ? 1 : 0), cell.getY() * 24 + offset);
        }
    }

    void drawHighlights(ArrayList<Cell> cells) {
        for (Cell cell : cells)
            drawSprite((map.getHeight() % 2), contour, cell);
    }

    void initTextures(TextureAtlas atlas) {
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
}
