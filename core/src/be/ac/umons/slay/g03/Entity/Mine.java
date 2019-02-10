package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Mine extends Infrastructure {
    private boolean visible;

    public Mine(boolean visible, int maintenanceCost, int creationCost, Player player) {
        super(maintenanceCost, creationCost, player);
        this.visible = visible;
    }
    public boolean checkExplosion(){
        return false;
    }
    private boolean explode(Boat boat){
        return false;
    }
}
