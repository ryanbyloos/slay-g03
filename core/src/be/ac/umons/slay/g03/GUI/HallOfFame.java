package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HallOfFame extends MenuScreen {
    Table hofTable;

    @Override
    public void show() {
        super.show();
        hofTable = new Table().center().top().padTop(64);
        hofTable.setFillParent(true);
        hofTable.add(new Label("", Slay.game.skin), new Label("NAME", Slay.game.skin), new Label("GAME PLAYED", Slay.game.skin), new Label("WIN", Slay.game.skin), new Label("LOSE", Slay.game.skin), new Label("RATIO", Slay.game.skin));
        hofTable.row();
        hofTable.add(new Image(new Texture("assets/Debian-duologo.png"))).width(32).height(32);
        stage.addActor(hofTable);
    }
}
