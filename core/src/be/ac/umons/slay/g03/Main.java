package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.GUI.Menu;
import com.badlogic.gdx.Game;

public class Main extends Game {

    public Menu menu = new Menu(this);

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;

    public static int BUTTON_WIDTH = 200;
    public static int BUTTON_HEIGHT = 50;

    @Override
    public void create() {
        setScreen(menu);
    }
}
