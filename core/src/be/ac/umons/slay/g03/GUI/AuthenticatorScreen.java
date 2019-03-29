package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.GameHandler.AuthenticationError;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class AuthenticatorScreen extends MenuScreen {

    Player player1, player2;

    Table authTableLeft, authTableRight, chooseLevelTable;

    Label labelLoginP1 = new Label("Username:", Slay.game.skin);
    Label labelPasswordP1 = new Label("Password:", Slay.game.skin);
    Label labelLoginP2 = new Label("Username:", Slay.game.skin);
    Label labelPasswordP2 = new Label("Password:", Slay.game.skin);

    TextField loginP1 = new TextField("", Slay.game.skin);
    TextField passwordP1 = new TextField("", Slay.game.skin);
    TextField loginP2 = new TextField("", Slay.game.skin);
    TextField passwordP2 = new TextField("", Slay.game.skin);

    TextButton authP1 = new TextButton("Log in", Slay.game.skin);
    TextButton authP2 = new TextButton("Log in", Slay.game.skin);

    TextButton guestP1 = new TextButton("Log as guest", Slay.game.skin);
    TextButton guestP2 = new TextButton("Log as guest", Slay.game.skin);


    TextButton register = new TextButton("REGISTER", Slay.game.skin);

    TextButton chooseLevel = new TextButton("CHOOSE LEVEL", Slay.game.skin);

    boolean P1logged, P2logged;

    @Override
    public void show() {
        super.show();
        P1logged = false;
        P2logged = false;
        authTableLeft = new Table();
        authTableRight = new Table();
        chooseLevelTable = new Table().center().bottom().padBottom(32);
        chooseLevelTable.setFillParent(true);
        authTableLeft.setSize(Slay.w / 2, Slay.h);
        authTableLeft.setPosition(0, 0);
        authTableRight.setSize(Slay.w / 2, Slay.h);
        authTableRight.setPosition(Slay.w / 2, 0);
        authTableLeft.add(labelLoginP1);
        authTableLeft.add(loginP1).width(Slay.w / 6);
        authTableRight.add(labelLoginP2);
        authTableRight.add(loginP2).width(Slay.w / 6);
        authTableLeft.row();
        authTableRight.row();
        authTableLeft.add(labelPasswordP1);
        authTableLeft.add(passwordP1).width(Slay.w / 6);
        passwordP1.setPasswordMode(true);
        passwordP1.setPasswordCharacter('*');
        authTableRight.add(labelPasswordP2);
        authTableRight.add(passwordP2).width(Slay.w / 6);
        passwordP2.setPasswordMode(true);
        passwordP2.setPasswordCharacter('*');
        authTableLeft.row();
        authTableRight.row();
        authTableLeft.add(guestP1);
        authTableLeft.add(authP1).pad(10);
        authTableRight.add(guestP2);
        authTableRight.add(authP2).pad(10);
        table.add(register).padLeft(Slay.w - 2 * Slay.buttonW - 20).width(Slay.buttonW).height(Slay.buttonH);
        stage.addActor(authTableLeft);
        stage.addActor(authTableRight);
        stage.addActor(chooseLevelTable);

        authP1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (Slay.authenticator.login(loginP1.getText(), passwordP1.getText())) {
                        String pseudo = Slay.authenticator.getPseudo(loginP1.getText());
                        player1 = new Player(pseudo, 1, -1, false, new ArrayList<>());
                        authTableLeft.clearChildren();
                        authTableLeft.add(new Label("Player 1 logged.", Slay.game.skin));
                        P1logged = true;
                    }
                } catch (AuthenticationError e) {
                }
            }
        });
        authP2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (Slay.authenticator.login(loginP2.getText(), passwordP2.getText())) {
                        String pseudo = Slay.authenticator.getPseudo(loginP2.getText());
                        player2 = new Player(pseudo, 2, -1, false, new ArrayList<>());
                        authTableRight.clearChildren();
                        authTableRight.add(new Label("Player 2 logged.", Slay.game.skin));
                        P2logged = true;
                    }
                } catch (AuthenticationError e) {
                }
            }
        });

        guestP1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player1 = new Player("Guest 1", 1, -1, true, new ArrayList<>());
                authTableLeft.clearChildren();
                authTableLeft.add(new Label("Player 1 logged.", Slay.game.skin));
                P1logged = true;
            }
        });

        guestP2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player2 = new Player("Guest 2", 2, -1, true, new ArrayList<>());
                authTableRight.clearChildren();
                authTableRight.add(new Label("Player 2 logged.", Slay.game.skin));
                P2logged = true;
            }
        });

        register.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Slay.registerScreen == null)
                    Slay.registerScreen = new RegisterScreen();
                Slay.setScreen(Slay.registerScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (P1logged && P2logged && chooseLevel == null) {
            Map map = new Map(new ArrayList<>(), player1, player2);
            Slay.levelPicker = new LevelPicker(map);
            chooseLevel = new TextButton("CHOOSE LEVEL", Slay.game.skin);
            chooseLevel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Slay.setScreen(Slay.levelPicker);
                }
            });
            chooseLevelTable.add(chooseLevel);
        } else if (chooseLevel != null && (!P1logged || !P2logged)) {
            chooseLevelTable.clearChildren();
            chooseLevel = null;
        }
    }
}
