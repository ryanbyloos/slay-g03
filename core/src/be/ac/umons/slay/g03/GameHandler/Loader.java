package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
    private String name;
    private String tmxFile;
    private String xmlFile;

    public Loader(String tmxFile, String xmlFile, String name) {
        this.tmxFile = tmxFile;
        this.xmlFile = xmlFile;
        this.name = name;
    }

    public void load(Map map) {
    }

    public void loadFromXmlFile(Map map, boolean save) throws WrongFormatException {
        try {
            String path;
            if (save) {
                path = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(xmlFile);
            } else {
                path = Gdx.files.getLocalStoragePath().concat("assets/World/").concat(xmlFile);
            }
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            ArrayList<Node> all = new ArrayList<Node>();
            NodeList items = doc.getElementsByTagName("item");
            NodeList units = doc.getElementsByTagName("unit");
            NodeList infrastructures = doc.getElementsByTagName("infrastructure");
            NodeList territories = doc.getElementsByTagName("territory");

            for (int i = 0; i < territories.getLength(); i++) {
                Territory territory;
                ArrayList<Cell> cells = new ArrayList<Cell>();
                Node node = territories.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int playerId = Integer.parseInt(element.getAttribute("playerId"));
                    int size = element.getElementsByTagName("cell").getLength();
                    int x, y;
                    for (int j = 0; j < size; j++) {
                        x = Integer.parseInt(element.getElementsByTagName("cell").item(j).getAttributes().getNamedItem("x").getTextContent());
                        y = Integer.parseInt(element.getElementsByTagName("cell").item(j).getAttributes().getNamedItem("y").getTextContent());
                        Cell cell = map.findCell(x, y);
                        if (cell != null && !cell.isWater() && cell.getOwner().getId() == playerId) {
                            cells.add(cell);
                        }
                    }
                    if (cells.size() > 1) {
                        territory = new Territory(cells);
                        switch (playerId) {
                            case 1:
                                map.player1.getTerritories().add(territory);
                                break;
                            case 2:
                                map.player2.getTerritories().add(territory);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
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
                int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getTextContent());
                int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getTextContent());
                Cell cell = map.findCell(x, y);
                if (type.equals("capital") && cell != null) {
                    int player = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (cell.getOwner() != null && cell.getOwner().getId() == player && !cell.isWater()) {
                        Player owner = cell.getOwner();
                        int money = Integer.parseInt(node.getAttributes().getNamedItem("money").getTextContent());
                        Capital capital = new Capital(0, 0, owner, money);
                        cell.setElementOn(capital);
                    }

                } else if (type.equals("soldier") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (cell.getOwner() != null && !cell.isWater() && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
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

                } else if (type.equals("boat") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (cell.isWater() && Infrastructure.isInfrastructureAvailable()) {
                        Player owner;
                        if (playerId == 1) {
                            owner = map.player1;
                        } else {
                            owner = map.player2;
                        }
                        int distMax = Integer.parseInt(node.getAttributes().getNamedItem("distmax").getTextContent());
                        Boat boat = new Boat(distMax, 0, 0, 25, owner);//Defense à determiner avec les autre, pareil pour creationCost
                        cell.setElementOn(boat);
                    }
                } else if (type.equals("attacktower") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (Infrastructure.isInfrastructureAvailable() && !cell.isWater() && cell.getOwner() != null && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        AttackTower attackTower;
                        switch (level) {
                            case 0:
                                attackTower = new AttackTower(2, 5, owner, 0);
                                break;
                            case 1:
                                attackTower = new AttackTower(4, 10, owner, 1);
                                break;
                            case 2:
                                attackTower = new AttackTower(8, 20, owner, 2);
                                break;
                            case 3:
                                attackTower = new AttackTower(16, 40, owner, 3);
                                break;
                            default:
                                attackTower = null;
                                break;
                        }
                        cell.setElementOn(attackTower);
                    }

                } else if (type.equals("defencetower") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (Infrastructure.isInfrastructureAvailable() && !cell.isWater() && cell.getOwner() != null && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        DefenceTower defenceTower;
                        switch (level) {
                            case 0:
                                defenceTower = new DefenceTower(2, 5, owner, 0);
                                break;
                            case 1:
                                defenceTower = new DefenceTower(4, 10, owner, 1);
                                break;
                            case 2:
                                defenceTower = new DefenceTower(8, 20, owner, 2);
                                break;
                            case 3:
                                defenceTower = new DefenceTower(16, 40, owner, 3);
                                break;
                            default:
                                defenceTower = null;
                                break;
                        }
                        cell.setElementOn(defenceTower);
                    }

                } else if (type.equals("grave") && cell != null) {
                    if (!cell.isWater()) {
                        cell.setElementOn(new Grave(0, 0, null));
                    }
                } else if (type.equals("mine") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    Boolean visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getTextContent());
                    if (Infrastructure.isInfrastructureAvailable() && cell.isWater()) {
                        Player owner;
                        if (playerId == 1) {
                            owner = map.player1;
                        } else {
                            owner = map.player2;
                        }
                        cell.setElementOn(new Mine(visible, 0, 10, owner));
                    }
                } else if (type.equals("tree") && cell != null) {
                    if (!cell.isWater()) {
                        cell.setElementOn(new Tree(0, 0, null));
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

    public void loadFromTmxFile(Map map, boolean save) throws WrongFormatException {
        try {
            String path;
            if (save) {
                path = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(tmxFile);
            } else {
                path = Gdx.files.getLocalStoragePath().concat("assets/World/").concat(tmxFile);
            }

            TiledMap tiledMap = new TmxMapLoader().load(path);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("map");
            int width = tiledLayer.getWidth();
            int heigth = tiledLayer.getHeight();
            map.setHeigth(heigth);
            map.setWidth(width);
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

    public String getTmxFile() {
        return tmxFile;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public String getName() {
        return name;
    }
}
