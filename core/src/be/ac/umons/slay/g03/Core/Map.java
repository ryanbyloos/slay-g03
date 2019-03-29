package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.MapElement;

import java.util.ArrayList;

/**
 * Classe instanciant une map contenant le 2 Players, les dimensions de celle-ci ainsi qu'une ArrayList de toutes les Cells là composant
 *
 */
public class Map {
    private ArrayList<Cell> cells;
    private Player player1;
    private Player player2;
    private int width;
    private int height;

    public Map(ArrayList<Cell> cells, Player player1, Player player2) {
        this.setCells(cells);
        this.setPlayer1(player1);
        this.setPlayer2(player2);
    }

    /**
     * permet de trouver un Cell avec ses coordonées (x,y)
     * @param x coordonnée x de la Cell cherchée
     * @param y coordonnée y de la Cell cherchée
     * @return La Cell cherchée
     */
    public Cell findCell(int x, int y) {
        for (Cell cell : getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    /**
     * permet de trouver la Cell du MapElement mit en parametre
     * @param mapElement MapElement dont on souhaite connaître la Cell
     * @return la Cell où se trouve le MapElement
     */
    public Cell findCell(MapElement mapElement){
        for (Cell cell: cells
             ) {
            if(cell.getElementOn() != null && cell.getElementOn() == (mapElement)){
                return cell;
            }
        }
        return null;
    }

    /**
     * @return le Player qui est en train de jouer
     */
    public Player playingPlayer() {
        if (getPlayer1().isTurn())
            return getPlayer1();
        if (getPlayer2().isTurn())
            return getPlayer2();
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

}
