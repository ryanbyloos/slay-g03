package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Classe permettant de superposer les divers Hud et le monde lors d'une partie.
 */
public class WorldScreen implements Screen {

    private World world;
    private TerritoryHud territoryHud;
    private Hud hud;
    private TextButton textButton = new TextButton("PAUSE", Slay.game.skin);
    private Stage stage = new Stage();

    private InputMultiplexer multiplexer = new InputMultiplexer();

    public WorldScreen(World world) {
        this.world = world;
    }

    @Override
    public void show() {
        world.create();
        textButton.setPosition(0, Slay.h / 12);
        textButton.setColor(Color.LIGHT_GRAY);
        territoryHud = new TerritoryHud(world);
        hud = new Hud(world);
        stage.addActor(textButton);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(territoryHud);
        multiplexer.addProcessor(world);
        Gdx.input.setInputProcessor(multiplexer);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.input.getInputProcessor() == multiplexer) {
                    Gdx.input.setInputProcessor(stage);
                    textButton.setLabel(new Label("RESUME", Slay.game.skin));
                } else {
                    Gdx.input.setInputProcessor(multiplexer);
                    textButton.setLabel(new Label("PAUSE", Slay.game.skin));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.render();
        territoryHud.draw();
        hud.draw();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        world.pause();
    }

    @Override
    public void resume() {
        world.resume();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        territoryHud.dispose();
        hud.dispose();
    }

}
