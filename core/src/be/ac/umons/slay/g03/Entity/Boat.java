package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

/**
 * Classe instanciant un Boat contenant la distance qu'il peut encore parcourir et sa defence.
 * Contient des methodes permetent de capturer un Boat et de faire embarquer/deplyer des Soldier
 */
public class Boat extends Infrastructure implements Controlable {

    private int t;
    private int defence;

    private ArrayList<Soldier> soldiers = new ArrayList<>();

    public Boat(Player player) {
        super(player);
        setHasMoved(false);
        this.defence = 0;
        this.maintenanceCost = 0;
        this.creationCost = 25;
    }

    /**
     * permet de deployer le dernier soldat monte dns le Boat
     *
     * @param source      Cell du bateau
     * @param destination Cell du deployment
     * @param map
     */
    public void deploy(Cell source, Cell destination, Map map) {
        if (source.adjacentCell(map,source,false).contains(destination) && soldiers.size()>0){
            Soldier soldier = soldiers.get(soldiers.size()-1);
            soldiers.remove(soldier);
            getDefence();
            soldier.move(new Cell(source.getX(),source.getY(), false,true,getOwner(),null),destination,map);
        }
    }


    /**
     * permet d'attquer un Bot avec un Soldier
     * @param soldier Soldier attaquant le Boat
     * @return
     */
    public boolean capture(Soldier soldier) {
        if(soldier.getLevel() > defence){
            this.setOwner(soldier.getOwner());
            this.soldiers = new ArrayList<>();
            this.soldiers.add(soldier);
            defence = soldier.getLevel();
            return true;
        }
        return false;
    }

    /**
     * permeet de faire embarquer un Soldier dans un Boat
     * @param soldier Soldier qui souhaite embrquer dns le Boat
     * @return vrai si le Soldier a embarque, faux sinon
     */
    public boolean bord(Soldier soldier){
        if(soldiers.size()<6){
            soldiers.add(soldier);
            getDefence();
            return true;
        }
        return false;
    }

    /** permet de deplacer un Boat
     * @param source Cell de depart du Boat
     * @param destination Cell de destintion de Bot
     * @param map
     */
    @Override
    public void move(Cell source, Cell destination, Map map) {
        if(t>0){
            if(source.adjacentCell(map,source,true).contains(destination)){
                if(destination.getElementOn() == null){
                    t = t-1;
                    destination.setElementOn(this);
                    source.setElementOn(null);
                }
                if(destination.getElementOn() instanceof Mine){
                    destination.setElementOn(null);
                    source.setElementOn(null);
                }
            }
        } else {
            this.setHasMoved(true);
        }
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getDefence() {
        if (soldiers != null && soldiers.size() != 0) {
            for (Soldier soldier : soldiers)
                if (soldier.getLevel() >= this.defence)
                    this.defence = soldier.getLevel();
            return this.defence;
        }
        defence = 0;
        return 0;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    @Override
    public boolean select() {

        return t>0 && getOwner().isTurn() && !isHasMoved();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Boat)) {
            return false;
        }
        Boat that = (Boat) other;
        return this.getCreationCost() == (that.getCreationCost())
                && this.getMaintenanceCost() == (that.getMaintenanceCost())
                && this.getT() == that.getT()
                && this.getDefence() == that.getDefence()
                && this.getOwner().equals(that.getOwner())
                && this.isHasMoved() == that.isHasMoved()
                && this.getSoldiers().equals(that.getSoldiers());
    }

}