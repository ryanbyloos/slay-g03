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

    public void setTmxFile(String tmxFile) {
        this.tmxFile = tmxFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public Loader(String tmxFile, String xmlFile, String name) {
        this.tmxFile = tmxFile;
        this.xmlFile = xmlFile;
        this.name = name;
    }

    public void load(Map map, boolean save) throws WrongFormatException {
        System.out.println(save);
        loadFromTmxFile(map, save);
        loadFromXmlFile(map, save);
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
                                map.getPlayer1().getTerritories().add(territory);
                                break;
                            case 2:
                                map.getPlayer2().getTerritories().add(territory);
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
                        Capital capital = new Capital(owner, money);
                        cell.setElementOn(capital);
                    }

                } else if (type.equals("soldier") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (cell.getOwner() != null && !cell.isWater() && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        boolean hasMoved = Boolean.parseBoolean(node.getAttributes().getNamedItem("hasmoved").getTextContent());
                        Soldier soldier = null;
                        if(level >=0 && level<4){
                            soldier = new Soldier(owner, level);
                            soldier.setHasMoved(hasMoved);
                        }
                        cell.setElementOn(soldier);
                    }

                } else if (type.equals("boat") && cell != null && Infrastructure.isAvailable) {
                    ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
                    NodeList soldiersData = node.getChildNodes();
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    boolean hasMoved = Boolean.parseBoolean(node.getAttributes().getNamedItem("hasmoved").getTextContent());
                    if (cell.isWater()) {
                        Player owner;
                        if (playerId == 1) {
                            owner = map.getPlayer1();
                        } else {
                            owner = map.getPlayer2();
                        }
                        int distMax = Integer.parseInt(node.getAttributes().getNamedItem("distmax").getTextContent());
                        for (int j = 0; j < soldiersData.getLength(); j++) {
                            Node soldierData = soldiersData.item(j);
                            if (soldierData.getNodeType() == Node.ELEMENT_NODE) {
                                int level = Integer.parseInt(soldierData.getAttributes().getNamedItem("level").getTextContent());
                                boolean soldierHasMoved = Boolean.parseBoolean(soldierData.getAttributes().getNamedItem("hasmoved").getTextContent());
                                Soldier soldier = null;
                                if(level >=0 && level < 4) {
                                    soldier = new Soldier(owner, level);
                                    soldier.setHasMoved(soldierHasMoved);
                                }
                                soldiers.add(soldier);

                            }

                        }
                        Boat boat = new Boat(owner);
                        boat.setSoldiers(soldiers);
                        boat.setT(distMax);
                        boat.setHasMoved(hasMoved);
                        cell.setElementOn(boat);
                    }
                } else if (type.equals("attacktower") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());

                    if (Infrastructure.isAvailable && !cell.isWater() && cell.getOwner() != null && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        boolean hasAttack = Boolean.parseBoolean(node.getAttributes().getNamedItem("hasattack").getTextContent());
                        AttackTower attackTower = null;
                        if(level >=0 && level < 4) {
                            attackTower = new AttackTower(owner, level);
                            attackTower.setHasAttack(hasAttack);
                        }
                        cell.setElementOn(attackTower);
                    }

                } else if (type.equals("defencetower") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    if (Infrastructure.isAvailable && !cell.isWater() && cell.getOwner() != null && cell.getOwner().getId() == playerId) {
                        Player owner = cell.getOwner();
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        DefenceTower defenceTower = null;
                        if(level >=0 && level < 4) defenceTower = new DefenceTower(owner, level);
                        cell.setElementOn(defenceTower);
                    }

                } else if (type.equals("grave") && cell != null) {
                    if (!cell.isWater()) {
                        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
                        if(level >=0 && level < 4) cell.setElementOn(new Grave(level));
                    }
                } else if (type.equals("mine") && cell != null) {
                    int playerId = Integer.parseInt(node.getAttributes().getNamedItem("playerId").getTextContent());
                    Boolean visible = Boolean.parseBoolean(node.getAttributes().getNamedItem("visible").getTextContent());
                    if (Infrastructure.isAvailable && cell.isWater()) {
                        Player owner;
                        if (playerId == 1) {
                            owner = map.getPlayer1();
                        } else {
                            owner = map.getPlayer2();
                        }
                        cell.setElementOn(new Mine(owner));
                    }
                } else if (type.equals("tree") && cell != null) {
                    if (!cell.isWater()) {
                        cell.setElementOn(new Tree());
                    }
                }

            }
        } catch (ParserConfigurationException  | SAXException | IOException e) {
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
            map.setHeight(heigth);
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
                                cell1 = new Cell(i, j, false, false, map.getPlayer1(), null);
                            } else {
                                cell1 = new Cell(i, j, false, false, map.getPlayer2(), null);
                            }
                        } else {
                            cell1 = new Cell(i, j, false, true, null, null);
                        }
                        map.getCells().add(cell1);
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
