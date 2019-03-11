package be.ac.umons.slay.g03.desktop;

import be.ac.umons.slay.g03.GUI.World;
import be.ac.umons.slay.g03.GameHandler.ScreenHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    static LwjglApplicationConfiguration config;
    public static void main(String[] arg) {
        config = new LwjglApplicationConfiguration();
       //new LwjglApplication(ScreenHandler.game, config);
        new LwjglApplication(new World(), config);
    }
}
