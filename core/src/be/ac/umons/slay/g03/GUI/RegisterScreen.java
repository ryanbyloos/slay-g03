package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.AuthenticationError;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RegisterScreen extends MenuScreen {
    Label userName, pwd, pseudo;
    TextField userNamef, pwdf, pseudof;
    TextButton registerButton;
    private Table registerTable;

    @Override
    public void show() {
        super.show();
        registerTable = new Table().center();
        registerTable.setFillParent(true);

        userName = new Label("Username:", Slay.game.skin);
        pseudo = new Label("Pseudo:", Slay.game.skin);
        pwd = new Label("Password:", Slay.game.skin);

        userNamef = new TextField("", Slay.game.skin);
        pwdf = new TextField("", Slay.game.skin);
        pseudof = new TextField("", Slay.game.skin);

        pwdf.setPasswordMode(true);
        pwdf.setPasswordCharacter('*');

        registerButton = new TextButton("REGISTER", Slay.game.skin);

        registerTable.add(userName, userNamef);
        registerTable.row();
        registerTable.add(pwd, pwdf);
        registerTable.row();
        registerTable.add(pseudo, pseudof);
        registerTable.row();
        registerTable.add(new Label("", Slay.game.skin));
        registerTable.add(registerButton).pad(20);
        stage.addActor(registerTable);

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton ok = new TextButton("OK", Slay.game.skin);
                Dialog dialog = new Dialog("", Slay.game.skin);
                dialog.setColor(Color.DARK_GRAY);
                dialog.setPosition(Slay.w / 2 - dialog.getWidth() / 2, Slay.h / 2 - dialog.getHeight() / 2);
                dialog.setSize(Slay.w / 4, Slay.h / 4);
                dialog.button(ok);
                ok.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Slay.setScreen(Slay.authScreen);
                    }
                });

                try {
                    if (Slay.authenticator.register(userNamef.getText(), pwdf.getText(), pseudof.getText(), "")) {
                        dialog.text("Successfully registered.");
                    } else {
                        dialog.text("Username already used.");
                    }
                } catch (AuthenticationError e) {
                }
                stage.addActor(dialog);
            }
        });
        returnButton.clearListeners();
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Slay.setScreen(Slay.authScreen);
            }
        });
    }
}
