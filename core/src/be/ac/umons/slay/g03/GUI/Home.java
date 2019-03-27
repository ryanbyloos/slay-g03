package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.AuthenticationError;
import be.ac.umons.slay.g03.GameHandler.Authenticator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Home implements Screen {

    private Stage stage;
    private Table table;

    @Override
    public void show() {
        try {
            Slay.authenticator = new Authenticator("assets/Register/register.xml");
        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }
        stage = new Stage(new FillViewport(Slay.w, Slay.h));
        Gdx.input.setInputProcessor(stage);

        table = new Table().align(Align.center);
        table.setFillParent(true);
        stage.addActor(table);

        TextButton resumeButton = new TextButton("RESUME", Slay.game.skin);
        TextButton newGameButton = new TextButton("NEW GAME", Slay.game.skin);
        TextButton replayButton = new TextButton("REPLAY", Slay.game.skin);
        TextButton hofButton = new TextButton("HALL OF FAME", Slay.game.skin);
        TextButton settingsButton = new TextButton("SETTINGS", Slay.game.skin);
        TextButton quitButton = new TextButton("QUIT", Slay.game.skin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.worldScreen == null)
                    Slay.worldScreen = new WorldScreen();
                Slay.setScreen(Slay.worldScreen);
            }
        });

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.authScreen == null)
                    Slay.authScreen = new AuthenticatorScreen();
                Slay.setScreen(Slay.authScreen);
            }
        });

        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.replay == null)
                    Slay.replay = new ReplayMenu();
                Slay.setScreen(Slay.replay);
            }
        });
        hofButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.hof == null)
                    Slay.hof = new HallOfFame();
                Slay.setScreen(Slay.hof);
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.settings == null)
                    Slay.settings = new Settings();
                Slay.setScreen(Slay.settings);
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        addToTable(table, resumeButton, newGameButton, replayButton, hofButton, settingsButton, quitButton);
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

    private void addToTable(Table table, Actor... actors) {
        for (Actor actor : actors) {
            table.add(actor).pad(Slay.h / 48).width(Slay.buttonW).height(Slay.buttonH);
            table.row();
        }
    }
}
