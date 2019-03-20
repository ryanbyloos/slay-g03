package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WorldScreen implements Screen {

    private World world = new World();
    private Stage hudStage = new Stage();

    private TextButton createButton = new TextButton("CREATE", ScreenHandler.game.skin);

    private InputMultiplexer multiplexer = new InputMultiplexer();

    @Override
    public void show() {

        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                world.creationMode = !world.creationMode;
            }
        });

        hudStage.addActor(createButton);


        world.create();
        multiplexer.addProcessor(hudStage);
        multiplexer.addProcessor(world);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        world.render();
        hudStage.draw();
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
        hudStage.dispose();
    }

}
