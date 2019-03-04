package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.GUI.HallOfFame;
import be.ac.umons.slay.g03.GUI.Menu;
import be.ac.umons.slay.g03.GUI.ReplayScreen;
import be.ac.umons.slay.g03.GUI.Settings;
import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Screen;

public class ScreenHandler {
    public static int WIDTH;
    public static int HEIGHT;
    public static int BUTTON_WIDTH;
    public static int BUTTON_HEIGHT;

    public static Menu menu = new Menu();
    public static Main game = new Main();

    public static Settings settings;
    public static HallOfFame hof;
    public static ReplayScreen replay;

    public static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
