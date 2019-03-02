package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.GUI.Menu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main extends Game {
    public Skin skin;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("assets/basic/uiskin.json"));
        setScreen(new Menu(this));
    }
}
