package be.ac.umons.slay.g03.desktop;

import be.ac.umons.slay.g03.GUI.ScreenHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    private static LwjglApplicationConfiguration config;
    public static void main(String[] arg) {
        config = new LwjglApplicationConfiguration();
        config.resizable = false;
        new LwjglApplication(ScreenHandler.game, config);
    }
}
