package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Infrastructure extends MapElement{
    public static boolean InfrastructureAvailable = false;

    public static boolean isInfrastructureAvailable() {
        return InfrastructureAvailable;
    }
    public Infrastructure(int maintenanceCost, int creationCost, Player player){
        super(maintenanceCost, creationCost, player);
    }

    public static void setInfrastructureAvailable(boolean infrastructureAvailable) {
        InfrastructureAvailable = infrastructureAvailable;
    }
}
