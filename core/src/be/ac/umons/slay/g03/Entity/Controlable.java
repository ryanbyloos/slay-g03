package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;

import java.util.ArrayList;

public interface Controlable {
    public boolean belongsTo();
    public void move(Cell cell);
    public ArrayList<Cell> accessibleCell();
    public void select();
}
