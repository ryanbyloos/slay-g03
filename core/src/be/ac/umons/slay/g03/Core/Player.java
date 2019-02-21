package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Player {
    private String name;
    private int id;
    private int numberOfActions;
    private boolean guest;
    private int score = 0;
    private ArrayList<Territory> territories;

    public Player(String name, int id, int numberOfActions, boolean guest, int score, ArrayList<Territory> territories) {
        this.name = name;
        this.numberOfActions = numberOfActions;
        this.guest = guest;
        this.score = score;
        this.territories = territories;
        this.id = id;
    }

    public void checkTerritory() {

    }

    public boolean isOver() {
        return false;
    }

    public void computeScore() {

    }

    public void surrend() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public boolean isGuest() {
        return guest;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }
        Player that = (Player) other;
        return this.getName().equals(that.getName())
                && this.getId() == that.getId()
                && this.getNumberOfActions() == that.getNumberOfActions()
                && this.isGuest() == that.isGuest()
                && this.getScore() == that.getScore()
                && this.getTerritories() == that.getTerritories();
    }
}
