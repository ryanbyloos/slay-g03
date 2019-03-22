package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class WorldScreen implements Screen {

    private World world = new World();
    private TerritoryHud territoryHud;
    private Hud hud;

    private InputMultiplexer multiplexer = new InputMultiplexer();

    @Override
    public void show() {
        world.create();
        territoryHud = new TerritoryHud(world);
        hud = new Hud(world);
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(territoryHud);
        multiplexer.addProcessor(world);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.render();
        territoryHud.draw();
        hud.draw();
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
