package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Player;
import be.ac.umons.slay.g03.Entity.*;
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

public class Replay {

    private int turn, move, moveDone;
    private int speed = 1;
    private String replayFileName;
    private ArrayList<ArrayList<ArrayList<Cell>>> replay = new ArrayList<>();
    private boolean autoDisplay;

    private Player p1, p2;

    public Replay() {
        this.turn = 0;
        this.move = 0;
        this.moveDone = 0;
    }

    public void setReplay() throws WrongFormatException {
//        File file = new File(Gdx.files.getLocalStoragePath().concat(replayFileName));
        File file = new File(replayFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList turns = doc.getDocumentElement().getChildNodes();
            p1 = new Player("yellow", 1, 0, false, null);
            p2 = new Player("red", 2, 0, false, null);
            for (int i = 0; i < turns.getLength(); i++) {
                Node turn = turns.item(i);
                if (turn.getNodeType() == Node.ELEMENT_NODE) {
                    Element turnElement = (Element) turn;
                    ArrayList<ArrayList<Cell>> movesCells = new ArrayList<>();
                    NodeList moves = turnElement.getChildNodes();
                    for (int j = 0; j < moves.getLength(); j++) {
                        Node move = moves.item(j);
                        if (move.getNodeType() == Node.ELEMENT_NODE) {
                            ArrayList<Cell> cells = new ArrayList<>();
                            Element moveElement = (Element) move;
                            NodeList cellsFromXml = moveElement.getElementsByTagName("cell");
                            for (int k = 0; k < cellsFromXml.getLength(); k++) {
                                Node node = cellsFromXml.item(k);
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
                                                cell.setOwner(p1);
                                                break;
                                            case 2:
                                                cell.setOwner(p2);
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
                                                }
                                                soldier.setHasMoved(hasMoved);
                                                cell.setElementOn(soldier);
                                                break;
                                            }
                                            case "attacktower": {
                                                int level = Integer.parseInt(cellData.getAttribute("level"));
                                                AttackTower attackTower = null;
                                                if (level >= 0 && level < 4) {
                                                    attackTower = new AttackTower(cell.getOwner(), level);
                                                }
                                                cell.setElementOn(attackTower);
                                                break;
                                            }
                                            case "boat": {
                                                boolean hasMoved = Boolean.parseBoolean(cellData.getAttribute("hasmoved"));
                                                int distMax = Integer.parseInt(node.getAttributes().getNamedItem("t").getTextContent());
                                                ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
                                                Boat boat = new Boat(cell.getOwner());
                                                boat.setHasMoved(hasMoved);
                                                boat.setT(distMax);
                                                cell.setElementOn(boat);
                                                NodeList soldiersData = cellData.getChildNodes();
                                                for (int l = 0; l < soldiersData.getLength(); l++) {
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
                                                if (level > 0 && level < 4) {
                                                    defenceTower = new DefenceTower(cell.getOwner(), level);
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
                                                cell.setElementOn(new Mine(cell.getOwner()));
                                                break;
                                            case "tree":
                                                cell.setElementOn(new Tree());
                                                break;
                                        }
                                        cells.add(cell);
                                    }

                                }
                            }
                            movesCells.add(cells);
                        }
                    }
                    replay.add(movesCells);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new WrongFormatException();
        }

    }

    public void jump(int turn) {
        if (turn < replay.size() && turn >= 0) {
            this.turn = turn;
        }
    }

    public boolean nextTurn() {
        if (turn + 1 < replay.size()) {
            turn++;
            return true;
        }
        return false;
    }

    public boolean previousTurn() {
        if (turn - 1 >= 0) {
            turn--;
            return true;
        }
        return false;
    }

    public void stopAutoDisplay() {
        autoDisplay = false;
    }

    public boolean previous() {
        moveDone--;
        if (move - 1 >= 0) {
            move--;
            return true;
        }
        previousTurn();
        move = getReplay().get(turn).size() - 1;
        return false;
    }

    public boolean next() {
        moveDone++;
        if (move + 1 < replay.get(turn).size()) {
            move++;
            return true;
        }
        move = 0;
        nextTurn();
        return false;
    }

    public ArrayList<String> getReplays() throws WrongFormatException {
        ArrayList<String> replays = new ArrayList<>();
        try {
//            File file = new File(Gdx.files.getLocalStoragePath().concat("assets/Saves/games.xml"));
            File file = new File("assets/Saves/games.xml");
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
                    if (!pending) {
                        replays.add(game.getAttribute("replay"));
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new WrongFormatException();
        }
        return replays;
    }


    public ArrayList<ArrayList<ArrayList<Cell>>> getReplay() {
        return this.replay;
    }

    public void setReplay(ArrayList<ArrayList<ArrayList<Cell>>> replay) {
        this.replay = replay;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int[] getTurnAndMove(int totalMove) {
        int k = totalMove;
        int[] res = new int[]{k, 0};
        for (int i = 0; i < replay.size(); i++) {
            if (replay.get(i).size() < res[0]) {
                res[0] -= replay.get(i).size();
                res[1]++;
            }
        }
        return res;
    }

    public int getTotalMove() {
        int res = 0;
        for (int i = 0; i < replay.size(); i++) {
            for (int j = 0; j < replay.get(i).size(); j++) {
                res++;
            }
        }
        return res;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setReplayFileName(String replayFileName) {
        this.replayFileName = replayFileName;
    }

    public boolean isAutoDisplay() {
        return autoDisplay;
    }

    public void setAutoDisplay(boolean autoDisplay) {
        this.autoDisplay = autoDisplay;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getMoveDone() {
        return moveDone;
    }

    public void setMoveDone(int moveDone) {
        this.moveDone = moveDone;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
