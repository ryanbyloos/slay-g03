package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.GUI.Menu;
import be.ac.umons.slay.g03.GUI.Options;
import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Screen;

public class ScreenHandler {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static int BUTTON_WIDTH = WIDTH / 6;
    public static int BUTTON_HEIGHT = HEIGHT / 12;

    public static Menu menu = new Menu();
    public static Main game = new Main();
    public static Options options;

    public static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
