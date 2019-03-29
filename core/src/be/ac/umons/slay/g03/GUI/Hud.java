package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Hud permettant d'afficher le bouton pour passer son tour ainsi que les boutons pour les infrastructures
 */
public class Hud extends Stage {
    World world;
    TextButton nextTurn, deployButton, levelUpButton;
    Table table, boatTable, towerTable;

    /**
     * @param world Monde sur lequel les boutons du hud feront effet.
     */
    Hud(World world) {
        this.world = world;

        table = new Table().bottom().right().padBottom(Slay.h / 12);
        table.setFillParent(true);


        nextTurn = new TextButton("PASS", Slay.game.skin);
        nextTurn.setColor(Color.LIGHT_GRAY);

        boatTable = new Table().bottom().right().padBottom(Slay.h / 12 + nextTurn.getHeight());
        boatTable.setFillParent(true);

        towerTable = new Table().bottom().right().padBottom(Slay.h / 12 + nextTurn.getHeight());
        towerTable.setFillParent(true);


        nextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.gameState.getStates().reset();
                world.gameState.nextTurn();
            }
        });

        table.add(nextTurn);
        deployButton = new TextButton("DEPLOY", Slay.game.skin);
        deployButton.setColor(Color.LIGHT_GRAY);

        levelUpButton = new TextButton("LEVEL UP", Slay.game.skin);
        levelUpButton.setColor(Color.LIGHT_GRAY);

        this.addActor(towerTable);
        this.addActor(boatTable);
        this.addActor(table);
    }

    @Override
    public void draw() {
        super.draw();
        if (!world.gameState.getStates().isBoatSelected()) {
            boatTable.clearChildren();
            deployButton = null;
        } else if (deployButton == null) {
            deployButton = new TextButton("DEPLOY", Slay.game.skin);
            deployButton.setColor(Color.LIGHT_GRAY);

            deployButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    world.gameState.getStates().setDeployMode(true);
                }
            });
            boatTable.add(deployButton);
        }

        if (!world.gameState.getStates().isUpgradeAble()) {
            towerTable.clearChildren();
            levelUpButton = null;
        } else if (levelUpButton == null) {
            int cost = world.gameState.getStates().getHold().getElementOn().getCreationCost() * 2;
            levelUpButton = new TextButton("LEVEL UP FOR " + cost, Slay.game.skin);
            if (cost > world.gameState.getStates().getHold().findTerritory().findCapital().getMoney())
                levelUpButton.setColor(Color.RED);
            else
                levelUpButton.setColor(Color.LIGHT_GRAY);

            levelUpButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    world.gameState.getStates().getHold().levelUpTower();
                    world.gameState.getStates().reset();
                    world.gameState.getStates().setHold(null);
                    world.gameState.getStates().setUpgradeAble(false);
                }
            });
            towerTable.add(levelUpButton);
        }
    }
}
