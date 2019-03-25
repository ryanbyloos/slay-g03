package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.GameHandler.Replay;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ReplayRenderer extends MapRenderer implements Screen {

    Replay replay;
    String replayFileName;
    ArrayList<ArrayList<ArrayList<Cell>>> replayMap;

    public ReplayRenderer(Replay replay, String replayFileName) {
        this.replay = replay;
        this.replayFileName = replayFileName;
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {

    }

    @Override
    public void show() {
        replay.setReplayFileName(this.replayFileName);
        try {
            replay.setReplay();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        replayMap = replay.getReplay();
    }

    @Override
    public void render(float delta) {
        map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), null, null);
        draw(map);
    }

    @Override
    public void hide() {

    }
}
