package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Écran de victoire, apparaissant a la fin d'une partie.
 */
public class VictoryScreen extends MenuScreen {

    String winner, loser;
    int turn;
    Label message;

    /**
     * @param winner Le nom du joueur gagnant.
     * @param loser  Le nom du joueur perdant.
     * @param turn   Le nombre de tour effectués a la fin de la partie.
     */
    VictoryScreen(String winner, String loser, int turn) {
        this.winner = winner;
        this.loser = loser;
        this.turn = turn;
    }

    @Override
    public void show() {
        super.show();
        message = new Label(winner + " exterminated " + loser + " in " + turn + " turns.", Slay.game.skin);
        message.setPosition(Slay.w / 2 - message.getWidth() / 2, Slay.h / 2);
        stage.addActor(message);
    }

}
