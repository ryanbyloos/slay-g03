package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;

import java.util.ArrayList;

public interface Controlable {
    boolean belongsTo();

    void move(Cell source, Cell destination);

    ArrayList<Cell> accessibleCell(Map map, Cell source);

    void select(Map map, Cell source);
}
