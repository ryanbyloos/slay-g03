package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.util.ArrayList;

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

    public void undo(Player player) throws ReplayParserException {
        if (player.getMoveNumber() - 1 >= 0) {
            player.setMoveNumber(player.getMoveNumber() - 1);
            File file = new File(logFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                refreshMap(doc, turnPlayed, player.getMoveNumber());

            } catch (ParserConfigurationException e) {
                throw new ReplayParserException();
            } catch (IOException e) {
                throw new ReplayParserException();
            } catch (SAXException e) {
                throw new ReplayParserException();
            }
        }
    }

    public void redo(Player player) throws ReplayParserException {
        if (player.getMoveNumber() + 1 < player.getMaxMoveNumber()) {
            player.setMoveNumber(player.getMoveNumber() + 1);
            File file = new File(logFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                refreshMap(doc, turnPlayed, player.getMoveNumber());
            } catch (ParserConfigurationException e) {
                throw new ReplayParserException();
            } catch (IOException e) {
                throw new ReplayParserException();
            } catch (SAXException e) {
                throw new ReplayParserException();
            }


        }
    }

    private void refreshMap(Document doc, int turn, int move) {
        ArrayList<Cell> newCells = new ArrayList<>();
        ArrayList<Territory> player1 = new ArrayList<>();
        ArrayList<Territory> player2 = new ArrayList<>();
        Element data = null;
        NodeList turns = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < turns.getLength(); i++) {
            Node node = turns.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element turnData = (Element) node;
                if (turnData.getAttribute("i").equals(Integer.toString(turn))) {
                    NodeList moves = turnData.getChildNodes();
                    for (int j = 0; j < moves.getLength(); j++) {
                        Element moveData = (Element) moves.item(j);
                        if (moveData.getAttribute("j").equals(Integer.toString(move))) {
                            data = moveData;
                            break;
                        }
                    }
                }
            }
        }
        if (data != null) {

            NodeList cells = data.getElementsByTagName("cell");
            for (int i = 0; i < cells.getLength(); i++) {
                Node node = cells.item(i);
                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element cellData = (Element) node;
                    int x = Integer.parseInt(cellData.getAttribute("x"));
                    int y = Integer.parseInt(cellData.getAttribute("y"));
                    if(!cellData.getParentNode().getNodeName().equals("territory")){
                        boolean isWater = Boolean.parseBoolean(cellData.getAttribute("isWater"));
                        boolean checked = Boolean.parseBoolean(cellData.getAttribute("checked"));
                        int playerId = Integer.parseInt(cellData.getAttribute("playerId"));
                        String entityName = cellData.getAttribute("element");
                        Cell cell = new Cell(x, y, checked, isWater, null, null);
                        switch (playerId) {
                            case 1:
                                cell.setOwner(map.getPlayer1());
                                break;
                            case 2:
                                cell.setOwner(map.getPlayer2());
                                break;
                            default:
                                break;
                        }
                        switch (entityName) {
                            case "soldier": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                boolean hasMoved = Boolean.parseBoolean(cellData.getAttribute("hasmoved"));
                                Soldier soldier;
                                switch (level) {
                                    case 0:
                                        soldier = new Soldier(2, 10, cell.getOwner(), 0, hasMoved);
                                        break;
                                    case 1:
                                        soldier = new Soldier(5, 20, cell.getOwner(), 1, hasMoved);
                                        break;
                                    case 2:
                                        soldier = new Soldier(14, 40, cell.getOwner(), 2, hasMoved);
                                        break;
                                    case 3:
                                        soldier = new Soldier(41, 80, cell.getOwner(), 3, hasMoved);
                                        break;
                                    default:
                                        soldier = null;
                                        break;
                                }
                                cell.setElementOn(soldier);
                                break;
                            }
                            case "attacktower": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                AttackTower attackTower;
                                switch (level) {
                                    case 0:
                                        attackTower = new AttackTower(2, 5, cell.getOwner(), 0);
                                        break;
                                    case 1:
                                        attackTower = new AttackTower(4, 10, cell.getOwner(), 1);
                                        break;
                                    case 2:
                                        attackTower = new AttackTower(8, 20, cell.getOwner(), 2);
                                        break;
                                    case 3:
                                        attackTower = new AttackTower(16, 40, cell.getOwner(), 3);
                                        break;
                                    default:
                                        attackTower = null;
                                        break;
                                }
                                cell.setElementOn(attackTower);
                                break;
                            }
                            case "boat": {
                                int t = Integer.parseInt(cellData.getAttribute("t"));
                                int defence = Integer.parseInt(cellData.getAttribute("defence"));
                                boolean hasMoved = Boolean.parseBoolean(cellData.getAttribute("hasmoved"));
                                ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
                                NodeList soldiersData = cellData.getChildNodes();
                                for (int j = 0; j < soldiersData.getLength(); j++) {
                                    Node node1 = soldiersData.item(j);
                                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                        Element soldierData = (Element) node1;
                                        int level = Integer.parseInt(soldierData.getAttributes().getNamedItem("level").getTextContent());
                                        boolean soldierHasMoved = Boolean.parseBoolean(soldierData.getAttributes().getNamedItem("hasmoved").getTextContent());
                                        Soldier soldier;
                                        switch (level) {
                                            case 0:
                                                soldier = new Soldier(2, 10, cell.getOwner(), 0, soldierHasMoved);
                                                break;
                                            case 1:
                                                soldier = new Soldier(5, 20, cell.getOwner(), 1, soldierHasMoved);
                                                break;
                                            case 2:
                                                soldier = new Soldier(14, 40, cell.getOwner(), 2, soldierHasMoved);
                                                break;
                                            case 3:
                                                soldier = new Soldier(41, 80, cell.getOwner(), 3, soldierHasMoved);
                                                break;
                                            default:
                                                soldier = null;
                                                break;
                                        }
                                        soldiers.add(soldier);
                                    }
                                }
                                Boat boat = new Boat(t, defence, 0, 25, cell.getOwner(), hasMoved);
                                boat.setSoldiers(soldiers);
                                cell.setElementOn(boat);
                                break;
                            }
                            case "capital":
                                int money = Integer.parseInt(cellData.getAttribute("money"));
                                cell.setElementOn(new Capital( cell.getOwner(), money));
                                break;
                            case "defencetower": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                DefenceTower defenceTower;
                                switch (level) {
                                    case 0:
                                        defenceTower = new DefenceTower(2, 5, cell.getOwner(), 0);
                                        break;
                                    case 1:
                                        defenceTower = new DefenceTower(4, 10, cell.getOwner(), 1);
                                        break;
                                    case 2:
                                        defenceTower = new DefenceTower(8, 20, cell.getOwner(), 2);
                                        break;
                                    case 3:
                                        defenceTower = new DefenceTower(16, 40, cell.getOwner(), 3);
                                        break;
                                    default:
                                        defenceTower = null;
                                        break;
                                }
                                cell.setElementOn(defenceTower);
                                break;
                            }
                            case "grave":
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                cell.setElementOn(new Grave(level));
                                break;
                            case "mine":
                                boolean visible = Boolean.parseBoolean(cellData.getAttribute("visible"));
                                cell.setElementOn(new Mine(visible, 0, 10, cell.getOwner()));
                                break;
                            case "tree":
                                cell.setElementOn(new Tree());
                                break;
                        }
                        newCells.add(cell);
                    }

                }
            }
            this.map.setCells(newCells);
            NodeList territories = data.getElementsByTagName("territory");
            for (int i = 0; i < territories.getLength(); i++) {
                Node node = territories.item(i);
                if(node.getNodeType() == node.ELEMENT_NODE){
                    Element territoryData = (Element) node;
                    int playerId = Integer.parseInt(territoryData.getAttribute("playerId"));
                    Territory territory = new Territory(new ArrayList<>());
                    NodeList territoryCells = territoryData.getChildNodes();
                    for (int j = 0; j < territoryCells.getLength(); j++) {
                        Node node1 = territoryCells.item(j);
                        if(node1.getNodeType() == node1.ELEMENT_NODE){
                            Element cellData = (Element) node1;
                            int x = Integer.parseInt(cellData.getAttribute("x"));
                            int y = Integer.parseInt(cellData.getAttribute("y"));
                            Cell cell = map.findCell(x, y);
                            if(cell!=null){
                                territory.getCells().add(cell);
                            }
                        }
                    }
                    if(playerId == 1){
                        player1.add(territory);
                    }
                    else if (playerId == 2) {
                        player2.add(territory);
                    }
                }
            }
        }

        map.getPlayer1().setTerritories(player1);
        map.getPlayer2().setTerritories(player2);
    }


    public void quit() {

    }

    public void saveTmxFile() throws IOException {
        String dest = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(map.getPlayer1().getName() + '-' + map.getPlayer2().getName() + '-').concat(loader.getTmxFile());
        String source = Gdx.files.getLocalStoragePath().concat("assets/World/").concat(loader.getTmxFile());
        File file = new File(dest);
        TiledMap tiledMap = new TmxMapLoader().load(source);
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("map");
        if (file.createNewFile()) {
            copyFile(new File(source), file);

        }
        for (int i = 0; i < map.getCells().size(); i++) {
            Cell cell = map.getCells().get(i);
            TiledMapTileLayer.Cell cellTmx;
            TiledMapTile tile;
            if (cell.getOwner() != null) {
                cellTmx = tiledLayer.getCell(cell.getX(), cell.getY());
                if (cell.getOwner().equals(map.getPlayer1())) {
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

    public void saveXmlFile() throws ParserConfigurationException, TransformerException {
        String file = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(map.getPlayer1().getName() + '-' + map.getPlayer2().getName() + '-').concat(loader.getXmlFile());
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
        Element territories = document.createElement("territories");
        root.appendChild(items);
        root.appendChild(units);
        root.appendChild(infrastructures);
        root.appendChild(territories);
        for (int i = 0; i < map.getCells().size(); i++) {
            Cell cell = map.getCells().get(i);
            MapElement entity;
            Element element;
            if ((entity = cell.getElementOn()) != null) {
                if (entity instanceof Soldier) {
                    element = document.createElement("unit");
                    Soldier soldier = (Soldier) entity;
                    element.setAttribute("type", "soldier");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(soldier.getOwner().getId()));
                    element.setAttribute("level", Integer.toString(soldier.getLevel()));
                    element.setAttribute("hasmoved", Boolean.toString(soldier.isHasMoved()));
                    units.appendChild(element);
                } else if (entity instanceof Capital) {
                    element = document.createElement("item");
                    element.setAttribute("type", "capital");
                    element.setAttribute("money", "10");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(entity.getOwner().getId()));
                    items.appendChild(element);
                } else if (entity instanceof AttackTower) {
                    element = document.createElement("infrastructure");
                    AttackTower attackTower = (AttackTower) entity;
                    element.setAttribute("type", "attacktower");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(entity.getOwner().getId()));
                    element.setAttribute("level", Integer.toString(attackTower.getLevel()));
                    infrastructures.appendChild(element);
                } else if (entity instanceof Boat) {
                    element = document.createElement("infrastructure");
                    Boat boat = (Boat) entity;
                    element.setAttribute("type", "boat");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("t", Integer.toString(boat.getT()));
                    element.setAttribute("defence", Integer.toString(boat.getDefence()));
                    element.setAttribute("hasmoved", Boolean.toString(boat.isHasMoved()));
                    element.setAttribute("playerId", Integer.toString(boat.getOwner().getId()));
                    for (int j = 0; j < boat.getSoldiers().size(); j++) {
                        Element soldierData = document.createElement("soldier");
                        Soldier soldier = ((Boat) entity).getSoldiers().get(j);
                        soldierData.setAttribute("level", Integer.toString(soldier.getLevel()));
                        soldierData.setAttribute("hasmoved", Boolean.toString(soldier.isHasMoved()));
                        element.appendChild(soldierData);
                    }
                    infrastructures.appendChild(element);
                } else if (entity instanceof DefenceTower) {
                    element = document.createElement("infrastructure");
                    DefenceTower defenceTower = (DefenceTower) entity;
                    element.setAttribute("type", "defencetower");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("playerId", Integer.toString(defenceTower.getLevel()));
                    element.setAttribute("level", Integer.toString(defenceTower.getLevel()));
                    infrastructures.appendChild(element);

                } else if (entity instanceof Grave) {
                    element = document.createElement("item");
                    element.setAttribute("type", "grave");
                    element.setAttribute("level", Integer.toString(entity.getLevel()));
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    items.appendChild(element);
                } else if (entity instanceof Mine) {
                    element = document.createElement("infrastructure");
                    Mine mine = (Mine) entity;
                    element.setAttribute("type", "mine");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    element.setAttribute("visible", Boolean.toString(mine.isVisible()));
                    infrastructures.appendChild(element);
                } else if (entity instanceof Tree) {
                    element = document.createElement("item");
                    element.setAttribute("type", "tree");
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
                    items.appendChild(element);
                }
            }
        }
        for (int i = 0; i < map.getPlayer1().getTerritories().size(); i++) {
            Element territory = document.createElement("territory");
            territory.setAttribute("playerId", "1");
            for (int j = 0; j < map.getPlayer1().getTerritories().get(i).getCells().size(); j++) {
                Cell cellL = map.getPlayer1().getTerritories().get(i).getCells().get(j);
                Element cell = document.createElement("cell");
                cell.setAttribute("x", Integer.toString(cellL.getX()));
                cell.setAttribute("y", Integer.toString(cellL.getY()));
                territory.appendChild(cell);
            }
            territories.appendChild(territory);
        }
        for (int i = 0; i < map.getPlayer2().getTerritories().size(); i++) {
            Element territory = document.createElement("territory");
            territory.setAttribute("playerId", "2");
            for (int j = 0; j < map.getPlayer2().getTerritories().get(i).getCells().size(); j++) {
                Cell cellL = map.getPlayer2().getTerritories().get(i).getCells().get(j);
                Element cell = document.createElement("cell");
                cell.setAttribute("x", Integer.toString(cellL.getX()));
                cell.setAttribute("y", Integer.toString(cellL.getY()));
                territory.appendChild(cell);
            }
            territories.appendChild(territory);
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

    public void nextTurn() {
        for (Cell cell: map.getCells()
                ) {
            cell.spwanTree(map);
        }
        try {
            storeTurn();

        } catch (ReplayParserException e) {
            e.printStackTrace();
        }

        if (map.getPlayer1().isTurn()) {
            if(turnPlayed >1) map.getPlayer2().checkTerritory();
            map.getPlayer1().setTurn(false);
            resetMoveableUnits(map.getPlayer1());
            map.getPlayer2().setTurn(true);
            map.getPlayer1().setMoveNumber(-1);
            map.getPlayer1().setMaxMoveNumber(-1);

            try {
                storeMove(map.getPlayer2());
            } catch (ReplayParserException e) {
                e.printStackTrace();
            }


        } else {
            map.getPlayer2().setTurn(false);
            resetMoveableUnits(map.getPlayer2());
            map.getPlayer1().setTurn(true);
            map.getPlayer2().setMoveNumber(-1);
            map.getPlayer2().setMaxMoveNumber(-1);
            map.getPlayer1().checkTerritory();
            try {
                storeMove(map.getPlayer1());
            } catch (ReplayParserException e) {
                e.printStackTrace();
            }


        }


    }

    private void resetMoveableUnits(Player player) {
        for (Territory t :player.getTerritories()
             ) {
            for (Cell c: t.getCells()
                 ) {
                if(c.getElementOn()!=null && c.getElementOn() instanceof Soldier){
                    c.getElementOn().setHasMoved(false);
                }
            }
        }
    }

    public void storeMove(Player player) throws ReplayParserException {
        player.setMoveNumber(player.getMoveNumber()+1);
        player.setMaxMoveNumber(player.getMoveNumber()+1);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(logFile);
            document.getDocumentElement().normalize();
            NodeList turns = document.getElementsByTagName("turn");//obtain all turn froms xml
            Node turn = turns.item(turns.getLength() - 1);//obtain the turn needed
            NodeList moves = turn.getChildNodes();
            for (int i = 0; i < moves.getLength(); i++) {
                Node move = moves.item(i);
                if (Integer.parseInt(move.getAttributes().getNamedItem("j").getTextContent()) == player.getMoveNumber()) {
                    move.getParentNode().removeChild(move);
                }
            }
            Element move = document.createElement("move");
            move.setAttribute("j", Integer.toString(player.getMoveNumber()));
            for (int i = 0; i < map.getCells().size(); i++) {
                Element element = document.createElement("cell");
                MapElement entity;
                Cell cell = map.getCells().get(i);
                element.setAttribute("x", Integer.toString(cell.getX()));
                element.setAttribute("y", Integer.toString(cell.getY()));
                element.setAttribute("isWater", Boolean.toString(cell.isWater()));
                element.setAttribute("checked", Boolean.toString(cell.isChecked()));
                if (cell.getOwner() == null) {
                    element.setAttribute("playerId", "0");
                } else {

                    switch (cell.getOwner().getId()) {
                        case 1:
                            element.setAttribute("playerId", "1");
                            break;
                        case 2:
                            element.setAttribute("playerId", "2");
                            break;
                        default:
                            break;
                    }
                }
                if (cell.getElementOn() == null) {
                    element.setAttribute("element", "null");
                } else {
                    entity = cell.getElementOn();
                    if (entity instanceof Soldier) {
                        element.setAttribute("element", "soldier");
                        element.setAttribute("level", Integer.toString(entity.getLevel()));
                        element.setAttribute("hasmoved", Boolean.toString(entity.isHasMoved()));
                    } else if (entity instanceof Capital) {
                        element.setAttribute("element", "capital");
                        element.setAttribute("money", Integer.toString(((Capital) entity).getMoney()));
                    } else if (entity instanceof AttackTower) {
                        element.setAttribute("element", "attacktower");
                        element.setAttribute("level", Integer.toString(entity.getLevel()));
                    } else if (entity instanceof Boat) {
                        element.setAttribute("element", "boat");
                        element.setAttribute("t", Integer.toString(((Boat) entity).getT()));
                        element.setAttribute("defence", Integer.toString(((Boat) entity).getDefence()));
                        element.setAttribute("hasmoved", Boolean.toString(entity.isHasMoved()));
                        for (int j = 0; j < ((Boat) entity).getSoldiers().size(); j++) {
                            Element soldierElement = document.createElement("soldier");
                            Soldier soldier = ((Boat) entity).getSoldiers().get(j);
                            soldierElement.setAttribute("level", Integer.toString(soldier.getLevel()));
                            soldierElement.setAttribute("hasmoved", Boolean.toString(soldier.isHasMoved()));
                            element.appendChild(soldierElement);
                        }
                    } else if (entity instanceof DefenceTower) {
                        element.setAttribute("element", "defencetower");
                        element.setAttribute("level", Integer.toString(entity.getLevel()));
                    } else if (entity instanceof Grave) {
                        element.setAttribute("element", "grave");
                        element.setAttribute("level", Integer.toString(entity.getLevel()));
                    } else if (entity instanceof Mine) {
                        element.setAttribute("element", "mine");
                        element.setAttribute("visible", Boolean.toString(((Mine) entity).isVisible()));
                    } else if (entity instanceof Tree) {
                        element.setAttribute("element", "tree");
                    }
                }
                move.appendChild(element);
            }
            Element territories = document.createElement("territories");
            for (int i = 0; i < map.getPlayer1().getTerritories().size(); i++) {
                Element territory = document.createElement("territory");
                territory.setAttribute("playerId", "1");
                for (int j = 0; j < map.getPlayer1().getTerritories().get(i).getCells().size(); j++) {
                    Cell cellL = map.getPlayer1().getTerritories().get(i).getCells().get(j);
                    Element cell = document.createElement("cell");
                    cell.setAttribute("x", Integer.toString(cellL.getX()));
                    cell.setAttribute("y", Integer.toString(cellL.getY()));
                    territory.appendChild(cell);
                }
                territories.appendChild(territory);
            }
            for (int i = 0; i < map.getPlayer2().getTerritories().size(); i++) {
                Element territory = document.createElement("territory");
                territory.setAttribute("playerId", "2");
                for (int j = 0; j < map.getPlayer2().getTerritories().get(i).getCells().size(); j++) {
                    Cell cellL = map.getPlayer2().getTerritories().get(i).getCells().get(j);
                    Element cell = document.createElement("cell");
                    cell.setAttribute("x", Integer.toString(cellL.getX()));
                    cell.setAttribute("y", Integer.toString(cellL.getY()));
                    territory.appendChild(cell);
                }
                territories.appendChild(territory);
            }
            move.appendChild(territories);
            turn.appendChild(move);
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(logFile);
            transformer.transform(source, result);
        } catch (IOException e) {
            throw new ReplayParserException();
        } catch (TransformerConfigurationException e) {
            throw new ReplayParserException();
        } catch (ParserConfigurationException e) {
            throw new ReplayParserException();
        } catch (SAXException e) {
            throw new ReplayParserException();
        } catch (TransformerException e) {
            throw new ReplayParserException();
        }


    }

    public void storeTurn() throws ReplayParserException {
        turnPlayed++;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(logFile);
            Element root = document.getDocumentElement();
            Element turn = document.createElement("turn");
            turn.setAttribute("i", Integer.toString(turnPlayed));
            root.appendChild(turn);
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(logFile);
            transformer.transform(source, result);
        } catch (IOException e) {
            throw new ReplayParserException();
        } catch (TransformerConfigurationException e) {
            throw new ReplayParserException();
        } catch (ParserConfigurationException e) {
            throw new ReplayParserException();
        } catch (SAXException e) {
            throw new ReplayParserException();
        } catch (TransformerException e) {
            throw new ReplayParserException();
        }


    }


    public void saveReplay() throws ReplayParserException {
        String file = Gdx.files.getLocalStoragePath().concat("assets/Replays/").concat("test.xml"); // TODO: 02-03-19 à changer par la suite (le nom, les 2 cas possible si(la partie repris est une sauvegarde ou pas ect ..)
        logFile = file;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("replay");
            document.appendChild(root);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(file));
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException e) {
            throw new ReplayParserException();
        } catch (TransformerException e) {
            throw new ReplayParserException();
        } catch (ParserConfigurationException e) {
            throw new ReplayParserException();
        }
    }
}
