package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class AuthenticatorScreen extends MenuScreen {
    Table authTable;

    Label labelLoginP1 = new Label("login:", Slay.game.skin);
    TextField loginP1 = new TextField("", Slay.game.skin);
    Label labelPasswordP1 = new Label("password:", Slay.game.skin);
    TextField passwordP1 = new TextField("", Slay.game.skin);

    Label labelLoginP2 = new Label("login:", Slay.game.skin);
    TextField loginP2 = new TextField("", Slay.game.skin);
    Label labelPasswordP2 = new Label("password:", Slay.game.skin);
    TextField passwordP2 = new TextField("", Slay.game.skin);

    TextButton authP1 = new TextButton("sign in", Slay.game.skin);
    TextButton authP2 = new TextButton("sign in", Slay.game.skin);

    @Override
    public void show() {
        super.show();
        authTable = new Table().center();
        authTable.setFillParent(true);
        authTable.add(labelLoginP1).padRight(10);
        authTable.add(loginP1).width(Slay.w / 6).padRight(Slay.w / 16);
        authTable.add(labelLoginP2).padLeft(Slay.w / 16);
        authTable.add(loginP2).width(Slay.w / 6).padLeft(10);
        authTable.row();
        authTable.add(labelPasswordP1).padRight(10);
        authTable.add(passwordP1).width(Slay.w / 6).padRight(Slay.w / 16);
        passwordP1.setPasswordMode(true);
        passwordP1.setPasswordCharacter('*');
        authTable.add(labelPasswordP2).padLeft(Slay.w / 16);
        authTable.add(passwordP2).width(Slay.w / 6).padLeft(10);
        passwordP2.setPasswordMode(true);
        passwordP2.setPasswordCharacter('*');
        authTable.row();
        authTable.add(authP1).colspan(2).pad(10);
        authTable.add(authP2).colspan(4).pad(10).padLeft(passwordP1.getWidth() - 32);

        stage.addActor(authTable);
    }
}
