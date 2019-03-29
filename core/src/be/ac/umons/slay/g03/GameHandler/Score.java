package be.ac.umons.slay.g03.GameHandler;

/**
 * Classe qui permet de stocker le pseudo d'un joueur ,un tableau contenant son nombre de partie jouée,gagnée,perdu
 * et son pourcentage de victoire ainsi que le path de l'avatar du joueur
 */
public class Score {
    private String playerName;
    private int[] score;
    private String image;

    public Score(String player, int[] score, String image) {
        this.playerName = player;
        this.score = score;
        this.image = image;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int[] getScore() {
        return score;
    }

    public String getImage() {
        return image;
    }
}
