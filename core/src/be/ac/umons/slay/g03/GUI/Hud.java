package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class Hud extends Stage {

    ImageButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    TextButton button;
    World world;
    private int w = ScreenHandler.WIDTH;
    private int h = ScreenHandler.HEIGHT;

    Hud(World world) {
        this.world = world;
        initActors();
    }

    private void initActors() {
        soldier0 = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.soldier0)));
        soldier1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.soldier1)));
        soldier2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.soldier2)));
        soldier3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.soldier3)));
        defenceTower = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.defenceTower)));
        attackTower = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.attackTower)));
        boat = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.boat)));
        mine = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.mine)));

        ImageButton buttons[] = {soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine};
        button = new TextButton("", ScreenHandler.game.skin);
        button.setSize(ScreenHandler.WIDTH, 48);
        this.addActor(button);
        int i = 0;
        for (ImageButton button : buttons) {
            button.setPosition(w / 24 + i, 6);
            i += 48;
            this.addActor(button);
        }
        soldier0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.creationMode = !world.creationMode;
            }
        });
    }
}
