package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.Grave;

import java.util.ArrayList;

/**
 * Classe instanciant un Player, contenant son pseudo, son id, son nombre de mouvement, si il est un invite et si c'est a son tour
 */
public class Player {
    private String name;
    private int id;
    private int moveNumber;
    private int maxMoveNumber;
    private boolean guest;
    private boolean turn;
    private ArrayList<Territory> territories;

    public Player(String name, int id, int moveNumber, boolean guest, ArrayList<Territory> territories) {
        this.name = name;
        this.moveNumber = moveNumber;
        this.guest = guest;
        this.territories = territories;
        this.id = id;
    }

    /**
     * check si les Teritory du Player sont en faillite
     */
    public void checkTerritory() {
        for (Territory territory : territories
        ) {
            territory.checkcost();
        }
    }

    /**
     * permet de savoir si le joueur a perdu ou non
     * @return vrai si le joueur a perdu
     */
    public boolean isOver() {
        if(territories.size() == 0) return true;

        return false;
    }


    /**
     * retire toutes les tombes de la map
     */
    public void cleanGrave(){
        for (Territory territory: territories
             ) {
            for (Cell cell: territory.getCells()
                 ) {
                if (cell.getElementOn() instanceof Grave){
                    cell.setElementOn(null);
                }
            }
        }
    }

    /**
     * retire le Territory des territories du Player
     * @param territory Territory a retirer
     */
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


    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
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

    @Override
    public String toString() {
        return "Name : " + name + " ID : " + id;
    }
}
