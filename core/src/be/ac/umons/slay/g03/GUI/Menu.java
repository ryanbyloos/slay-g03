package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Menu implements Screen {

    Stage stage;
    TextButton playButton;
    TextButton optionsButton;
    TextButton quitButton;
    TextButton hofButton;
    TextButton loadButton;
    BitmapFont font;
    Main main;

    public Menu(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        stage = new Stage(new FillViewport(Main.WIDTH, Main.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        font = main.skin.getFont("default-font");
        main.skin.addRegions(main.skin.getAtlas());

        playButton = new TextButton("PLAY", main.skin);
        playButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        playButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, Main.HEIGHT / 2 + 2 * Main.BUTTON_HEIGHT);

        optionsButton = new TextButton("OPTIONS", main.skin);
        optionsButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        optionsButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, (Main.HEIGHT + Main.BUTTON_HEIGHT) / 2);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(new Options(main));
            }
        });

        quitButton = new TextButton("QUIT", main.skin);
        quitButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        quitButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, Main.HEIGHT / 2 - Main.BUTTON_HEIGHT);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
}
