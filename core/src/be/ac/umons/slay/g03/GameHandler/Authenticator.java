package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Player;

public class Authenticator {
    private GameState gameState;
    private String hallOfFameFile;
    private String loginFile;

    public Authenticator(GameState gameState, String hallOfFameFile, String loginFile) {
        this.gameState = gameState;
        this.hallOfFameFile = hallOfFameFile;
        this.loginFile = loginFile;
    }

    public boolean login(String name, String pwd){
        return false;
    }

    public boolean register(String name, String pwd){
        return false;
    }

    public boolean addScore(Player player){
        return false;
    }

    public boolean start(String xmlFile, String tmxFile){
        return false;
    }
}