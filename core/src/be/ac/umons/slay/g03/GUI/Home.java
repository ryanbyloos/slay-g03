package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Home implements Screen {

    private Stage stage;

    @Override
    public void show() {

        stage = new Stage(new FillViewport(ScreenHandler.WIDTH, ScreenHandler.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        MenuButton resumeButton = new MenuButton("RESUME", ScreenHandler.game.skin, 1);
        MenuButton newGameButton = new MenuButton("NEW GAME", ScreenHandler.game.skin, 2);
        MenuButton replayButton = new MenuButton("REPLAY", ScreenHandler.game.skin, 3);
        MenuButton hofButton = new MenuButton("HALL OF FAME", ScreenHandler.game.skin, 4);
        MenuButton settingsButton = new MenuButton("SETTINGS", ScreenHandler.game.skin, 5);
        MenuButton quitButton = new MenuButton("QUIT", ScreenHandler.game.skin, 6);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ScreenHandler.worldScreen == null)
                    ScreenHandler.worldScreen = new WorldScreen();
                ScreenHandler.setScreen(ScreenHandler.worldScreen);
            }
        });

        replayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ScreenHandler.replay == null)
                    ScreenHandler.replay = new ReplayMenu();
                ScreenHandler.setScreen(ScreenHandler.replay);
            }
        });
        hofButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ScreenHandler.hof == null)
                    ScreenHandler.hof = new HallOfFame();
                ScreenHandler.setScreen(ScreenHandler.hof);
            }
        });
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ScreenHandler.settings == null)
                    ScreenHandler.settings = new Settings();
                ScreenHandler.setScreen(ScreenHandler.settings);
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
}
