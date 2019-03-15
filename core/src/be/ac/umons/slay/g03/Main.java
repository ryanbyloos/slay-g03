package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.GUI.ScreenHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main extends Game {
    public Skin skin;
    private Preferences prefs;

    @Override
    public void create() {
        prefs = Gdx.app.getPreferences("settings");
        if (!prefs.getBoolean("initialized")) {
            prefs.putBoolean("initialized", true);
            prefs.putInteger("width", 640);
            prefs.putInteger("height", 640);
            prefs.flush();
        }
        ScreenHandler.WIDTH = prefs.getInteger("width");
        ScreenHandler.HEIGHT = prefs.getInteger("height");
        ScreenHandler.BUTTON_WIDTH = ScreenHandler.WIDTH / 4;
        ScreenHandler.BUTTON_HEIGHT = ScreenHandler.HEIGHT / 12;

//        skin = new Skin(Gdx.files.internal("assets/flat-earth/flat-earth-ui.json"));
        skin = new Skin(Gdx.files.internal("assets/pixel-theme/skin/skin.json"));
        skin.addRegions(skin.getAtlas());
        Gdx.graphics.setWindowedMode(ScreenHandler.WIDTH, ScreenHandler.HEIGHT);
        setScreen(ScreenHandler.menu);
    }
}
