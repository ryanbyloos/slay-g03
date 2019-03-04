package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.ScreenHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MenuScreen implements Screen {
    public Stage stage;

    @Override
    public void show() {
        stage = new Stage(new FillViewport(ScreenHandler.WIDTH, ScreenHandler.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        SlayButton returnButton = new SlayButton("RETURN", ScreenHandler.game.skin);
        returnButton.setSize(ScreenHandler.BUTTON_WIDTH, ScreenHandler.BUTTON_HEIGHT);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenHandler.setScreen(ScreenHandler.menu);
            }
        });
        stage.addActor(returnButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void addActor(Actor actor) {
        stage.addActor(actor);
    }
}
