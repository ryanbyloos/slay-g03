package be.ac.umons.slay.g03.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WorldHUD extends ApplicationAdapter implements InputProcessor {

    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion soldier0, soldier1, soldier2, soldier3;
    private TextureAtlas.AtlasRegion defenceTower, attackTower, boat, mine;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    @Override
    public void create() {
        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        soldier0 = atlas.findRegion("tile004");
        soldier1 = atlas.findRegion("tile005");
        soldier2 = atlas.findRegion("tile006");
        soldier3 = atlas.findRegion("tile007");
        defenceTower = atlas.findRegion("tile008");
        attackTower = atlas.findRegion("tile009");
        boat = atlas.findRegion("tile010");
        mine = atlas.findRegion("tile012");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    @Override
    public void render() {
//        Gdx.gl.glClearColor(145 / 255f, 145 / 255f, 145 / 255f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(soldier0, 0, 0);
        batch.draw(soldier1, 64, 64);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
