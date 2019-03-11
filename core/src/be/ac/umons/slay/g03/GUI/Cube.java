package be.ac.umons.slay.g03.GUI;

public class Cube {
    private double x,y,z;
    public Cube(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void round(){
        int rx = (int)Math.round(x);
        int ry = (int)Math.round(y);
        int rz = (int)Math.round(z);
        double xDiff = Math.abs(rx-x);
        double yDiff = Math.abs(ry-y);
        double zDiff = Math.abs(rz-z);
        if (xDiff > yDiff && xDiff > zDiff){
            rx = -ry-rz;
        }
        else if (yDiff > zDiff){
            ry = -rx-rz;
        }
        else {
            rz = -rx-ry;
        }
        this.x = rx;
        this.y = ry;
        this.z = rz;
    }
    public int[] toEvenCoor(){
        int col = (int)(this.x + (this.z + ((int)this.z&1)) / 2);
        int row = (int)this.z;
        return new int[]{col, row};
    }

    public int[] toOddCoor(){
        int col = (int)this.x + ((int)this.z - ((int)this.z&1)) / 2;
        int row = (int)this.z;
        return new int[]{col, row};
    }

}
