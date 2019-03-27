package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Entity.Infrastructure;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings extends MenuScreen {
    @Override
    public void show() {
        super.show();
        Table table = new Table().center();
        table.setFillParent(true);
        stage.addActor(table);

        CheckBox box = new CheckBox("INFRASTRUCTURES", Slay.game.skin);
        box.setChecked(Infrastructure.isAvailable);
        box.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Infrastructure.setAvailability(!Infrastructure.isAvailable);
                Slay.game.preferences.putBoolean("infrastructures", Infrastructure.isAvailable);
                Slay.game.preferences.flush();
            }
        });
        table.add(box);
    }
}
