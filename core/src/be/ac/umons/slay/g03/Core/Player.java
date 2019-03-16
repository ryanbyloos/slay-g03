package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Player {
    private String name;
    private int id;
    private int moveNumber;
    private int maxMoveNumber;
    private boolean guest;
    private int score;
    private boolean turn;
    private ArrayList<Territory> territories;

    public Player(String name, int id, int moveNumber, boolean guest, int score, ArrayList<Territory> territories) {
        this.name = name;
        this.moveNumber = moveNumber;
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

    public void removeTerritory(Territory territory) {
        territories.remove(territory);
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

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
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

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }
        Player that = (Player) other;
        return this.getName().equals(that.getName()) &&
                this.getId() == that.getId();

    }

    public int getMaxMoveNumber() {
        return maxMoveNumber;
    }

    public void setMaxMoveNumber(int maxMoveNumber) {
        this.maxMoveNumber = maxMoveNumber;
    }
}
