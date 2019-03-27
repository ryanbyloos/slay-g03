package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.GUI.Slay;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Initializer extends Game {
    public Skin skin;
    public BitmapFont font;
    public Preferences preferences;

    @Override
    public void create() {
        Slay.w = Gdx.graphics.getWidth();
        Slay.h = Gdx.graphics.getHeight();
        Slay.buttonW = Slay.w / 4;
        Slay.buttonH = Slay.h / 12;

        preferences = Gdx.app.getPreferences("settings");
        skin = new Skin(Gdx.files.internal("assets/pixel-theme/skin/skin.json"));
        skin.addRegions(skin.getAtlas());
        font = skin.getFont("default");
        Gdx.graphics.setWindowedMode(Slay.w, Slay.h);
        setScreen(Slay.home);
        Infrastructure.setAvailability(Slay.game.preferences.getBoolean("infrastructures", false));
    }
}
