package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.Replay;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

/**
 * Menu permettant d'afficher et de choisir une partie a rejouee.
 */
public class ReplayMenu extends MenuScreen {

    Replay replay = new Replay();
    ReplayRenderer replayScreen;

    @Override
    public void show() {
        super.show();
        Table replayTable = new Table().top().center();
        replayTable.setFillParent(true);
        try {
            ArrayList<String> replays = replay.getReplays();
            for (String replayName : replays) {
                TextButton button = new TextButton(replayName, Slay.game.skin);
                replayTable.add(button).pad(5);
                replayTable.row();

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        replay = new Replay();
                        replayScreen = new ReplayRenderer(replay, "assets/Replays/" + replayName);
                        Slay.setScreen(replayScreen);
                    }
                });
            }
            stage.addActor(replayTable);
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
    }
}
