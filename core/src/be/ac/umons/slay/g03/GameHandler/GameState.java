package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

public class GameState {
    private Map map;
    private Loader loader;
    private int turnPlayed;
    private String logFile;

    public GameState(Map map, Loader loader, int turnPlayed, String logFile) {
        this.map = map;
        this.loader = loader;
        this.turnPlayed = turnPlayed;
        this.logFile = logFile;
    }

    public void pause() {

    }

    public void resume() {

    }

    public void undo(Player player) {

    }

    public void redo(Player player) {

    }

    public void quit() {

    }

    public void saveTmxFile() {

    }

    public void saveXmlFile() {

    }

    public void saveReplay() {

    }
}
