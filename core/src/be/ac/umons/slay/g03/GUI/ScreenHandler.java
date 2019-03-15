package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.Screen;

public class ScreenHandler {
    public static int WIDTH;
    public static int HEIGHT;
    public static int BUTTON_WIDTH;
    public static int BUTTON_HEIGHT;

    public static Menu menu = new Menu();
    public static Main game = new Main();

    static Settings settings;
    static HallOfFame hof;
    static ReplayScreen replay;
    static WorldScreen worldScreen;

    static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
