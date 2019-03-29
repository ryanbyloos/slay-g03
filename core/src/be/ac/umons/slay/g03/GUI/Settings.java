package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Entity.Infrastructure;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings extends MenuScreen {
    TextButton changePassword, changeAvatar;
    @Override
    public void show() {
        super.show();
        Table table = new Table().center();
        changePassword = new TextButton("Change password", Slay.game.skin);
        changeAvatar = new TextButton("Change Avatar", Slay.game.skin);
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
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Slay.playerModificationScreen = new PlayerModificationScreen(true);
                Slay.setScreen(Slay.playerModificationScreen);
            }
        });
        changeAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Slay.playerModificationScreen = new PlayerModificationScreen(false);
                Slay.setScreen(Slay.playerModificationScreen);
            }
        });
        table.add(box).pad(10);
        table.row();
        table.add(changePassword).pad(10);
        table.row();
        table.add(changeAvatar).pad(10);
    }
}
