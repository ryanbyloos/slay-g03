package be.ac.umons.slay.g03.GameHandler;

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

/**
 * Classe qui contient le fichier xml avec les logs
 * Elle contient les methodes permetant de gerer les connexions, la creation de compte
 * ainsi que de recuperer les scores de tout les joueurs
 */
public class Authenticator {
    private String loginFile;

    public Authenticator(String loginFile) throws AuthenticationError {

        if (!Gdx.files.internal(loginFile).exists()) {
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

            } catch (Exception e) {
                throw new AuthenticationError();
            }
        }
        this.loginFile = loginFile;

    }

    /**
     * @param userName Nom de compte du joueur
     * @param pwd Mot de passe du joueur
     * @return un boolean vrai, si le compte existe et si le mot de passe est correct, faux sinon
     * @throws AuthenticationError
     */
    public boolean login(String userName, String pwd) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("userName").equals(userName) && eElement.getAttribute("password").equals(hashPassword(pwd)))
                        return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new AuthenticationError();
        }
    }

    /**
     * recupère le pseudo du joueur à partir de son nom de compte
     * @param userName
     * @return le pseudo du joueur
     * @throws AuthenticationError
     */
    public String getPseudo(String userName) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("userName").equals(userName))
                        return eElement.getAttribute("pseudo");
                }
            }
            return null;
        } catch (Exception e) {
            throw new AuthenticationError();
        }
    }

    /**
     * @param userName Nom de compte du joueur
     * @param pwd Mot de passe du joueur
     * @param pseudo Pseudo du joueur
     * @param image path de l'avatar du joueur
     * @return true si le nom de compte est disponible et que le compte a ete cree, faux sinon
     * @throws AuthenticationError
     */
    public boolean register(String userName, String pwd, String pseudo, String image) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getAttribute("userName").equals(userName)) return false;
                    if (eElement.getAttribute("pseudo").equals(pseudo)) return false;
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

        } catch (Exception e) {
            throw new AuthenticationError();
        }
        return true;
    }

    /** actualise le score des joueurs en fonction de leur resultat
     * @param winner Player gagnant
     * @param loser Player perdant
     * @throws AuthenticationError
     */
    public void addScore(Player winner, Player loser) throws AuthenticationError {
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (!winner.isGuest() && eElement.getAttribute("pseudo").equals(winner.getName())) {
                        eElement.setAttribute("game", Integer.toString(Integer.parseInt(eElement.getAttribute("game")) + 1));
                        eElement.setAttribute("gameWin", Integer.toString(Integer.parseInt(eElement.getAttribute("gameWin")) + 1));
                    }
                    if (!loser.isGuest() && eElement.getAttribute("pseudo").equals(loser.getName())) {
                        eElement.setAttribute("game", Integer.toString(Integer.parseInt(eElement.getAttribute("game")) + 1));
                        eElement.setAttribute("gameLose", Integer.toString(Integer.parseInt(eElement.getAttribute("gameLose")) + 1));
                    }
                }
            }

        } catch (Exception e) {
            throw new AuthenticationError();
        }
    }

    /** recupere le score de tout les joueurs enregistrés
     * @return Une ArrayList contenant le score de tout les joueurs enregistrés
     * @throws AuthenticationError
     */
    public ArrayList<Score> getScore() throws AuthenticationError {
        ArrayList<Score> allScore = new ArrayList<>();
        try {
            File inputFile = new File(loginFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int[] score = new int[4];
                    score[0] = Integer.parseInt(eElement.getAttribute("game"));
                    score[1] = Integer.parseInt(eElement.getAttribute("gameWin"));
                    score[2] = Integer.parseInt(eElement.getAttribute("gameLose"));
                    if (score[0] != 0) score[3] = (score[1] / score[0]) * 100;
                    else score[3] = 0;
                    allScore.add(new Score(eElement.getAttribute("pseudo"),score, eElement.getAttribute("image")));

                }
            }
            return allScore;
        } catch (Exception e) {
            throw new AuthenticationError();
        }
    }

    private String hashPassword(String password) {
        String hexString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; i++) {
                hexString = hexString + (Integer.toHexString(0xFF & digest[i]));
            }

        } catch (NoSuchAlgorithmException e) {
            e.fillInStackTrace();
        }
        return hexString;
    }
}