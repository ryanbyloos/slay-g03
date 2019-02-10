package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class DefenceTower extends Infrastructure {
    private int level;

    public DefenceTower(int maintenanceCost, int creationCost, Player player) {
        super(maintenanceCost, creationCost, player);
    }

    public void levelUp(){

    }
    public boolean belongsTo(){
        return false;
    }
}
