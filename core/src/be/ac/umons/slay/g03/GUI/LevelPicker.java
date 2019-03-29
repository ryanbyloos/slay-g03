package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Menu a partir duquel un niveau sera selectionne.
 */
public class LevelPicker extends MenuScreen {

    private Map map;
    private World world;

    /**
     * @param map La map qui sera chargee avec le niveau selectionne.
     */
    LevelPicker(Map map) {
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
            String tmx = "g3_" + (i - 1) + ".tmx";
            String xml = "g3_" + i + ".xml";
            if (Infrastructure.isAvailable || (j != 2 && j != 4)) {
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
            } else {
                button.setColor(Color.RED);
                button.setLabel(new Label("NEEDS INFRASTRUCTURES", Slay.game.skin));
            }
            table.add(button).pad(10).width(Slay.buttonW).height(Slay.buttonH);
            if (j % 2 != 0)
                table.row();
        }
        stage.addActor(table);
    }
}
