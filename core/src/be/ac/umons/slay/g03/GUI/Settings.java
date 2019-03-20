package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Entity.Infrastructure;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Settings extends MenuScreen {
    @Override
    public void show() {
        super.show();
        CheckBox box = new CheckBox("INFRASTRUCTURES", ScreenHandler.game.skin);
        box.setPosition((ScreenHandler.WIDTH - (int) box.getWidth()) >> 1, ScreenHandler.HEIGHT >> 1);
        box.setChecked(Infrastructure.isAvailable);
        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Infrastructure.setAvailability(!Infrastructure.isAvailable);
                ScreenHandler.game.prefs.putBoolean("infrastructures", Infrastructure.isAvailable);
            }
        });
        stage.addActor(box);
    }
}
