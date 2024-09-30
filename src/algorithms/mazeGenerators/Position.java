package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Position implements Serializable {

    private int row;

    public void setSuc(Position pos) {
        this.suc= pos;
    }

    private int column;

    public Position getSuc() {
        return suc;
    }

    private Position suc;

    public Position(int i, int col) {
        row = i;
        column = col;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private boolean visited = false;

    public Position() {

    }

    public int getRowIndex() {
        return this.row;

    }

    public int getColumnIndex() {
        return this.column;
    }

    //checks all four directions (up, down, left, right).
    //only adds a neighbor if it's within the maze boundaries.
    public List<Position> getNeigh(int rowmax, int colmax,Maze m) {
        List<Position> ans = new ArrayList<>();
        if (rowmax > this.row + 1) {
            ans.add(m.getPos(this.row + 1, this.column));
        }
        if (this.row - 1 >= 0)
            ans.add(m.getPos(this.row - 1, this.column));

        if (colmax > this.column + 1)
            ans.add(m.getPos(this.row, this.column + 1));
        if (this.column - 1 >= 0)
            ans.add(m.getPos(this.row, this.column - 1));

        return ans;
    }

    //filters a list of positions to find "valid" neighbors
    public List<Position> getvalidneigh(List<Position> tmp, int row, int col,Maze m) {
        List<Position> ans = new ArrayList<>();
        List<Position> ans2 = new ArrayList<>();
        boolean flag = true;
        boolean flag2 = true;
        //go through the list of my neighbors, for each get the neighbors of their neighbors
        for (int i = 0; i < tmp.size(); i++) {
            ans = tmp.get(i).getNeigh(row, col,m);
            flag=true;
            //go through the list of the neighbors of neighbors
            for (int k = 0; k < ans.size(); k++) {
                if(ans.get(k)==this){
                    continue;
                }
                //if the cell is visited or is in the list already break and go to next neighbor
                if (ans.get(k).isVisited() || ans2.contains(tmp.get(i))) {
                    flag=false;
                    break;
                }
            }
            //if the cell is not visited and not in the list already add it to the list
            if(flag)
                ans2.add(tmp.get(i));
        }
        return ans2;
    }

    public List<Position> GETNEIGHBORS(Maze m) {
        List<Position> neighbors = this.getNeigh(m.rows,m.cols,m);
        List<Position> tmp = new ArrayList<>();
        for(int i=0;i<neighbors.size();i++){
            if(!neighbors.get(i).isVisited())
                tmp.add(neighbors.get(i));
        }

        return this.getvalidneigh(tmp,m.rows,m.cols,m);

    }

    //overriding toString method for print out
    @Override
    public String toString(){
        return "{"+this.row+","+this.column+"}";
    }


    //overriding the equals function for comparison between positions
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            Position p = (Position)obj;
            if(this.row==p.row && this.column==p.column){
                return true;
            }
        }
        return false;
    }
}

