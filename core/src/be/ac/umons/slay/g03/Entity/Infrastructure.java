package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Infrastructure extends MapElement {
    public static boolean isAvailable = false;

    public Infrastructure(Player owner) {
        super(owner);
    }

    public static void setAvailability(boolean bool) {
        Infrastructure.isAvailable = bool;
    }
}
