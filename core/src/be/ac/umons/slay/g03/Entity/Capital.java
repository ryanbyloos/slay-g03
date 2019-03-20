package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Capital extends MapElement {
    private int money;

    public Capital(Player owner, int money) {
        super(0, 0, owner);
        this.money = money;
    }

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

    public void addMoneyThirdCapital(Capital capital1, Capital capital2) {
        if (capital1.getMoney() > capital2.getMoney()) {
            setMoney(capital2.getMoney());
        } else {
            setMoney(capital1.getMoney());
        }
    }

    public void splitMoney(Capital newCapital1, Capital newCapital2, int nbCellTot, int nbCellOldCapital, int nbCellNewCapital1, int nbCellNewCapital2) {
        double money = this.money;

    }

    public void addMoney(int gain) {
        money = money + gain;
    }

    public void setMoney(int money) {
        this.money = money;
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

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + " with : " + money;
    }
}
