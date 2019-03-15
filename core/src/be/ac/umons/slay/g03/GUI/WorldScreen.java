package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Screen;

public class WorldScreen implements Screen {

    private World world = new World();

    @Override
    public void show() {
        world.create();
    }

    @Override
    public void render(float delta) {
        world.render();
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
    }
}
