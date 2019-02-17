package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Map {
    public ArrayList<Cell> cells;
    public Player player1;
    public Player player2;
    private int width;
    private int heigth;

    public Map(ArrayList<Cell> cells, Player player1, Player player2) {
        this.cells = cells;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Cell findCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
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

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
