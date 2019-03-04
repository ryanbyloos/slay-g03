package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.GUI.Menu;
import be.ac.umons.slay.g03.GUI.Options;
import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Screen;

public class ScreenHandler {
    public static Menu menu = new Menu();
    public static Options options;
    public static Main game = new Main();

    public static int WIDTH;
    public static int HEIGHT;
    public static int BUTTON_WIDTH;
    public static int BUTTON_HEIGHT;

    public static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
