package be.ac.umons.slay.g03.Core;

import java.util.ArrayList;

public class Map {
    public ArrayList<Cell> cells;
    public Player player1;
    public Player player2;

    public Map(ArrayList<Cell> cells, Player player1, Player player2) {
        this.cells = cells; this.player1 = player1 ; this.player2 = player2;
    }
    public Cell findMapElement(int x, int y){
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(0);
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }
    public static int getWaterTilesNumber(){
        return 0;
    }


}
