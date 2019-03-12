package be.ac.umons.slay.g03.Core;

import be.ac.umons.slay.g03.Entity.MapElement;

import java.util.ArrayList;


public class Cell {
    private int y;
    private int x;
    private boolean checked;
    private boolean water;
    private Player owner;
    private MapElement elementOn;

    public Cell(int x, int y, boolean checked, boolean water, Player owner, MapElement elementOn) {
        this.x = x;
        this.y = y;
        this.checked = checked;
        this.water = water;
        this.owner = owner;
        this.elementOn = elementOn;
    }

    public void kill() {

    }
    public Territory findTerritory(Player player){
        for (Territory territory : player.getTerritories()) {
            for (Cell cell : territory.getCells()) {
                if(cell.getX() == x && cell.getY() == y){
                    return territory;
                }
            }
        }
        return null;
    }
    //experimental
   /* public ArrayList<Cell> soldierAdjacentsE(Map map,int distance){
        int parity;
        int[][][] evenDirections = new int[][][]{
                {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {0, +1}, {+1, +1}},
                {{+1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, +1}, {0, +1}}
        };
        if(distance == 1){
            ArrayList<Cell> cells = new ArrayList<>();
            parity = y & 1;
            for (int i = 0; i < 6; i++) {
                int[] direction = evenDirections[parity][i];
                Cell cell = map.findCell(x+direction[0], y+direction[1]);
                if(cell != null){
                    cells.add(cell);
                }
            }
            return cells;
        }
        else {
            parity = y & 1;
            ArrayList<Cell> cells = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                int[] direction = evenDirections[parity][i];
                Cell cell = map.findCell(x+direction[0], y+direction[1]);
                if(cell != null){
                    if(!cell.isWater() && cell.getOwner() != null){
                        cells.addAll(cell.soldierAdjacentsE(map, distance-1));
                    }
                    else if(!cell.isWater() && cell.getOwner() == null){
                        cells.add(cell);
                    }
                }
            }
            return cells;
        }
    }*/

    /*public ArrayList<int[]> getAdjacentsEven(ArrayList<int[]> positions, int distance) {
        int parity;
        int[][][] evenDirections = new int[][][]{
                {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {0, +1}, {+1, +1}},
                {{+1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, +1}, {0, +1}}
        };
        if (distance == 1) {
            parity = y & 1;
            for (int i = 0; i < 6; i++) {
                int[] direction = evenDirections[parity][i];
                positions.add(new int[]{x + direction[0], y + direction[1]});
            }
            return positions;
        } else {
            ArrayList<int[]> newPositions = getAdjacentsEven(positions, distance - 1);
            ArrayList<int[]> tmp = new ArrayList<>();
            for (int i = 0; i < newPositions.size(); i++) {
                int[] position = newPositions.get(i);
                parity = position[1] & 1;
                for (int j = 0; j < 6; j++) {
                    int[] direction = evenDirections[parity][j];
                    int[] adjacentPos = new int[]{position[0] + direction[0], position[1] + direction[1]};
                    if (!newPositions.contains(adjacentPos)) {
                        if ((adjacentPos[0] != x || adjacentPos[1] != y)) tmp.add(adjacentPos);
                    }
                }
            }
            newPositions.addAll(tmp);
            return newPositions;
        }
    }*/

   /* public ArrayList<int[]> getAdjacentsOdd(ArrayList<int[]> positions, int distance) {
        int parity;
        int[][][] oddDirections = new int[][][]{
                {{+1, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, +1}, {0, +1}},
                {{+1, 0}, {+1, -1}, {0, -1}, {-1, 0}, {0, +1}, {+1, +1}}
        };
        if (distance == 1) {
            parity = y & 1;
            for (int i = 0; i < 6; i++) {
                int[] direction = oddDirections[parity][i];
                positions.add(new int[]{x + direction[0], y + direction[1]});
            }
            return positions;
        } else {
            ArrayList<int[]> newPositions = getAdjacentsEven(positions, distance - 1);
            ArrayList<int[]> tmp = new ArrayList<>();
            for (int i = 0; i < newPositions.size(); i++) {
                int[] position = newPositions.get(i);
                parity = position[1] & 1;
                for (int j = 0; j < 6; j++) {
                    int[] direction = oddDirections[parity][j];
                    int[] adjacentPos = new int[]{position[0] + direction[0], position[1] + direction[1]};
                    if (!newPositions.contains(adjacentPos)) {
                        if ((adjacentPos[0] != x || adjacentPos[1] != y)) tmp.add(adjacentPos);
                    }
                }
            }
            return newPositions;
        }
    }*/

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public MapElement getElementOn() {
        return elementOn;
    }

    public void setElementOn(MapElement elementOn) {
        this.elementOn = elementOn;
    }
}
