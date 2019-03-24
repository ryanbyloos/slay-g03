package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Hud extends Stage {
    World world;
    TextButton nextTurn;

    Hud(World world) {
        this.world = world;

        nextTurn = new TextButton("PASS", ScreenHandler.game.skin);
        nextTurn.setPosition(ScreenHandler.WIDTH - nextTurn.getWidth(), 64);
        nextTurn.setColor(Color.LIGHT_GRAY);

        nextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.gameState.getStates().reset();
                world.gameState.nextTurn();
            }
        });

        this.addActor(nextTurn);
    }

    @Override
    public void draw() {
        super.draw();
    }
}
