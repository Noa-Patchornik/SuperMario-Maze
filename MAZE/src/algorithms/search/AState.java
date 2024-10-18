package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {

    private double cost =0;
    private AState successor;
    private static int counter;
    private double sum = 0;

    public double getSum(){
        return sum;
    }

    public void setSum(double sumtoadd){
        this.sum +=sumtoadd;
    }

    public abstract String toString();

    public void setSuccessor(MazeState n) {
        successor=n;
    }

    public AState getSuccessor() {
        return  this.successor;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double costof){
        this.cost = costof;
    }

}
