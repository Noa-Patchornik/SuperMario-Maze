package algorithms.mazeGenerators;

import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

public class Maze {
    int rows;
    int cols;
    int[][]maze;
    private Position[][] pos;
    Position initState;
    Position goalState;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze= new int [rows][cols];
        pos = new Position[rows][cols];
    }

    public Maze(int rows, int cols, int type) {
        this.rows = rows;
        this.cols = cols;
        maze= new int [rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                maze[i][j]= 1;
            }
        }
        pos = new Position[rows][cols];

    }

    public int getRows() {
        return rows;
    }

    public void setMaze(int r, int c, int change) {
        this.maze[r][c]=change;
    }

    public int getCols() {
        return cols;
    }

    public int[][] getMaze() {
        return maze;
    }


    public void setStartPosition() {
        this.initState = this.randomaize();
    }

    private Position randomaize() {
        Position pos=null;
        Random myrandom = new Random();
        int numrow;
        int row = myrandom.nextInt(2);
        int col = myrandom.nextInt(2);
        if(col==0 &&row==0) {
            numrow = myrandom.nextInt(this.rows);
            pos = this.getPos(numrow,0);
        }
        else if(col==1 && row==0){
            numrow = myrandom.nextInt(this.cols);
            pos = this.getPos(0, numrow);
        }
        else if( col==1 && row==1){
            numrow = myrandom.nextInt(this.rows);
            pos = this.getPos(numrow, this.cols-1);

        }
        else if(col==0 && row==1){
            numrow = myrandom.nextInt(this.cols);
            pos = this.getPos(this.rows-1, numrow);
        }
        return pos;
    }

    public void setGoalPosition() {

        while (true){
            Position pos = randomaize();
            if (pos.getRowIndex() == this.initState.getRowIndex() && pos.getColumnIndex() == this.initState.getColumnIndex()) {
                continue;
            }
            if( pos.getColumnIndex() == this.initState.getColumnIndex()){
                continue;
            }
            if(pos.getRowIndex() == this.initState.getRowIndex()){
                continue;
            }
            else{
                this.goalState=pos;
                return;
            }
        }

    }

    public void print(){
        System.out.println();
        if(this.initState!=null)
            maze[initState.getRowIndex()][initState.getColumnIndex()]=2;
        if(this.goalState!=null)
            maze[goalState.getRowIndex()][goalState.getColumnIndex()]=3;
        for(int r=0;r<this.rows;r++){
            System.out.print("[");
            for(int c=0;c<this.cols;c++) {
                if (maze[r][c] == 2) {
                    System.out.print(" S ,");
                } else if (maze[r][c] == 3) {
                    System.out.print(" E ,");
                } else {
                    if (c != this.cols - 1)
                        System.out.print(" " + this.maze[r][c] + " ,");
                    else
                        System.out.print(" " + this.maze[r][c]);
                }
            }
            System.out.print("]\n");
        }
        System.out.println();
    }


    public boolean ValidRCell(int i) {
        return i >= 0 && i < this.rows;
    }

    public boolean ValidCCell(int i) {
        return i >= 0 && i < this.cols;
    }

    public Position getPos(int i, int column) {
        if (pos[i][column]==null)
            pos[i][column]=new Position(i,column);
        return pos[i][column];
    }

    public Position[][] getPosarray(){
        return this.pos;
    }

    public Position getStartPosition() {
        return this.initState;
    }

    public Position getGoalPosition() {
        return this.goalState;    }

    public int getCellMaze(int row,int col){
        return maze[row][col];
    }
}
