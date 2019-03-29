package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Cell;
import be.ac.umons.slay.g03.Core.Map;
import be.ac.umons.slay.g03.Core.Player;

import java.util.ArrayList;

/**
 * Classe instanciant une AttackTower, contenat un level, et un boolean qui dit si l' AttackTower a attaque ou non
 * Ainsi qu'une methoide pour attaquer
 */
public class AttackTower extends Infrastructure {
    private int level;
    private boolean hasAttack;

    public AttackTower(Player owner, int level) {
        super(owner);
        this.level = level;
        switch (level) {
            case 0:
                this.creationCost = 20;
                this.maintenanceCost = 2;
                break;
            case 1:
                this.creationCost = 40;
                this.maintenanceCost = 6;
                break;
            case 2:
                this.creationCost = 80;
                this.maintenanceCost = 12;
                break;
            case 3:
                this.creationCost = 160;
                this.maintenanceCost = 36;
                break;
        }
    }


    public boolean select() {
        return getOwner().isTurn() && !hasAttack;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    /**
     * permet de faire attaquer une AttackTower
     * @param map
     * @param tower la Cell de la tour qui attaque
     * @param enemy la Cell l'ennemi qui est attaque
     */
    public void attack(Map map, Cell tower, Cell enemy) {
        if (tower.towerRange(map).contains(enemy) && enemy.getElementOn() != null) {
            if (enemy.getElementOn().getOwner() == null) {
                enemy.setElementOn(null);
                hasAttack = true;
            } else if (!(enemy.getElementOn() instanceof Mine) && enemy.getElementOn().getLevel() < level) {
                enemy.setElementOn(null);
                hasAttack = true;

            }
        }
    }

    public boolean isHasAttack() {
        return hasAttack;
    }

    public void setHasAttack(boolean hasAttack) {
        this.hasAttack = hasAttack;
    }
}
