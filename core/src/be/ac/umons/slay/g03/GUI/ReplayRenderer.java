package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.GameHandler.Replay;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class ReplayRenderer extends MapRenderer implements Screen {

    Replay replay;
    String replayFileName;
    ArrayList<ArrayList<ArrayList<Cell>>> replayMap;
    float elapsed;

    private Stage stage = new Stage();
    private TextButton button = new TextButton("NEXT", ScreenHandler.game.skin);

    public ReplayRenderer(Replay replay, String replayFileName) {
        this.replay = replay;
        this.replayFileName = replayFileName;
    }

    @Override
    public void show() {
        create();
        replay.setReplayFileName(this.replayFileName);
        try {
            replay.setReplay();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        replayMap = replay.getReplay();
        this.batch = new SpriteBatch();
        button.setPosition(0, ScreenHandler.HEIGHT - button.getHeight());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                replay.setAutoDisplay(true);
                System.out.println("GNEH");
            }
        });
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
        elapsed = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsed += delta;
        this.batch.begin();
//        if (map==null)
        map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), replay.getP1(), replay.getP2());
        if (elapsed >= 1 && replay.isAutoDisplay()) {
            if (replay.next()) {
                map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), replay.getP1(), replay.getP2());
                elapsed = 0;
            } else {
                replay.moveNumber = 0;
                if (!replay.nextTurn())
                    replay.stopAutoDisplay();
            }
            System.out.println(replay.getMoveNumber() + " " + replay.getTurnNumber());
        }
//        replay.stopAutoDisplay();
        draw(map);
        this.batch.end();
        stage.draw();
    }

    @Override
    public void hide() {

    }
}
