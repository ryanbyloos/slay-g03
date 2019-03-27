package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.GameHandler.Replay;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class ReplayRenderer extends MapRenderer implements Screen {

    Replay replay;
    String replayFileName;
    ArrayList<ArrayList<ArrayList<Cell>>> replayMap;
    float elapsed;

    private Stage stage = new Stage();
    private Table table;
    private TextButton playButton, returnButton, speedButton;
    private Slider slider;

    public ReplayRenderer(Replay replay, String replayFileName) {
        this.replay = replay;
        this.replayFileName = replayFileName;
    }

    @Override
    public void show() {
        create();
        playButton = new TextButton("PLAY", Slay.game.skin);
        returnButton = new TextButton("RETURN", Slay.game.skin);
        table = new Table(Slay.game.skin).top().left().pad(10);
        table.setFillParent(true);
//        slider = new Slider(0, )

        replay.setReplayFileName(this.replayFileName);
        try {
            replay.setReplay();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }

        replayMap = replay.getReplay();
        this.batch = new SpriteBatch();

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                replay.setAutoDisplay(!replay.isAutoDisplay());
            }
        });

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                replay.stopAutoDisplay();
                Slay.setScreen(Slay.replay);
            }
        });

        table.add(returnButton).pad(5).padRight(returnButton.getWidth());
        table.add(playButton).pad(5);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), replay.getP1(), replay.getP2());
        setViewport(camera, map);
        camera.zoom = 1.2f;
        elapsed = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        elapsed += delta;
        this.batch.begin();
        map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), replay.getP1(), replay.getP2());
        if (elapsed >= .05 && replay.isAutoDisplay()) {
            replay.next();
                map = new Map(replayMap.get(replay.getTurnNumber()).get(replay.getMoveNumber()), replay.getP1(), replay.getP2());
                elapsed = 0;
        }
        draw(map);
        this.batch.end();
        stage.draw();
        batch.setProjectionMatrix(camera.combined);

        if (replay.isAutoDisplay()) {
            playButton.setLabel(new Label("STOP", Slay.game.skin));
        } else
            playButton.setLabel(new Label("PLAY", Slay.game.skin));
    }

    @Override
    public void hide() {

    }

    public int[] getSize() {
        Cell cell = replayMap.get(0).get(0).get(replayMap.get(0).get(0).size() - 1);
        return new int[]{cell.getX(), cell.getY()};
    }

    @Override
    void setViewport(OrthographicCamera camera, Map map) {
        int[] coord = getSize();
        int mapH = coord[1];
        int w = (coord[0] * 32) + 48;
        int h = mapH * 24 + 8 + (32 * (mapH % 2));
        camera.setToOrtho(false, w, h);
    }
}
