package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Menu implements Screen {

    Stage stage;
    TextButton playButton;
    TextButton optionsButton;
    TextButton quitButton;
    TextButton hofButton;
    TextButton loadButton;
    BitmapFont font;
    Skin skin;
    Main main;

    public Menu(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("assets/basic/uiskin.json"));
        stage = new Stage(new FillViewport(Main.WIDTH, Main.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        font = skin.getFont("default-font");
        skin.addRegions(skin.getAtlas());

        playButton = new TextButton("PLAY", skin);
        playButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        playButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, Main.HEIGHT / 2 + 2 * Main.BUTTON_HEIGHT);

        optionsButton = new TextButton("OPTIONS", skin);
        optionsButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        optionsButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, (Main.HEIGHT + Main.BUTTON_HEIGHT) / 2);

        quitButton = new TextButton("QUIT", skin);
        quitButton.setSize(Main.BUTTON_WIDTH, Main.BUTTON_HEIGHT);
        quitButton.setPosition((Main.WIDTH - Main.BUTTON_WIDTH) / 2, Main.HEIGHT / 2 - Main.BUTTON_HEIGHT);

        stage.addActor(playButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    @Override
    public void render(float delta) {
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
