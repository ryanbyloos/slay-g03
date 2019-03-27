package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RegisterScreen extends MenuScreen {
    Label userName, pwd, pseudo;
    TextField userNamef, pwdf, pseudof;
    private Table registerTable;

    @Override
    public void show() {
        super.show();
        registerTable = new Table().center();
        registerTable.setFillParent(true);

        userName = new Label("username:", Slay.game.skin);
        pseudo = new Label("pseudo:", Slay.game.skin);
        pwd = new Label("password:", Slay.game.skin);

        userNamef = new TextField("", Slay.game.skin);
        pwdf = new TextField("", Slay.game.skin);
        pseudof = new TextField("", Slay.game.skin);

        registerTable.add(userName, userNamef);
        registerTable.row();
        registerTable.add(pwd, pwdf);
        registerTable.row();
        registerTable.add(pseudo, pseudof);
        registerTable.row();

        stage.addActor(registerTable);
    }
}
