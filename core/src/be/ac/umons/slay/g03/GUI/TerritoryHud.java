package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

class TerritoryHud extends Stage {

    HudButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    HudButton[] buttons;
    Table table;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    World world;

    private float w = Slay.w;
    private float h = Slay.h;

    TerritoryHud(World world) {
        this.world = world;
        initActors();
    }

    private void initActors() {
        soldier0 = new HudButton(10, Slay.game.skin);
        soldier1 = new HudButton(20, Slay.game.skin);
        soldier2 = new HudButton(40, Slay.game.skin);
        soldier3 = new HudButton(80, Slay.game.skin);
        attackTower = new HudButton(25, Slay.game.skin);
        defenceTower = new HudButton(10, Slay.game.skin);
        boat = new HudButton(25, Slay.game.skin);
        mine = new HudButton(20, Slay.game.skin);

        table = new Table().center().bottom().padBottom(h / 150);
        table.setFillParent(true);
        this.addActor(table);

        this.buttons = new HudButton[]{soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine};
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        for (HudButton button : buttons) {
            button.getLabel().setAlignment(Align.right);
            table.add(button).padLeft(w / 64).padRight(w / 64).width(w / 12).height(h / 14);
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
        if (world.gameState.getStates().isTerritorySelected()) {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(0, 0, w, h / 12);
            shapeRenderer.end();

            super.draw();

            batch.begin();
            int i = 0;
            for (TextureAtlas.AtlasRegion image : buttonImages) {
                batch.draw(new TextureRegion(image), (float) (w / 14) + i, (float) (h / 65));
                i += w / 32 + w / 12;
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
        }
        batch.begin();
        Slay.game.font.draw(batch, "Player: " + world.map.playingPlayer().getName(), (float) (w - w / 8), h);
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
                    if(Infrastructure.isAvailable && (name.equals("mine") || (name.equals("boat")))){
                    }
                    else {
                        world.gameState.getStates().setCreatableCells(world.gameState.getStates().getTerritory().accesibleCellToCreateUnit(world.gameState.getMap()));
                    }

                }
                world.gameState.setElementToBuild(name);
            }
        };
    }

    private void showTerritoryInfo(SpriteBatch batch, Territory territory) {
        String money = "Money: " + territory.findCapital().getMoney();
        String gain = "Gain: " + territory.getGainThisTurn();
        Slay.game.font.draw(batch, money + " " + gain, 0, h);
    }

}
