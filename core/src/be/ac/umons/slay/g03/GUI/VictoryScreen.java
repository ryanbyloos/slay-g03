package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class VictoryScreen extends MenuScreen {

    String winner, loser;
    int turn;
    Label message;

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
