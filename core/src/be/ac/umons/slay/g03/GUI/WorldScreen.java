package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

public class WorldScreen implements Screen {

    private World world = new World();
    private Hud hud;

    private InputMultiplexer multiplexer = new InputMultiplexer();

    @Override
    public void show() {
        world.create();
        hud = new Hud(world);
        multiplexer.addProcessor(hud);
        multiplexer.addProcessor(world);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        world.render();
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
        hud.dispose();
    }

}
