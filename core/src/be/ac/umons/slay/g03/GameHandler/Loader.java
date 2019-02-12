package be.ac.umons.slay.g03.GameHandler;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Entity.MapElement;
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
    private String tmxFile;
    private String xmlFile;

    public Loader(String tmxFile, String xmlFile){
        this.tmxFile = tmxFile;
        this.xmlFile = xmlFile;
    }
    public void load(Map map){

    }

    public void loadFromXmlFile(Map map){
        String path = Gdx.files.internal(xmlFile).file().getAbsolutePath();
        String absolutePath = path.substring(0, path.length()-xmlFile.length()).concat("src"+File.separator+"be"+File.separator+"ac"+File.separator+"umons"+File.separator+"slay"+File.separator+"g03"+File.separator+"World"+File.separator).concat(xmlFile);
        File file = new File(absolutePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            ArrayList<Node> all = new ArrayList<Node>();
            NodeList items = doc.getElementsByTagName("items");
            NodeList units = doc.getElementsByTagName("units");
            NodeList infrastructures = doc.getElementsByTagName("infrastructures");
            for (int i = 0; i < items.getLength(); i++) {
                Node node= items.item(i);
                all.add(node);
            }
            for (int i = 0; i < units.getLength(); i++) {
                Node node= units.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    all.add(node);
                }

            }
            for (int i = 0; i < infrastructures.getLength(); i++) {
                Node node= infrastructures.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    all.add(node);
                }
            }
            for (int i = 0; i < all.size(); i++) {
                Element element = (Element)all.get(i);
                String type = element.getElementsByTagName("type").item(0).getTextContent();
                if(type.equals("capital")){
                    int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
                    int y = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());
                    int money = Integer.parseInt(element.getElementsByTagName("money").item(0).getTextContent());
                    int player = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());
                    Cell cell = map.findMapElement(x,y);
                    if (cell != null){
                        // à reprendre
                    }
                }
                else if(type.equals("soldier")){

                }
                else if(type.equals("infrastructure")){

                }

            }




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void loadFromTmxFile(Map map){
        try{
            String path = Gdx.files.internal(tmxFile).file().getAbsolutePath();
            String absolutePath = path.substring(0, path.length()-tmxFile.length()).concat("src"+File.separator+"be"+File.separator+"ac"+File.separator+"umons"+File.separator+"slay"+File.separator+"g03"+File.separator+"World"+File.separator).concat(tmxFile);
            TiledMap tiledMap = new TmxMapLoader().load(absolutePath);
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)tiledMap.getLayers().get("map"); // recupère la couche map
            int width = tiledLayer.getWidth();
            int heigth = tiledLayer.getHeight();
            TiledMapTileLayer.Cell cell;
            //parcours les tuiles de la couche et soustrais les informations de chaque tuiles (available, player, ect ...)

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    cell = tiledLayer.getCell(i, j);
                    if(cell != null){
                        //Construis des Cells en fonctions des informations et les ajoutes dans l'arraylist de la map
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
        catch (GdxRuntimeException e){
            e.printStackTrace();
        }
    }
}
