package be.ac.umons.slay.g03.desktop;

import be.ac.umons.slay.g03.GameHandler.ScreenHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = ScreenHandler.WIDTH;
        config.height = ScreenHandler.HEIGHT;
        new LwjglApplication(ScreenHandler.game, config);
    }
}
