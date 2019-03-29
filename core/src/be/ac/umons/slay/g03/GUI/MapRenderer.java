package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Entity.*;
import be.ac.umons.slay.g03.GameHandler.GameState;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Classe gerant l'affichage des niveaux.
 */
public class MapRenderer extends Game {

    Map map;
    TextureAtlas.AtlasRegion soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    SpriteBatch batch;
    OrthographicCamera camera;
    GameState gameState;

    private TextureAtlas.AtlasRegion blueHex, greenHex, yellowHex, redHex, tree, grave, contour, capital;
    private TextureAtlas atlas;

    @Override
    public void create() {
        String atlasPath = "assets/Sprites/spritesheet.atlas";
        atlas = new TextureAtlas(atlasPath);
        initTextures(atlas);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    /**
     * @param map Dessine les elements contenus dans map.
     */
    void draw(Map map) {
        int parity = map.getHeight() % 2;
        for (int i = 0; i < map.getCells().size(); i++) {
            Cell cell = map.getCells().get(i);
            if (cell.isWater()) {
                drawSprite(parity, blueHex, cell);
                if (cell.getElementOn() != null) {
                    if (cell.getElementOn() instanceof Boat) {
                        drawSprite(parity, boat, cell);
                    } else if (cell.getElementOn() instanceof Mine && cell.getElementOn().getOwner().isTurn()) {
                        drawSprite(parity, mine, cell);
                    }
                }
            } else {
                if (cell.getOwner() == null) drawSprite(parity, greenHex, cell);
                else if (cell.getOwner() == map.getPlayer1()) drawSprite(parity, yellowHex, cell);
                else drawSprite(parity, redHex, cell);
                if (gameState != null) {
                    if (gameState.getStates().isTerritorySelected()) {
                        if (!(gameState.getStates().isBoatCreation() || gameState.getStates().isMineCreation() || gameState.getStates().isOtherCreation())) {
                            if (gameState.getStates().getTerritoryLoaded().getCells() != null)
                                drawHighlights(gameState.getStates().getTerritoryLoaded().getCells());
                        }
                        if (gameState.getStates().isOtherCreation()) {
                            if (gameState.getStates().getDisplayCells() != null)
                                drawHighlights(gameState.getStates().getDisplayCells());
                        } else if (gameState.getStates().isBoatCreation() || gameState.getStates().isMineCreation()) {
                            if (gameState.getStates().getDisplayCells() != null)
                                drawHighlights(gameState.getStates().getDisplayCells());
                        }
                    } else if (gameState.getStates().isUpgradeAble()) {
                        ArrayList<Cell> hold = new ArrayList<>();
                        if (gameState.getStates().getHold() != null) {
                            hold.add(gameState.getStates().getHold());
                            drawHighlights(hold);
                        }
                    }
                    if (gameState.getStates().isSoldierSelected()) {
                        if (gameState.getStates().getHold() != null && gameState.getStates().getHold().accessibleCell(map) != null) {
                            drawHighlights(gameState.getStates().getHold().accessibleCell(map));
                        }
                    } else if (gameState.getStates().isBoatSelected()) {
                        if (gameState.getStates().isDeployMode() && gameState.getStates().getHold().adjacentCell(map, gameState.getStates().getHold(), false) != null)
                            drawHighlights(gameState.getStates().getHold().adjacentCell(map, gameState.getStates().getHold(), false));
                        else if (gameState.getStates().getHold().adjacentCell(map, gameState.getStates().getHold(), true) != null)
                            drawHighlights(gameState.getStates().getHold().adjacentCell(map, gameState.getStates().getHold(), true));
                    } else if (gameState.getStates().isAttackTowerSelected()) {
                        if (gameState.getStates().getHold().towerRange(map) != null)
                            drawHighlights(gameState.getStates().getHold().towerRange(map));
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

    /**
     * @param camera Camera utilisee en jeu.
     * @return Renvoie les coordonnees de la souris par rapport au plateau.
     */
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

    /**
     * @param camera Camera utilise en jeu.
     * @param map    La carte avec laquelle la camera est calibree
     */
    void setViewport(OrthographicCamera camera, Map map) {
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
            this.batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == EVEN) ? 1 : 0), cell.getY() * 24 + offset);
        } else {
            this.batch.draw(sprite, cell.getX() * 32 + 16 * ((parity == ODD) ? 1 : 0), cell.getY() * 24 + offset);
        }
    }

    private void drawHighlights(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            if (cell != null)
                drawSprite((map.getHeight() % 2), contour, cell);
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
}
