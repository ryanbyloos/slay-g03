package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Territory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class Hud extends Stage {

    TextButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    World world;
    private int w = ScreenHandler.WIDTH;
    private int h = ScreenHandler.HEIGHT;

    Hud(World world) {
        this.world = world;
        initActors();
    }

    private void initActors() {
        soldier0 = new TextButton("          10", ScreenHandler.game.skin);
        soldier1 = new TextButton("          20", ScreenHandler.game.skin);
        soldier2 = new TextButton("          30", ScreenHandler.game.skin);
        soldier3 = new TextButton("          40", ScreenHandler.game.skin);
        attackTower = new TextButton("          10", ScreenHandler.game.skin);
        defenceTower = new TextButton("          10", ScreenHandler.game.skin);
        boat = new TextButton("          10", ScreenHandler.game.skin);
        mine = new TextButton("          10", ScreenHandler.game.skin);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        TextButton[] buttons = {soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine};
        int i = 0;
        for (TextButton button : buttons) {
            button.setPosition((float) w / 24 + i, (float) h / 100);
            button.setHeight(38f);
            i += 96;
            this.addActor(button);
        }
        soldier0.addListener(createClickListener("soldier0"));
        soldier1.addListener(createClickListener("soldier1"));
        soldier2.addListener(createClickListener("soldier2"));
        soldier3.addListener(createClickListener("soldier3"));
        defenceTower.addListener(createClickListener("defenceTower"));
        attackTower.addListener(createClickListener("attackTower"));
        boat.addListener(createClickListener("boat"));
        mine.addListener(createClickListener("mine"));
    }

    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(0, 0, w, 48);

        shapeRenderer.end();

        super.draw();

        TextureAtlas.AtlasRegion[] buttonImages = {world.soldier0, world.soldier1, world.soldier2, world.soldier3, world.defenceTower, world.attackTower, world.boat, world.mine};
        batch.begin();
        int i = 0;
        for (TextureAtlas.AtlasRegion image : buttonImages) {
            batch.draw(new TextureRegion(image), (float) (w / 22) + i, (float) (h / 65));
            i += 96;
        }
        Territory territory = world.gameState.getStates().getTerritory();
        if (territory != null) {
            showTerritoryInfo(batch, territory);
        }
        batch.end();

    }

    private ClickListener createClickListener(String name) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (world.gameState.getStates().isTerritorySelected())
                    world.gameState.getStates().setCreationMode(!world.gameState.getStates().isCreationMode());
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
