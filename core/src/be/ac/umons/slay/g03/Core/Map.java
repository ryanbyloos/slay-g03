package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.MapElement;
import be.ac.umons.slay.g03.Entity.Soldier;

import java.util.ArrayList;

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

    public Cell findCell(int x, int y) {
        for (Cell cell : getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    public Cell findCell(MapElement mapElement){
        for (Cell cell: cells
             ) {
            if(cell.getElementOn() != null && cell.getElementOn() == (mapElement)){
                return cell;
            }
        }
        return null;
    }

    public static int getWaterTilesNumber() {
        return 0;
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
