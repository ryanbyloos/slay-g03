package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Loader {
    private String tmxFile;
    private String xmlFile;

    public Loader(String tmxFile, String xmlFile){
        this.tmxFile = tmxFile;
        this.xmlFile = xmlFile;
    }
    public void load(Map map){

    }

    public void loadFromXmlFile(Map map){



    }
    public void loadFromTmxFile(Map map){
        try{
            String path = Gdx.files.internal(tmxFile).file().getAbsolutePath();
            String absolutePath = path.substring(0, path.length()-tmxFile.length()).concat("src"+File.separator+"be"+File.separator+"ac"+File.separator+"umons"+File.separator+"slay"+File.separator+"g03"+File.separator+"World"+File.separator).concat(tmxFile);
            TiledMap tiledMap = new TmxMapLoader().load(absolutePath);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)tiledMap.getLayers().get("map");
            int width = tiledLayer.getWidth();
            int heigth = tiledLayer.getHeight();
            TiledMapTileLayer.Cell cell;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    cell = tiledLayer.getCell(i, j);
                    if(cell != null){
                        Cell cell1;
                        Boolean available = cell.getTile().getProperties().get("available", Boolean.class);
                        if(available){
                            Integer player = cell.getTile().getProperties().get("player", Integer.class);
                            if(player == 0){
                                cell1 = new Cell(i, j, false, false, null, null);
                            }
                            else if(player == 1){
                                cell1 = new Cell(i, j, false, false, map.player1, null);
                            }
                            else{
                                cell1 = new Cell(i, j, false, false, map.player2, null);
                            }

                        }
                        else {
                            cell1 = new Cell(i, j, false, true, null, null);
                        }
                        map.cells.add(cell1);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
