package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.GameHandler.AuthenticationError;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Menu utilisé pour modifié un attribut d'un joueur.
 */
public class PlayerModificationScreen extends MenuScreen {
    boolean password;
    Label userName, oldValue, newValue;
    TextField userNamef, oldValuef, newValuef;
    TextButton changeButton;
    private Table changeTable;

    /**
     * @param password Défini si le parametre modifié est le mot de passe (vrai) ou l'avatar (faux).
     */
    PlayerModificationScreen(boolean password) {
        this.password = password;
    }

    @Override
    public void show() {
        super.show();
        changeTable = new Table().center();
        changeTable.setFillParent(true);

        userName = new Label("Username:", Slay.game.skin);
        userNamef = new TextField("", Slay.game.skin);
        oldValuef = new TextField("", Slay.game.skin);
        newValuef = new TextField("", Slay.game.skin);

        if (password) {
            oldValue = new Label("Old password:", Slay.game.skin);
            newValue = new Label("New password:", Slay.game.skin);

            oldValuef.setPasswordMode(true);
            oldValuef.setPasswordCharacter('*');
            newValuef.setPasswordMode(true);
            newValuef.setPasswordCharacter('*');
        } else {
            oldValue = new Label("Old avatar:", Slay.game.skin);
            newValue = new Label("New avatar:", Slay.game.skin);
        }

        changeButton = new TextButton("CHANGE", Slay.game.skin);

        changeTable.add(userName, userNamef);
        changeTable.row();
        changeTable.add(oldValue, oldValuef);
        changeTable.row();
        changeTable.add(newValue, newValuef);
        changeTable.row();
        changeTable.add(new Label("", Slay.game.skin));
        changeTable.add(changeButton).pad(20);
        stage.addActor(changeTable);

        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TextButton ok = new TextButton("OK", Slay.game.skin);
                Dialog dialog = new Dialog("", Slay.game.skin);
                dialog.setColor(Color.DARK_GRAY);
                dialog.setPosition(Slay.w / 2 - dialog.getWidth() / 2, Slay.h / 2 - dialog.getHeight() / 2);
                dialog.setSize(Slay.w / 3, Slay.h / 4);
                dialog.button(ok);
                ok.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Slay.setScreen(Slay.authScreen);
                    }
                });

                try {
                    if (password) {
                        if (Slay.authenticator.changePassword(userNamef.getText(), oldValuef.getText(), newValuef.getText())) {
                            dialog.text("Password successfully changed.");
                        } else {
                            dialog.text("Error, password unchanged.");
                        }
                    } else {
                        if (Slay.authenticator.changeAvatar(userNamef.getText(), oldValuef.getText(), newValuef.getText())) {
                            dialog.text("Avatar successfully changed.");
                        } else {
                            dialog.text("Error, avatar unchanged.");
                        }
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
                Slay.setScreen(Slay.settings);
            }
        });
    }
}
