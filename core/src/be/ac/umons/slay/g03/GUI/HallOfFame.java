package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.AuthenticationError;
import be.ac.umons.slay.g03.GameHandler.Score;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class HallOfFame extends MenuScreen {
    Table hofTable;
    ArrayList<Score> scores;

    @Override
    public void show() {
        super.show();
        hofTable = new Table().center().top().padTop(64);
        hofTable.setFillParent(true);
        hofTable.add(new Label("NAME", Slay.game.skin)).pad(10);
        hofTable.add(new Label("GAME PLAYED", Slay.game.skin)).pad(10);
        hofTable.add(new Label("WIN", Slay.game.skin)).pad(10);
        hofTable.add(new Label("LOSE", Slay.game.skin)).pad(10);
        hofTable.add(new Label("RATIO", Slay.game.skin)).pad(10);
        hofTable.row();
        try {
            scores = Slay.authenticator.getScore();
        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }
        for (Score score : scores) {
            Label name = new Label(score.getPlayerName(), Slay.game.skin);
            Label played = new Label("" + score.getScore()[0], Slay.game.skin);
            Label win = new Label("" + score.getScore()[1], Slay.game.skin);
            Label lose = new Label("" + score.getScore()[2], Slay.game.skin);
            Label ratio = new Label("" + score.getScore()[3], Slay.game.skin);
            hofTable.add(name, played, win, lose, ratio);
            hofTable.row();
        }
//        hofTable.add(new Image(new Texture("assets/Debian-duologo.png"))).width(32).height(32);
        stage.addActor(hofTable);
    }
}
