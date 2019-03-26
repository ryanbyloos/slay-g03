package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MenuScreen implements Screen {
    Stage stage;
    Table table;

    @Override
    public void show() {
        stage = new Stage(new FillViewport(Slay.w, Slay.h));
        Gdx.input.setInputProcessor(stage);

        table = new Table().bottom().left().pad(10);
        table.setFillParent(true);
        stage.addActor(table);

        TextButton returnButton = new TextButton("RETURN", Slay.game.skin);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Slay.setScreen(Slay.home);
            }
        });
        table.add(returnButton).width(Slay.buttonW).height(Slay.buttonH);

        stage.addActor(table);
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
