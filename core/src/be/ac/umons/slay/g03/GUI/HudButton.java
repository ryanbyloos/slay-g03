package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Bouton personnalisé contenant un cout.
 */
class HudButton extends TextButton {
    private int cost;

    HudButton(int cost, Skin skin) {
        super("" + cost, skin);
        this.cost = cost;
    }

    int getCost() {
        return cost;
    }
}
