package be.ac.umons.slay.g03;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.GameHandler.GameState;
import be.ac.umons.slay.g03.GameHandler.Loader;
import be.ac.umons.slay.g03.GameHandler.WrongFormatException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    private TextureAtlas.AtlasRegion blueImage;
    private TextureAtlas.AtlasRegion greenImage;
    private TextureAtlas.AtlasRegion yellowImage;
    private TextureAtlas.AtlasRegion redImage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private TextureAtlas atlas;


    @Override
    public void create() {

        String atlasPath = Gdx.files.getLocalStoragePath().concat("assets/Sprites/").concat("spritesheet.atlas");
        atlas = new TextureAtlas(atlasPath);
        blueImage = atlas.findRegion("sprite-water");
        greenImage = atlas.findRegion("sprite-land");
        redImage = atlas.findRegion("sprite-2");
        yellowImage = atlas.findRegion("sprite-1");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 112, 80);
        batch = new SpriteBatch();
        map = new Map(new ArrayList<Cell>(), new Player("Danial", 1, 0,false, 0, new ArrayList<Territory>()),
                new Player("Alex", 2,0, false, 0, new ArrayList<Territory>()));
        Loader loader = new Loader("test.tmx", "test.xml", "testIsland");
        Infrastructure.setInfrastructureAvailable(true);
        try {
            loader.loadFromTmxFile(map, false);
            loader.loadFromXmlFile(map);
        }

        catch (WrongFormatException e){
            e.printStackTrace();
        }

        GameState gameState = new GameState(map, loader, 0, "smth");

        try {
            gameState.saveXmlFile();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(145/255f, 145/255f, 145/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 0; i < map.cells.size(); i++) {
            Cell cell = map.cells.get(i);
            if(map.getHeigth() % 2 == 0){
                if(cell.getY() % 2 == 0 ){
                    if(cell.isWater()){
                        batch.draw(blueImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                    }
                    else{
                        if(cell.getOwner() == null)
                            batch.draw(greenImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                        else if(cell.getOwner() == map.player1) batch.draw(yellowImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                        else batch.draw(redImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                    }

                }
                else {
                    if(cell.isWater()){
                        batch.draw(blueImage, cell.getX()*32, (cell.getY()*32)-(cell.getY()*8));
                    }
                    else {
                        if(cell.getOwner() == null)
                            batch.draw(greenImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));
                        else if(cell.getOwner() == map.player1) batch.draw(yellowImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));
                        else batch.draw(redImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));

                    }

                }
            }
            else {
                if(cell.getY() % 2 == 0 ){
                    if(cell.isWater()){
                        batch.draw(blueImage, cell.getX()*32, (cell.getY()*32)-(cell.getY()*8));

                    }
                    else {
                        if(cell.getOwner() == null)
                            batch.draw(greenImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));
                        else if(cell.getOwner() == map.player1) batch.draw(yellowImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));
                        else batch.draw(redImage, (cell.getX())*32, (cell.getY()*32)-(cell.getY()*8));
                    }

                }
                else {
                    if(cell.isWater()){
                        batch.draw(blueImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                    }
                    else{
                        if(cell.getOwner() == null)
                            batch.draw(greenImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                        else if(cell.getOwner() == map.player1) batch.draw(yellowImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                        else batch.draw(redImage, (cell.getX())*32+16, (cell.getY()*32)-(cell.getY()*8));
                    }

                }
            }
        }
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}
