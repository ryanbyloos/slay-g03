package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Initializer;
import com.badlogic.gdx.Screen;

public class ScreenHandler {
    public static int WIDTH;
    public static int HEIGHT;
    public static int BUTTON_WIDTH;
    public static int BUTTON_HEIGHT;

    public static Home home = new Home();
    public static Initializer game = new Initializer();

    static Settings settings;
    static HallOfFame hof;
    static ReplayMenu replay;
    static ReplayRenderer replayScreen;
    static WorldScreen worldScreen;

    static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
