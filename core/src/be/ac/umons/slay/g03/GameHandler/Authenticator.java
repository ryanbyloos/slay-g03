package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;
import com.badlogic.gdx.Gdx;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Authenticator {
    private GameState gameState;
    private String hallOfFameFile;
    private String loginFile;

    public Authenticator(String loginFile) throws AuthenticationError {

        if(!Gdx.files.internal(loginFile).exists()) {
            try {
                File newFile = new File(loginFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                Element root = doc.createElement("users");
                doc.appendChild(root);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(newFile);
                transformer.transform(domSource, streamResult);

            }catch (Exception e){
                throw new AuthenticationError();
            }
        }
        this.loginFile = loginFile;
        this.gameState = new GameState(new Map(new ArrayList<>(), null, null), new Loader(null, null, null), -1, null);
    }

    public boolean login(String userName, String pwd) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength() ; i++) {
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("userName").equals(userName) && eElement.getAttribute("password").equals(hashPassword(pwd)))
                        return true;
                }
            }
            return false;
        }catch (Exception e){
            throw new AuthenticationError();
        }
    }

    public boolean register(String userName, String pwd, String pseudo, String image) throws AuthenticationError {
        try{
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength() ; i++) {
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    if(eElement.getAttribute("userName").equals(userName)) return false;
                    if(eElement.getAttribute("pseudo").equals(pseudo)) return false;
                }
            }

            Element newUser = doc.createElement("user");
            newUser.setAttribute("userName", userName);
            newUser.setAttribute("password", hashPassword(pwd));
            newUser.setAttribute("pseudo", pseudo);
            newUser.setAttribute("image", image);
            newUser.setAttribute("game", "0");
            newUser.setAttribute("gameWin", "0");
            newUser.setAttribute("gameLose", "0");

            doc.getDocumentElement().appendChild(newUser);
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(loginFile);
            transformer.transform(source, result);

        }catch (Exception e){
            throw new AuthenticationError();
        }
        return true;
    }

    public void addScore(Player winner, Player loser) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength() ; i++) {
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    if(! winner.isGuest() && eElement.getAttribute("pseudo").equals(winner.getName())){
                        eElement.setAttribute("game", Integer.toString(Integer.parseInt(eElement.getAttribute("game")) + 1));
                        eElement.setAttribute("gameWin", Integer.toString(Integer.parseInt(eElement.getAttribute("gameWin")) + 1));
                    }
                    if(! loser.isGuest() && eElement.getAttribute("pseudo").equals(loser.getName())){
                        eElement.setAttribute("game", Integer.toString(Integer.parseInt(eElement.getAttribute("game")) + 1));
                        eElement.setAttribute("gameLose", Integer.toString(Integer.parseInt(eElement.getAttribute("gameLose")) + 1));
                    }
                }
            }

        }catch (Exception e){
            throw new AuthenticationError();
        }
    }

    public boolean start(String xmlFile, String tmxFile) {
        return false;
    }

    private String hashPassword(String password){
        String hexString ="";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            for (int i = 0;i<digest.length;i++) {
                hexString = hexString + (Integer.toHexString(0xFF & digest[i]));
            }

        }catch (NoSuchAlgorithmException e){
            e.fillInStackTrace();
        }
        return hexString;
    }

    public GameState getGameState() {
        return gameState;
    }
}