package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

/**
 * Classe instanciant une Capital, qui contient de l'argent
 * Elle dispose de methode permetant de gerer l'argent après un split de territoire
 */
public class Capital extends MapElement {
    private int money;

    public Capital(Player owner, int money) {
        super(owner);
        this.money = money;
    }

    /**gere la redistribution de l'argent apres un slpit
     * @param newCapital Capital cree après un slipt
     * @param nbCellTot nombre de Cell de l'ancien Territory
     * @param nbCellOldCapital nombre de Cell du nouveu Territory de l'ancienne capital
     * @param nbCellNewCapital nombre de Cell du nouveu Territory de la nouvelle capital
     */
    public void splitMoney(Capital newCapital, int nbCellTot, int nbCellOldCapital, int nbCellNewCapital) {
        double money = this.money;
        newCapital.setMoney((int) Math.round(money * nbCellNewCapital / nbCellTot));
        this.money = (int) Math.round(money * nbCellOldCapital / nbCellTot);

        if (nbCellTot != nbCellNewCapital + nbCellOldCapital && money / 3 != this.money) {
            this.money = this.money + 1;
        } else if (this.money + newCapital.getMoney() != money) {
            newCapital.addMoney(-1);
        }
    }

    /**
     * gere l'argent de la Capital cree lors d'un triple split
     * @param capital1 ancienne Capital
     * @param capital2 l'autre ancienne Capital
     */
    public void addMoneyThirdCapital(Capital capital1, Capital capital2) {
        if (capital1.getMoney() > capital2.getMoney()) {
            setMoney(capital2.getMoney());
        } else {
            setMoney(capital1.getMoney());
        }
    }

    public void addMoney(int gain) {
        money = money + gain;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Capital)) {
            return false;
        }
        Capital that = (Capital) other;
        return this.getCreationCost() == (that.getCreationCost())
                && this.getMaintenanceCost() == (that.getMaintenanceCost())
                && this.getMoney() == that.getMoney()
                && this.getOwner().equals(that.getOwner());
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + " with : " + money;
    }
}
