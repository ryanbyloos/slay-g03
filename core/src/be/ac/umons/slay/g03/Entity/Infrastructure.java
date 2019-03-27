package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.GUI.Slay;

public class Infrastructure extends MapElement {
    public static boolean isAvailable = Slay.game.preferences.getBoolean("infrastructures", false);

    public Infrastructure(Player owner) {
        super(owner);
    }

    public static void setAvailability(boolean bool) {
        Infrastructure.isAvailable = bool;
    }
}
