package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Initializer;
import com.badlogic.gdx.Screen;

public class Slay {
    public static int w;
    public static int h;
    public static int buttonW;
    public static int buttonH;

    public static Home home = new Home();
    public static Initializer game = new Initializer();

    static Settings settings;
    static HallOfFame hof;
    static AuthenticatorScreen authScreen;
    static ReplayMenu replay;
    static ReplayRenderer replayScreen;
    static WorldScreen worldScreen;

    static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
