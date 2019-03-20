package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class Hud extends Stage {

    ImageButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    World world;
    private int w = ScreenHandler.WIDTH;
    private int h = ScreenHandler.HEIGHT;

    Hud(World world) {
        this.world = world;
        initImageButtons();
        this.addActor(soldier0);
    }

    public void initImageButtons() {
        soldier0 = new ImageButton(new TextureRegionDrawable(new TextureRegion(world.soldier0)));
        soldier0.setPosition(w / 24, h / 20);
        soldier0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.creationMode = !world.creationMode;
            }
        });
    }
}
