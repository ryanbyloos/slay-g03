package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Player {
    private String name;
    private int numberOfActions;
    private boolean guest;
    private int score = 0;
    private ArrayList<Territory> territories;

    public Player(String name, int numberOfActions, boolean guest, int score, ArrayList<Territory> territories) {
        this.name = name;
        this.numberOfActions = numberOfActions;
        this.guest = guest;
        this.score = score;
        this.territories = territories;
    }
    public void checkTerritory(){

    }
    public boolean isOver(){
        return false;
    }
    public void computeScore(){

    }
    public void surrend(){

    }
}
