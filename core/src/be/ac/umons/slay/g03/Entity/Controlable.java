package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;

public interface Controlable {

    void move(Cell source, Cell destination, Map map);

    boolean select();
}
