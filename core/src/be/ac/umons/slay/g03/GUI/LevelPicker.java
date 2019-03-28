package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelPicker extends MenuScreen {

    Map map;
    World world;

    public LevelPicker(Map map) {
        this.map = map;
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table().center();
        table.setFillParent(true);
        TextButton lv1 = new TextButton("Level 1", Slay.game.skin);
        lv1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Loader loader = new Loader("g3_2.tmx", "g3_3.xml", "Quicky");
                try {
                    loader.load(map, false);
                } catch (WrongFormatException e) {
                    e.printStackTrace();
                }
                world = new World(map, loader);
//                world.gameState.getStates().
                if (Slay.worldScreen == null)
                    Slay.worldScreen = new WorldScreen(world);
                Slay.setScreen(Slay.worldScreen);
            }
        });
        table.add(lv1);
        stage.addActor(table);
    }
}
