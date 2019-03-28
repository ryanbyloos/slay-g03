package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Core.Territory;
import be.ac.umons.slay.g03.Entity.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameState {

    private Map map;
    private Loader loader;
    private String logFile;
    private States states;
    private int turnPlayed;
    private String elementToBuild;

    public GameState(Map map, Loader loader, int turnPlayed, String logFile) {
        this.map = map;
        this.loader = loader;
        this.turnPlayed = turnPlayed;
        this.logFile = logFile;
        this.states = new States();
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

    public void pause() {

    }

    public void resume() {
        try {
            if (checkAndSetPending()) {
                loader.load(map, true);
            }
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
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
                    if (!cellData.getParentNode().getNodeName().equals("territory")) {
                        boolean isWater = Boolean.parseBoolean(cellData.getAttribute("isWater"));
                        boolean checked = Boolean.parseBoolean(cellData.getAttribute("checked"));
                        int playerId = Integer.parseInt(cellData.getAttribute("playerId"));
                        String entityName = cellData.getAttribute("element");
                        Cell cell = new Cell(x, y, checked, isWater, null, null);
                        switch (playerId) {
                            case 1:
                                if (!isWater) cell.setOwner(map.getPlayer1());
                                break;
                            case 2:
                                if (!isWater) cell.setOwner(map.getPlayer2());
                                break;
                            default:
                                break;
                        }
                        switch (entityName) {
                            case "soldier": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                boolean hasMoved = Boolean.parseBoolean(cellData.getAttribute("hasmoved"));
                                Soldier soldier = null;
                                if (level >= 0 && level < 4) {
                                    soldier = new Soldier(cell.getOwner(), level);
                                    soldier.setHasMoved(hasMoved);
                                }
                                cell.setElementOn(soldier);
                                break;
                            }
                            case "attacktower": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                AttackTower attackTower = null;
                                if (level >= 0 && level < 4) attackTower = new AttackTower(cell.getOwner(), level);
                                cell.setElementOn(attackTower);
                                break;
                            }
                            case "boat": {
                                int t = Integer.parseInt(cellData.getAttribute("t"));
                                boolean hasMoved = Boolean.parseBoolean(cellData.getAttribute("hasmoved"));
                                ArrayList<Soldier> soldiers = new ArrayList<>();
                                NodeList soldiersData = cellData.getChildNodes();
                                Boat boat = new Boat(cell.getOwner());
                                boat.setHasMoved(hasMoved);
                                boat.setT(t);
                                cell.setElementOn(boat);
                                switch (playerId) {
                                    case 1:
                                        cell.getElementOn().setOwner(map.getPlayer1());
                                        break;
                                    case 2:
                                        cell.getElementOn().setOwner(map.getPlayer2());
                                        break;
                                    default:
                                        break;
                                }
                                for (int j = 0; j < soldiersData.getLength(); j++) {
                                    Node node1 = soldiersData.item(j);
                                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                        Element soldierData = (Element) node1;
                                        int level = Integer.parseInt(soldierData.getAttributes().getNamedItem("level").getTextContent());
                                        boolean soldierHasMoved = Boolean.parseBoolean(soldierData.getAttributes().getNamedItem("hasmoved").getTextContent());
                                        Soldier soldier = null;
                                        if (level >= 0 && level < 4) {
                                            soldier = new Soldier(cell.getElementOn().getOwner(), level);
                                            soldier.setHasMoved(soldierHasMoved);
                                        }
                                        soldiers.add(soldier);
                                    }
                                }
                                boat.setSoldiers(soldiers);
                                break;
                            }
                            case "capital":
                                int money = Integer.parseInt(cellData.getAttribute("money"));
                                cell.setElementOn(new Capital(cell.getOwner(), money));
                                break;
                            case "defencetower": {
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                DefenceTower defenceTower = null;
                                if (level > 0 && level < 4) defenceTower = new DefenceTower(cell.getOwner(), level);
                                cell.setElementOn(defenceTower);
                                break;
                            }
                            case "grave":
                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                cell.setElementOn(new Grave(level));
                                break;
                            case "mine":
                                boolean visible = Boolean.parseBoolean(cellData.getAttribute("visible"));
                                Mine mine = null;
                                int mineOwner = Integer.parseInt(cellData.getAttribute("owner"));
                                if (mineOwner == 1) {
                                    mine = new Mine(map.getPlayer1());
                                } else {
                                    mine = new Mine(map.getPlayer2());
                                }
                                cell.setElementOn(mine);
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
                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element territoryData = (Element) node;
                    int playerId = Integer.parseInt(territoryData.getAttribute("playerId"));
                    Territory territory = new Territory(new ArrayList<>());
                    NodeList territoryCells = territoryData.getChildNodes();
                    for (int j = 0; j < territoryCells.getLength(); j++) {
                        Node node1 = territoryCells.item(j);
                        if (node1.getNodeType() == node1.ELEMENT_NODE) {
                            Element cellData = (Element) node1;
                            int x = Integer.parseInt(cellData.getAttribute("x"));
                            int y = Integer.parseInt(cellData.getAttribute("y"));
                            Cell cell = map.findCell(x, y);
                            if (cell != null) {
                                territory.getCells().add(cell);
                            }
                        }
                    }
                    if (playerId == 1) {
                        player1.add(territory);
                    } else if (playerId == 2) {
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

    public void handle(int x, int y) {
        Cell cell;
        if ((cell = map.findCell(x, y)) == null) {// si la  cellule est inexistante on reset les états
            states.setUpgradeAble(false);
            states.setTerritoryLoaded(null);
            states.setDisplayCells(null);
            states.setHold(null);
            states.reset();
        } else {
            if (states.isUpgradeAble()) {
                states.setUpgradeAble(false);
                states.setHold(null);
                states.reset();
            } else if (states.isEverythingFalse()) { // si rien ne se passe , tous est à faux.
                if ((cell.getElementOn() == null || cell.getElementOn() instanceof Capital) && cell.getOwner() == map.playingPlayer()) {// si il n'y a rien sur la cellule ou qu'il y a une capital et la cellule appartient au joueur qui joue alors il y a un territoire qui est selectionée
                    states.setTerritoryLoaded(cell.findTerritory());//territoire selectionné
                    states.setDisplayCells(states.getTerritoryLoaded().getCells());//cellule à afficher
                    states.setTerritorySelected(true);
                } else if (cell.getElementOn() != null) {//sinon si il y a un élément dessus
                    if (cell.getElementOn() instanceof Soldier) {
                        if (((Soldier) cell.getElementOn()).select()) {
                            states.setSoldierSelected(true);
                            states.setHold(cell);
                        }
                    } else if (cell.getElementOn() instanceof Boat) {
                        if (cell.getElementOn() instanceof Boat) {
                            if (((Boat) cell.getElementOn()).select()) {
                                states.setBoatSelected(true);
                                states.setHold(cell);
                            }
                        }
                    } else if (cell.getElementOn() instanceof AttackTower) {
                        if (cell.getElementOn() instanceof AttackTower) {
                            if (((AttackTower) cell.getElementOn()).select()) {
                                states.setAttackTowerSelected(true);
                                states.setHold(cell);
                            }
                        }
                    } else if (cell.getElementOn() instanceof DefenceTower) {
                        if (cell.getElementOn() instanceof DefenceTower) {
                            if (((DefenceTower) cell.getElementOn()).select()) {
                                states.setDefenceTowerSelected(true);
                                states.setHold(cell);
                            }
                        }
                    }
                }
            } else if (states.isTerritorySelected()) { // si le territoire est selectionne
                if (states.isOtherCreation()) {//si on est en cours de creation d'un soldat, d'une tour attaque ou bien une tour de défense(ces états sont défini à partir de TerritoryHUD)
                    if (!cell.isWater() && (cell.getElementOn() == null || cell.getElementOn() instanceof Tree)) {
                        if (states.getDisplayCells().contains(cell)) {
                            cell.setElementOn(newElement(elementToBuild, map.playingPlayer()));
                            cell.setOwner(map.playingPlayer());
                            states.getTerritoryLoaded().findCapital().addMoney(-cell.getElementOn().getCreationCost());
                            try {
                                storeMove(map.playingPlayer());
                            } catch (ReplayParserException e) {
                            }
                        }

                    }
                } else if (states.isMineCreation()) {
                    if (Infrastructure.isAvailable && cell.isWater() && cell.getElementOn() == null) {
                        if (states.getDisplayCells().contains(cell)) {
                            cell.setElementOn(newElement(elementToBuild, map.playingPlayer()));
                            states.getTerritoryLoaded().findCapital().addMoney(-cell.getElementOn().getCreationCost());
                            try {
                                storeMove(map.playingPlayer());
                            } catch (ReplayParserException e) {
                            }
                        }
                    }
                } else if (states.isBoatCreation()) {
                    if (Infrastructure.isAvailable && cell.isWater() && cell.getElementOn() == null) {
                        if (states.getDisplayCells().contains(cell)) {
                            cell.setElementOn(newElement(elementToBuild, map.playingPlayer()));
                            states.getTerritoryLoaded().findCapital().addMoney(-cell.getElementOn().getCreationCost());
                            try {
                                storeMove(map.playingPlayer());
                            } catch (ReplayParserException e) {
                            }
                        }
                    }
                } else if (cell.getElementOn() != null && (cell.getElementOn() instanceof AttackTower || cell.getElementOn() instanceof DefenceTower)) {
                    if (cell.getElementOn().getLevel() < 3 && cell.getElementOn().getLevel() >= 0) {
                        states.setUpgradeAble(true);
                        states.setHold(cell);
                    }
                }
                states.setTerritoryLoaded(null);
                states.setDisplayCells(null);
                states.reset();
//                states.setTerritorySelected(true);
//                System.out.println(states);
            } else if (states.isSoldierSelected() || states.isBoatSelected() || states.isAttackTowerSelected()) {//là on est dans le cas où une unité est selectionnée
                if (states.isSoldierSelected()) {
                    Soldier soldier = (Soldier) states.getHold().getElementOn();
                    soldier.move(states.getHold(), cell, map);
                    try {
                        storeMove(map.playingPlayer());
                    } catch (ReplayParserException e) {
                    }
                    states.setSoldierSelected(false);
                    states.setHold(null);
                } else if (states.isBoatSelected()) {
                    Boat boat = (Boat) states.getHold().getElementOn();
                    if (states.isDeployMode()) {
                        if (states.getHold().adjacentCell(map, states.getHold(), false).contains(cell)) {
                            boat.deploy(states.getHold(), cell, map);
                            try {
                                storeMove(map.playingPlayer());
                            } catch (ReplayParserException e) {
                            }
                        } else {
                            states.setDisplayCells(null);
                            states.setHold(null);
                            states.reset();
                        }
                    } else {
                        if ((boat.getT() > 1)) {
                            if (states.getHold().adjacentCell(map, states.getHold(), true).contains(cell)) {
                                boat.move(states.getHold(), cell, map);
                                states.setHold(cell);
                                try {
                                    storeMove(map.playingPlayer());
                                } catch (ReplayParserException e) {
                                }
                            } else {
                                states.setDisplayCells(null);
                                states.setHold(null);
                                states.reset();
                            }
                        } else {
                            if (states.getHold().adjacentCell(map, states.getHold(), true).contains(cell)) {
                                boat.move(states.getHold(), cell, map);
                                states.setHold(cell);
                                states.setBoatSelected(false);
                                states.setHold(null);
                                try {
                                    storeMove(map.playingPlayer());
                                } catch (ReplayParserException e) {
                                }
                            } else {
                                states.setTerritoryLoaded(null);
                                states.setDisplayCells(null);
                                states.setHold(null);
                                states.reset();
                            }
                        }
                    }


                } else if (states.isAttackTowerSelected()) {
                    AttackTower attackTower = (AttackTower) states.getHold().getElementOn();
                    if (states.getHold().towerRange(map).contains(cell)) {
                        attackTower.attack(map, states.getHold(), cell);
                        states.setAttackTowerSelected(false);
                        states.setHold(null);
                        try {
                            storeMove(map.playingPlayer());
                        } catch (ReplayParserException e) {
                        }
                    } else {
                        states.setTerritoryLoaded(null);
                        states.setDisplayCells(null);
                        states.setHold(null);
                        states.reset();
                    }


                }
            }

        }
    }


    private MapElement newElement(String elementToBuild, Player player) {
        switch (elementToBuild) {
            case "soldier0":
                return new Soldier(player, 0);
            case "soldier1":
                return new Soldier(player, 1);
            case "soldier2":
                return new Soldier(player, 2);
            case "soldier3":
                return new Soldier(player, 3);
            case "defenceTower":
                return new DefenceTower(player, 0);
            case "attackTower":
                return new AttackTower(player, 0);
            case "boat":
                return new Boat(player);
            case "mine":
                return new Mine(player);
        }
        return null;
    }

    public void save() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        saveTmxFile();
        String xml = saveXmlFile();
        String tmx = saveTmxFile();
        int splitIndex = logFile.lastIndexOf('/') + 1;
        String path = logFile.substring(splitIndex);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
        Element root = document.getDocumentElement();
        Element game = document.createElement("game");
        game.setAttribute("player1", map.getPlayer1().getName());
        game.setAttribute("player2", map.getPlayer2().getName());
        game.setAttribute("pending", "true");
        game.setAttribute("xml", xml);
        game.setAttribute("tmx", tmx);
        game.setAttribute("replay", path);
        game.setAttribute("turn", Integer.toString(turnPlayed));
        root.appendChild(game);
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
        transformer.transform(source, result);

    }

    private String saveTmxFile() throws IOException {
        int splitIndex = logFile.lastIndexOf('/') + 1;
        String path = logFile.substring(splitIndex, logFile.length() - 4);
        String dest = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(path).concat(loader.getTmxFile());
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
        return path.concat(loader.getTmxFile());
    }

    private String saveXmlFile() throws ParserConfigurationException, TransformerException {
        int splitIndex = logFile.lastIndexOf('/') + 1;
        String path = logFile.substring(splitIndex, logFile.length() - 4);
        String file = Gdx.files.getLocalStoragePath().concat("assets/Saves/").concat(path).concat(loader.getXmlFile());
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
                    element.setAttribute("hasattack", Boolean.toString(attackTower.isHasAttack()));
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
                    element.setAttribute("playerId", Integer.toString(defenceTower.getOwner().getId()));
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
                    element.setAttribute("playerId", Integer.toString(mine.getOwner().getId()));
                    element.setAttribute("x", Integer.toString(cell.getX()));
                    element.setAttribute("y", Integer.toString(cell.getY()));
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
        return path.concat(loader.getXmlFile());

    }

    public void nextTurn() {
        for (Cell cell : map.getCells()
        ) {
            cell.spwanTree(map);
        }
        try {
            storeTurn();

        } catch (ReplayParserException e) {
            e.printStackTrace();
        }

        if (map.getPlayer1().isTurn()) {
            map.getPlayer2().cleanGrave();
            if (turnPlayed > 1) map.getPlayer2().checkTerritory();
            states.setSoldierSelected(false);
            states.setUpgradeAble(false);
            states.reset();
            states.setTerritoryLoaded(null);
            states.setDisplayCells(null);
            states.setHold(null);
            resetBoats(map.getPlayer1());
            map.getPlayer1().setTurn(false);
            if (map.getPlayer2().isOver()) {
                states.setOver(true);
                try {
                    deleteSaves();
                } catch (WrongFormatException e) {
                    e.printStackTrace();
                }
            }
            resetSoldiers(map.getPlayer1());
            map.getPlayer2().setTurn(true);
            map.getPlayer1().setMoveNumber(-1);
            map.getPlayer1().setMaxMoveNumber(-1);
            try {
                storeMove(map.getPlayer2());
            } catch (ReplayParserException e) {
                e.printStackTrace();
            }


        } else {
            states.setSoldierSelected(false);
            states.setUpgradeAble(false);
            states.reset();
            states.setTerritoryLoaded(null);
            states.setDisplayCells(null);
            states.setHold(null);
            resetBoats(map.getPlayer2());
            map.getPlayer2().setTurn(false);
            if (map.getPlayer1().isOver()) {
                states.setOver(true);
                try {
                    deleteSaves();
                } catch (WrongFormatException e) {
                    e.printStackTrace();
                }
            }
            resetSoldiers(map.getPlayer2());
            map.getPlayer1().setTurn(true);
            map.getPlayer2().setMoveNumber(-1);
            map.getPlayer2().setMaxMoveNumber(-1);
            map.getPlayer1().cleanGrave();
            map.getPlayer1().checkTerritory();

            try {
                storeMove(map.getPlayer1());
            } catch (ReplayParserException e) {
                e.printStackTrace();
            }
        }
        states.reset();
    }

    private void resetSoldiers(Player player) {
        for (Territory t : player.getTerritories()
        ) {
            for (Cell c : t.getCells()
            ) {
                if (c.getElementOn() != null && c.getElementOn() instanceof Soldier) {
                    c.getElementOn().setHasMoved(false);
                }
            }
        }
    }

    private void resetBoats(Player player) {
        for (Cell cell : map.getCells()) {

            if (cell.getElementOn() != null && map.playingPlayer().equals(player) && cell.getElementOn() instanceof Boat && cell.getElementOn().getOwner() != null && cell.getElementOn().getOwner().equals(player)) {
                ((Boat) cell.getElementOn()).setT(5);
                cell.getElementOn().setHasMoved(false);
            }
        }
    }

    public void storeMove(Player player) throws ReplayParserException {
        player.setMoveNumber(player.getMoveNumber() + 1);
        player.setMaxMoveNumber(player.getMoveNumber() + 1);
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
                    element.setAttribute("playerId", Integer.toString(cell.getOwner().getId()));
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
                        element.setAttribute("hasattack", Boolean.toString(((AttackTower) entity).isHasAttack()));
                    } else if (entity instanceof Boat) {
                        element.setAttribute("element", "boat");
                        element.setAttribute("t", Integer.toString(((Boat) entity).getT()));
                        element.setAttribute("defence", Integer.toString(((Boat) entity).getDefence()));
                        element.setAttribute("hasmoved", Boolean.toString(entity.isHasMoved()));
                        element.setAttribute("playerId", Integer.toString(entity.getOwner().getId()));
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
                        element.setAttribute("owner", Integer.toString(entity.getOwner().getId()));
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
        } catch (IOException | TransformerException | ParserConfigurationException | SAXException e) {
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
        } catch (ParserConfigurationException e) {
            throw new ReplayParserException();
        } catch (SAXException e) {
            throw new ReplayParserException();
        } catch (TransformerException e) {
            throw new ReplayParserException();
        }


    }

    public boolean checkAndSetPending() throws WrongFormatException {
        try {
            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList games = doc.getElementsByTagName("game");
            for (int i = 0; i < games.getLength(); i++) {
                Node node = games.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element game = (Element) node;
                    boolean pending = Boolean.parseBoolean(game.getAttribute("pending"));
                    if (pending) {
                        String player1 = game.getAttribute("player1");
                        String player2 = game.getAttribute("player2");
                        if (player1.equals(map.getPlayer1().getName()) && player2.equals(map.getPlayer2().getName())) {
                            loader.setTmxFile(game.getAttribute("tmx"));
                            loader.setXmlFile(game.getAttribute("xml"));
                            logFile = game.getAttribute("replay");
                            turnPlayed = Integer.parseInt(game.getAttribute("turn"));
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new WrongFormatException();
        }


    }

    public boolean isPending() throws WrongFormatException {
        try {
            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList games = doc.getElementsByTagName("game");
            for (int i = 0; i < games.getLength(); i++) {
                Node node = games.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element game = (Element) node;
                    boolean pending = Boolean.parseBoolean(game.getAttribute("pending"));
                    if (pending) {
                        String player1 = game.getAttribute("player1");
                        String player2 = game.getAttribute("player2");
                        if (player1.equals(map.getPlayer1().getName()) && player2.equals(map.getPlayer2().getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (ParserConfigurationException e) {
            throw new WrongFormatException();
        } catch (IOException e) {
            throw new WrongFormatException();
        } catch (SAXException e) {
            throw new WrongFormatException();
        }
    }

    public void deleteGame() throws WrongFormatException {
        try {
            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList games = doc.getElementsByTagName("game");
            for (int i = 0; i < games.getLength(); i++) {
                Node node = games.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element game = (Element) node;
                    boolean pending = Boolean.parseBoolean(game.getAttribute("pending"));
                    if (pending) {
                        String p1 = game.getAttribute("player1");
                        String p2 = game.getAttribute("player2");
                        String xml = game.getAttribute("xml");
                        String tmx = game.getAttribute("tmx");
                        String replay = game.getAttribute("replay");
                        if (map.getPlayer1().getName().equals(p1) && map.getPlayer2().getName().equals(p2)) {
                            game.getParentNode().removeChild(game);
                            FileHandle xmlFile = Gdx.files.local("assets/Saves/" + xml);
                            FileHandle tmxFile = Gdx.files.local("assets/Saves/" + tmx);
                            FileHandle replayFile = Gdx.files.local("assets/Replays/" + replay);
                            xmlFile.delete();
                            tmxFile.delete();
                            replayFile.delete();
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new WrongFormatException();
        }
    }

    public void deleteSaves() throws WrongFormatException {
        try {
            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList games = doc.getElementsByTagName("game");
            for (int i = 0; i < games.getLength(); i++) {
                Node node = games.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element game = (Element) node;
                    boolean pending = Boolean.parseBoolean(game.getAttribute("pending"));
                    if (pending) {
                        String p1 = game.getAttribute("player1");
                        String p2 = game.getAttribute("player2");
                        String xml = game.getAttribute("xml");
                        String tmx = game.getAttribute("tmx");
                        if (map.getPlayer1().getName().equals(p1) && map.getPlayer2().getName().equals(p2)) {
                            FileHandle xmlFile = Gdx.files.local("assets/Saves/" + xml);
                            FileHandle tmxFile = Gdx.files.local("assets/Saves/" + tmx);
                            xmlFile.delete();
                            tmxFile.delete();
                            game.setAttribute("pending", "false");
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new WrongFormatException();
        }
    }

    public void deleteGamePending() throws WrongFormatException {
        try {
            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList games = doc.getElementsByTagName("game");
            for (int i = 0; i < games.getLength(); i++) {
                Node node = games.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element game = (Element) node;
                    boolean pending = Boolean.parseBoolean(game.getAttribute("pending"));
                    if (pending) {
                        String p1 = game.getAttribute("player1");
                        String p2 = game.getAttribute("player2");
                        if (map.getPlayer1().getName().equals(p1) && map.getPlayer2().getName().equals(p2)) {
                            game.getParentNode().removeChild(game);
                        }
                    }
                }
            }
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new WrongFormatException();
        }
    }

    public void saveReplay() throws ReplayParserException {
        try {
            if (isPending()) deleteGame();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        }
        String file;
        Date today = new Date();
        SimpleDateFormat changeFormat = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
        String date = changeFormat.format(today);
        file = Gdx.files.getLocalStoragePath().concat("assets/Replays/").concat(map.getPlayer1().getName() + "-" + map.getPlayer2().getName() + "(" + date + ")").concat(".xml"); // TODO: 02-03-19 à changer par la suite (le nom, les 2 cas possible si(la partie repris est une sauvegarde ou pas ect ..)
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
            StreamResult streamResult = new StreamResult(new File(logFile));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException | ParserConfigurationException e) {
            throw new ReplayParserException();
        }
    }

    public States getStates() {
        return states;
    }

    public String getElementToBuild() {
        return elementToBuild;
    }

    public void setElementToBuild(String elementToBuild) {
        this.elementToBuild = elementToBuild;
    }

    public Map getMap() {
        return map;
    }
}
