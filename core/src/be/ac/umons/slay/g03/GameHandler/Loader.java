package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Entity.Boat;
import be.ac.umons.slay.g03.Entity.Capital;
import be.ac.umons.slay.g03.Entity.Infrastructure;
import be.ac.umons.slay.g03.Entity.Soldier;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
    private String tmxFile;
    private String xmlFile;

    public Loader(String tmxFile, String xmlFile) {
        this.tmxFile = tmxFile;
        this.xmlFile = xmlFile;
    }

    public void load(Map map) {
    }

    public void loadFromXmlFile(Map map) throws WrongFormatException {
        try {
            String path = Gdx.files.internal(xmlFile).file().getAbsolutePath();
            String absolutePath = path.substring(0, path.length() - xmlFile.length()).concat("src" + File.separator + "be" + File.separator + "ac" + File.separator + "umons" + File.separator + "slay" + File.separator + "g03" + File.separator + "World" + File.separator).concat(xmlFile);
            File file = new File(absolutePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            ArrayList<Node> all = new ArrayList<Node>();
            NodeList items = doc.getElementsByTagName("item");
            NodeList units = doc.getElementsByTagName("unit");
            NodeList infrastructures = doc.getElementsByTagName("infrastructure");
            for (int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    all.add(node);
                }

            }
            for (int i = 0; i < units.getLength(); i++) {
                Node node = units.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    all.add(node);
                }

            }
            for (int i = 0; i < infrastructures.getLength(); i++) {
                Node node = infrastructures.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    all.add(node);
                }
            }
            for (int i = 0; i < all.size(); i++) {
                Node node = all.get(i);
                String type = node.getAttributes().getNamedItem("type").getTextContent();
                if (type.equals("capital")) {
                    int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getTextContent());
                    int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getTextContent());
                    Cell cell = map.findCell(x, y);
                    if (cell != null) {
                        int player = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                        if (cell.getOwner().getId() == player && !cell.isWater()) {
                            Player owner;
                            if (player == 1) {
                                owner = map.player1;
                            } else {
                                owner = map.player2;
                            }
                            int money = Integer.parseInt(node.getAttributes().getNamedItem("money").getTextContent());
                            Capital capital = new Capital(0, 0, owner, money);
                            cell.setElementOn(capital);
                        }
                    }
                } else if (type.equals("soldier")) {
                    int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getTextContent());
                    int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getTextContent());
                    Cell cell = map.findCell(x, y);
                    if (cell != null) {
                        int player = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                        if (!cell.isWater() && cell.getOwner().getId() == player) {
                            Player owner;
                            if (player == 1) {
                                owner = map.player1;
                            } else {
                                owner = map.player2;
                            }
                            int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                            Soldier soldier;
                            switch (level) {
                                case 0:
                                    soldier = new Soldier(2, 10, owner, 0);
                                    break;
                                case 1:
                                    soldier = new Soldier(5, 20, owner, 1);
                                    break;
                                case 2:
                                    soldier = new Soldier(14, 40, owner, 2);
                                    break;
                                case 3:
                                    soldier = new Soldier(41, 80, owner, 3);
                                    break;
                                default:
                                    soldier = null;
                                    break;
                            }
                            cell.setElementOn(soldier);
                        }
                    }

                } else if (type.equals("boat")) {
                    int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getTextContent());
                    int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getTextContent());
                    Cell cell = map.findCell(x, y);
                    if (cell != null) {
                        int player = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                        if (cell.getOwner().getId() == player && cell.isWater() && Infrastructure.isInfrastructureAvailable()) {
                            Player owner;
                            if (player == 1) {
                                owner = map.player1;
                            } else {
                                owner = map.player2;
                            }
                            int distMax = Integer.parseInt(node.getAttributes().getNamedItem("distmax").getTextContent());
                            Boat boat = new Boat(distMax, 0, 0, 25, owner);//Defense à determiner avec les autre, pareil pour creationCost
                            cell.setElementOn(boat);
                        }
                    }
                }

            }
        } catch (ParserConfigurationException e) {
            throw new WrongFormatException();
        } catch (SAXException e) {
            throw new WrongFormatException();
        } catch (IOException e) {
            throw new WrongFormatException();
        }
    }

    public void loadFromTmxFile(Map map) throws WrongFormatException {
        try {
            String path = Gdx.files.internal(tmxFile).file().getAbsolutePath();
            String absolutePath = path.substring(0, path.length() - tmxFile.length()).concat("src" + File.separator + "be" + File.separator + "ac" + File.separator + "umons" + File.separator + "slay" + File.separator + "g03" + File.separator + "World" + File.separator).concat(tmxFile);
            TiledMap tiledMap = new TmxMapLoader().load(absolutePath);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("map");
            int width = tiledLayer.getWidth();
            int heigth = tiledLayer.getHeight();
            TiledMapTileLayer.Cell cell;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    cell = tiledLayer.getCell(i, j);
                    if (cell != null) {
                        Cell cell1;
                        Boolean available = cell.getTile().getProperties().get("available", Boolean.class);
                        if (available) {
                            Integer player = cell.getTile().getProperties().get("player", Integer.class);
                            if (player == 0) {
                                cell1 = new Cell(i, j, false, false, null, null);
                            } else if (player == 1) {
                                cell1 = new Cell(i, j, false, false, map.player1, null);
                            } else {
                                cell1 = new Cell(i, j, false, false, map.player2, null);
                            }
                        } else {
                            cell1 = new Cell(i, j, false, true, null, null);
                        }
                        map.cells.add(cell1);
                    }
                }
            }
        } catch (GdxRuntimeException e) {
            throw new WrongFormatException();
        }
    }
}
