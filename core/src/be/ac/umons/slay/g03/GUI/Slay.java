package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.Authenticator;
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
    static WorldScreen worldScreen;
    static Authenticator authenticator;
    static RegisterScreen registerScreen;
    static LevelPicker levelPicker;
    static VictoryScreen victoryScreen;
    static PlayerModificationScreen playerModificationScreen;

    static void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
