package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Infrastructure extends MapElement {
    public static boolean isAvailable = false;

    public Infrastructure(int maintenanceCost, int creationCost, Player player) {
        super(maintenanceCost, creationCost, player);
    }

    public static void setIsAvailable(boolean bool) {
        Infrastructure.isAvailable = bool;
    }
}
