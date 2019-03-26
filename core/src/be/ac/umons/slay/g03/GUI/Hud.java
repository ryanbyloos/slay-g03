package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Hud extends Stage {
    World world;
    TextButton nextTurn;
    Table table;

    Hud(World world) {
        this.world = world;

        table = new Table().bottom().right().padBottom(Slay.h / 12);
        table.setFillParent(true);

        nextTurn = new TextButton("PASS", Slay.game.skin);
        nextTurn.setColor(Color.LIGHT_GRAY);

        nextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.gameState.getStates().reset();
                world.gameState.nextTurn();
            }
        });

        table.add(nextTurn);

        this.addActor(table);
    }

    @Override
    public void draw() {
        super.draw();
    }
}
