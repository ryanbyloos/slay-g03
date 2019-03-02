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
    TextButton resumeButton;
    TextButton newGameButton;
    TextButton settingsButton;
    TextButton replayButton;
    TextButton hofButton;
    TextButton quitButton;
    BitmapFont font;
    Main main;

    public Menu(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        int centerWidth = (main.WIDTH - main.BUTTON_WIDTH) / 2;
        int unitHeight = (int) (1.5 * main.BUTTON_HEIGHT);

        stage = new Stage(new FillViewport(Main.WIDTH, Main.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        font = main.skin.getFont("default-font");
        main.skin.addRegions(main.skin.getAtlas());

        resumeButton = new TextButton("RESUME", main.skin);
        resumeButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        resumeButton.setPosition(centerWidth, 7 * unitHeight);

        newGameButton = new TextButton("NEW GAME", main.skin);
        newGameButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        newGameButton.setPosition(centerWidth, 6 * unitHeight);

        replayButton = new TextButton("REPLAY", main.skin);
        replayButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        replayButton.setPosition(centerWidth, 5 * unitHeight);

        hofButton = new TextButton("HALL OF FAME", main.skin);
        hofButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        hofButton.setPosition(centerWidth, 4 * unitHeight);

        settingsButton = new TextButton("SETTINGS", main.skin);
        settingsButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        settingsButton.setPosition(centerWidth, 3 * unitHeight);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setScreen(new Options(main));
            }
        });

        quitButton = new TextButton("QUIT", main.skin);
        quitButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        quitButton.setPosition(centerWidth, 2 * unitHeight);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        stage.addActor(resumeButton);
        stage.addActor(newGameButton);
        stage.addActor(replayButton);
        stage.addActor(hofButton);
        stage.addActor(settingsButton);
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
