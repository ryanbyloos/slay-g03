package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.Replay;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class ReplayMenu extends MenuScreen {

    Replay replay = new Replay(0);

    @Override
    public void show() {
        super.show();
        int i = ScreenHandler.HEIGHT;
        try {
            ArrayList<String> replays = replay.getReplays();
            for (String replayName : replays) {
                TextButton button = new TextButton(replayName, ScreenHandler.game.skin);
                stage.addActor(button);
                button.setPosition((ScreenHandler.WIDTH - button.getWidth()) / 2, i - 2 * button.getHeight());
                i -= 2 * button.getHeight();

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (ScreenHandler.replayScreen == null) {
                            ScreenHandler.replayScreen = new ReplayRenderer(replay, "Replays/" + replayName);
                            ScreenHandler.setScreen(ScreenHandler.replayScreen);
                        }
                    }
                });
            }
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
    }
}
