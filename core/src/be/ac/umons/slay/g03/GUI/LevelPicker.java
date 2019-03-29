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
        int j = 1;
        for (int i = 1; i <= 21; i += 2) {
            TextButton button = new TextButton("Level " + j, Slay.game.skin);
            j++;
//            Loader loader = new Loader("g3_" + (i-1) + ".tmx", "g3_"+i+".xml", "");
            String tmx = "g3_" + (i - 1) + ".tmx";
            String xml = "g3_" + i + ".xml";
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Loader loader = new Loader(tmx, xml, "");
                    try {
                        loader.load(map, false);
                    } catch (WrongFormatException e) {
                        e.printStackTrace();
                    }
                    world = new World(map, loader);
                    Slay.worldScreen = new WorldScreen(world);
                    Slay.setScreen(Slay.worldScreen);
                }
            });
            table.add(button).pad(10).width(Slay.buttonW).height(Slay.buttonH);
            if (j % 2 != 0)
                table.row();
        }
        stage.addActor(table);
    }
}
