package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;

import java.util.ArrayList;

public interface Controlable {
    boolean belongsTo();
    void move(Cell cell);
    ArrayList<Cell> accessibleCell();
    void select();
}
