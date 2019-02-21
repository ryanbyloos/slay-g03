package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Entity.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class GameState {
    private Map map;
    private Loader loader;
    private int turnPlayed;
    private String logFile;

    public GameState(Map map, Loader loader, int turnPlayed, String logFile) {
        this.map = map;
        this.loader = loader;
        this.turnPlayed = turnPlayed;
        this.logFile = logFile;
    }

    public void pause() {

    }

    public void resume() {

    }

    public void undo(Player player) {

    }

    public void redo(Player player) {

    }

    public void quit() {

    }

    public void saveTmxFile() throws IOException {
        String dest = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(map.player1.getName() + '-' + map.player2.getName() + '-').concat(loader.getTmxFile());
        String source = Gdx.files.getLocalStoragePath().concat("assets/World/").concat(loader.getTmxFile());
        File file = new File(dest);
        TiledMap tiledMap = new TmxMapLoader().load(source);
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("map");
        if (file.createNewFile()) {
            copyFile(new File(source), file);
            for (int i = 0; i < map.cells.size(); i++) {
                Cell cell = map.cells.get(i);
                TiledMapTileLayer.Cell cellTmx;
                TiledMapTile tile;
                if (cell.getOwner() != null) {
                    cellTmx = tiledLayer.getCell(cell.getX(), cell.getY());
                    if (cell.getOwner().equals(map.player1)) {
                        tile = cellTmx.getTile();
                        tile.setId(3);
                        cellTmx.setTile(tile);
                        tiledLayer.setCell(cell.getX(), cell.getY(), cellTmx);
                    } else {
                        tile = cellTmx.getTile();
                        tile.setId(4);
                        cellTmx.setTile(tile);
                        tiledLayer.setCell(cell.getX(), cell.getY(), cellTmx);
                    }
                }
            }
        } else {
            for (int i = 0; i < map.cells.size(); i++) {
                Cell cell = map.cells.get(i);
                TiledMapTileLayer.Cell cellTmx;
                TiledMapTile tile;
                if (cell.getOwner() != null) {
                    cellTmx = tiledLayer.getCell(cell.getX(), cell.getY());
                    if (cell.getOwner().equals(map.player1)) {
                        tile = cellTmx.getTile();
                        tile.setId(3);
                        cellTmx.setTile(tile);
                        tiledLayer.setCell(cell.getX(), cell.getY(), cellTmx);
                    } else {
                        tile = cellTmx.getTile();
                        tile.setId(4);
                        cellTmx.setTile(tile);
                        tiledLayer.setCell(cell.getX(), cell.getY(), cellTmx);
                    }
                }
            }
        }
    }

    public void saveXmlFile() throws ParserConfigurationException, TransformerException {
        String file = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(map.player1.getName() + '-' + map.player2.getName() + '-').concat(loader.getXmlFile());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        // root element
        Element root = document.createElement("world");
        root.setAttribute("name", loader.getName());
        root.setAttribute("map", loader.getTmxFile());
        document.appendChild(root);
        // map element
        Element items = document.createElement("items");
        Element units = document.createElement("units");
        Element infrastructures = document.createElement("infrastructures");
        root.appendChild(items);
        root.appendChild(units);
        root.appendChild(infrastructures);
        for (int i = 0; i < map.cells.size(); i++) {
            Cell cell = map.cells.get(i);
            MapElement entity;
            Element element;
            if ((entity = cell.getElementOn()) != null) {
                if (entity.getClass() == Soldier.class) {
                    element = document.createElement("unit");
                    Soldier soldier = (Soldier) entity;
                    element.setAttribute("type", "soldier");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(soldier.getOwner().getId()));
                    element.setAttribute("level", Integer.toString(soldier.getLevel()));
                    units.appendChild(element);
                } else if (entity.getClass() == Capital.class) {
                    element = document.createElement("item");
                    element.setAttribute("type", "capital");
                    element.setAttribute("money", "10");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(entity.getOwner().getId()));
                    items.appendChild(element);
                } else if (entity.getClass() == AttackTower.class) {
                    element = document.createElement("infrastructure");
                    AttackTower attackTower = (AttackTower) entity;
                    element.setAttribute("type", "attacktower");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(entity.getOwner().getId()));
                    element.setAttribute("level", Integer.toString(attackTower.getLevel()));
                    infrastructures.appendChild(element);
                } else if (entity.getClass() == Boat.class) {
                    element = document.createElement("infrastructure");
                    Boat boat = (Boat) entity;
                    element.setAttribute("type", "boat");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("t", Integer.toString(boat.getT()));
                    element.setAttribute("defence", Integer.toString(boat.getDefense()));
                    element.setAttribute("hasmoved", Boolean.toString(boat.isHasMoved()));
                    element.setAttribute("playerId", Integer.toString(boat.getOwner().getId()));
                    infrastructures.appendChild(element);
                } else if (entity.getClass() == DefenceTower.class) {
                    element = document.createElement("infrastructure");
                    DefenceTower defenceTower = (DefenceTower) entity;
                    element.setAttribute("type", "defencetower");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(defenceTower.getLevel()));
                    element.setAttribute("level", Integer.toString(defenceTower.getLevel()));
                    infrastructures.appendChild(element);

                } else if (entity.getClass() == Grave.class) {
                    element = document.createElement("item");
                    element.setAttribute("type", "grave");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    items.appendChild(element);
                } else if (entity.getClass() == Mine.class) {
                    element = document.createElement("infrastructure");
                    Mine mine = (Mine) entity;
                    element.setAttribute("type", "mine");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("visible", Boolean.toString(mine.isVisible()));
                    infrastructures.appendChild(element);
                } else if (entity.getClass() == Tree.class) {
                    element = document.createElement("item");
                    element.setAttribute("type", "tree");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    items.appendChild(element);
                }
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(file));
        transformer.transform(domSource, streamResult);

    }

    private static void copyFile(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        sourceChannel = new FileInputStream(source).getChannel();
        destChannel = new FileOutputStream(dest).getChannel();
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        sourceChannel.close();
        destChannel.close();
    }


    public void saveReplay() {

    }
}
