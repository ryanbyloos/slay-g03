package be.ac.umons.slay.g03.Entity;

import be.ac.umons.slay.g03.Core.Player;

public class Capital extends MapElement {
    private int money;

    public Capital(Player owner, int money) {
        super(0,0, owner);
        this.money = money;
    }

    public void addMoney(int gain) {
        money = money+gain;
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
}
