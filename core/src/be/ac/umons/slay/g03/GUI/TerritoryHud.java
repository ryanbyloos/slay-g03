package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.Soldier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class TerritoryHud extends Stage {

    HudButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    HudButton[] buttons;
    TextButton levelUp;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    World world;
    private int w = ScreenHandler.WIDTH;
    private int h = ScreenHandler.HEIGHT;

    TerritoryHud(World world) {
        this.world = world;
        initActors();
    }

    private void initActors() {
        soldier0 = new HudButton("          10", ScreenHandler.game.skin, 10);
        soldier1 = new HudButton("          20", ScreenHandler.game.skin, 20);
        soldier2 = new HudButton("          40", ScreenHandler.game.skin, 40);
        soldier3 = new HudButton("          80", ScreenHandler.game.skin, 80);
        attackTower = new HudButton("          25", ScreenHandler.game.skin, 25);
        defenceTower = new HudButton("          10", ScreenHandler.game.skin, 10);
        boat = new HudButton("          25", ScreenHandler.game.skin, 25);
        mine = new HudButton("          20", ScreenHandler.game.skin, 20);

        this.buttons = new HudButton[]{soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine};

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        int i = 0;
        for (HudButton button : buttons) {
            button.setPosition((float) w / 24 + i, (float) h / 100);
            button.setHeight(38f);
            i += 96;
            this.addActor(button);
        }

        soldier0.addListener(createClickListener("soldier0", soldier0.getCost()));
        soldier1.addListener(createClickListener("soldier1", soldier1.getCost()));
        soldier2.addListener(createClickListener("soldier2", soldier2.getCost()));
        soldier3.addListener(createClickListener("soldier3", soldier3.getCost()));
        defenceTower.addListener(createClickListener("defenceTower", defenceTower.getCost()));
        attackTower.addListener(createClickListener("attackTower", attackTower.getCost()));
        boat.addListener(createClickListener("boat", boat.getCost()));
        mine.addListener(createClickListener("mine", mine.getCost()));
    }

    @Override
    public void draw() {
        Territory territory = world.gameState.getStates().getTerritory();
        TextureAtlas.AtlasRegion[] buttonImages = {world.soldier0, world.soldier1, world.soldier2, world.soldier3, world.defenceTower, world.attackTower, world.boat, world.mine};

        if (territory != null)
            checkPrice(buttons, territory);
        else if (!world.gameState.getStates().isSelectionMode()) {
            for (HudButton button : buttons)
                button.setColor(Color.GRAY);
        }
        if (world.gameState.getStates().isSelectionMode() || world.gameState.getStates().isTerritorySelected()) {
            levelUp = new TextButton("LEVEL UP", ScreenHandler.game.skin);
            levelUp.setPosition(ScreenHandler.WIDTH - levelUp.getWidth(), 96);

            levelUp.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (world.gameState.getStates().getSource().getElementOn() != null)
                        world.gameState.getStates().getSource().getElementOn().levelUp();
                }
            });
            this.addActor(levelUp);

            if (world.gameState.getStates().getSource()!=null &&  world.gameState.getStates().getSource().getElementOn() instanceof Soldier)
                levelUp.setColor(Color.LIGHT_GRAY);
            else
                levelUp.setColor(Color.RED);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.rect(0, 0, w, 48);
            shapeRenderer.end();

            super.draw();
            batch.begin();
            int i = 0;
            for (TextureAtlas.AtlasRegion image : buttonImages) {
                batch.draw(new TextureRegion(image), (float) (w / 22) + i, (float) (h / 65));
                i += 96;
            }
            batch.end();
        }
        if (territory != null) {
            if ((territory.findCapital().getOwner() == world.map.playingPlayer())) {
                batch.begin();
                showTerritoryInfo(batch, territory);
                batch.end();
            }
        } else if (world.gameState.getStates().isSoldierSelected()) {
            batch.begin();
            showTerritoryInfo(batch, world.gameState.getStates().getSource().findTerritory());
            batch.end();
        } else {
            if (levelUp != null) {
                levelUp.remove();
            }
        }
        batch.begin();
        ScreenHandler.game.font.draw(batch, "Player: " + world.map.playingPlayer().getName(), (float) (w - w / 8), h);
        batch.end();
    }

    private void checkPrice(HudButton[] buttons, Territory territory) {
        for (HudButton button : buttons) {
            if (territory.findCapital().getMoney() < button.getCost()) {
                button.setColor(Color.RED);
            }
        }
    }

    private ClickListener createClickListener(String name, int cost) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (world.gameState.getStates().isTerritorySelected() && world.gameState.getStates().getTerritory().findCapital().getMoney() >= cost){
                    world.gameState.getStates().setCreationMode(!world.gameState.getStates().isCreationMode());
                    world.gameState.getStates().setCreatableCells(world.gameState.getStates().getTerritory().accesibleCellToCreateUnit(world.gameState.getMap()));
                }

                world.gameState.setElementToBuild(name);
            }
        };
    }

    private void showTerritoryInfo(SpriteBatch batch, Territory territory) {
        String money = "Money: " + territory.findCapital().getMoney();
        String gain = "Gain: " + territory.getGainThisTurn();
        ScreenHandler.game.font.draw(batch, money + " " + gain, 0, h);
    }

}
