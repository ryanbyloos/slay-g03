package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.ScreenHandler;
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


    @Override
    public void show() {

        stage = new Stage(new FillViewport(ScreenHandler.WIDTH, ScreenHandler.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        font = ScreenHandler.game.skin.getFont("default-font");
        ScreenHandler.game.skin.addRegions(ScreenHandler.game.skin.getAtlas());

        resumeButton = new SlayButton("RESUME", ScreenHandler.game.skin, 1);
        newGameButton = new SlayButton("NEW GAME", ScreenHandler.game.skin, 2);
        replayButton = new SlayButton("REPLAY", ScreenHandler.game.skin, 3);
        hofButton = new SlayButton("HALL OF FAME", ScreenHandler.game.skin, 4);
        settingsButton = new SlayButton("SETTINGS", ScreenHandler.game.skin, 5);
        quitButton = new SlayButton("QUIT", ScreenHandler.game.skin, 6);

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ScreenHandler.options == null)
                    ScreenHandler.options = new Options();
                ScreenHandler.setScreen(ScreenHandler.options);
            }
        });
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
