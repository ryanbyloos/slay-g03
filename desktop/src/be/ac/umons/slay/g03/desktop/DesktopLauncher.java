package be.ac.umons.slay.g03.desktop;

import be.ac.umons.slay.g03.GUI.Menu;
import be.ac.umons.slay.g03.GUI.World;
import be.ac.umons.slay.g03.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Main main = new Main();
		config.width = Main.WIDTH;
		config.height = Main.HEIGHT;
		new LwjglApplication(main, config);
	}
}
