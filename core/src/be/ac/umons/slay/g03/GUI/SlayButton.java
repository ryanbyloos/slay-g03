package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

class SlayButton extends TextButton {

    SlayButton(String text, Skin skin, int pos) {
        super(text, skin);
        this.setSize(ScreenHandler.BUTTON_WIDTH, ScreenHandler.BUTTON_HEIGHT);
        this.setPosition((ScreenHandler.WIDTH - ScreenHandler.BUTTON_WIDTH) >> 1, (8 - pos) * (int) (1.25 * ScreenHandler.BUTTON_HEIGHT));
    }

    SlayButton(String text, Skin skin) {
        super(text, skin);
        this.setSize(ScreenHandler.BUTTON_WIDTH, ScreenHandler.BUTTON_HEIGHT);
        this.setPosition(25, 25);
    }
}
