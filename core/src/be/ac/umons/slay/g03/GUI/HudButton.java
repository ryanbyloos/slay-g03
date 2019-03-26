package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HudButton extends TextButton {
    private int cost;

    public HudButton(int cost, Skin skin) {
        super("" + cost, skin);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
