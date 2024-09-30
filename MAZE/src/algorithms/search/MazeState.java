package algorithms.search;

public class MazeState extends AState  {
    private int r;
    private int c;


    public MazeState(int r, int c) {
        super();
        this.r = r;
        this.c = c;
    }


    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String toString() {
        return "{" + r + "," + c + "}";
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof MazeState))
            return false;
        if (this.getR() == ((MazeState) o).r && this.getC() == ((MazeState) o).c)
            return true;
        return false;
    }

}