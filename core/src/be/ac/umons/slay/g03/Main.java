package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.GameHandler.ScreenHandler;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main extends Game {
    public Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("assets/basic/uiskin.json"));
        skin.addRegions(ScreenHandler.game.skin.getAtlas());
        setScreen(ScreenHandler.menu);
    }
}
