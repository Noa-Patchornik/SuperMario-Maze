package algorithms.mazeGenerators;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

public class Maze implements Serializable {
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

    public Position getStartPosition() {
        return this.initState;
    }

    public Position getGoalPosition() {
        return this.goalState;    }

    public int getCellMaze(int row,int col){
        return maze[row][col];
    }

//--------------------------------------------------------------------------------------------------------------//
     //constructor gets a byte[] array and decoding it to maze
//    public Maze(byte[] arr){
//        int startposR=0;
//        int startposC=0;
//        int goalposR=0;
//        int goalposC=0;
//        int i=0;
//
//    // for start position row index
//        startposR=decode(arr,0);
//    // for start position col index
//        startposC=decode(arr,4);
//        setStartPos(startposR,startposC);
//    // for goal position row index
//        goalposR=decode(arr,8);
//    // for goal position col index
//        goalposC=decode(arr,12);
//        setGoalPos(goalposR,goalposC);
//        int rows=0;
//        int cols=0;
//        //for the size of the maze rows and cols
//        rows=decode(arr,16);
//        cols=decode(arr,20);
//        this.rows = rows;
//        this.cols = cols;
//        i=24;
//        this.maze = new int[rows][cols];
//        //for the contact of the maze
//        for(int r=0;r<rows;r++) {
//            for (int c = 0; c < cols; c++) {
//                //make sure that if it's the start position or the goal position it would be 0 in the maze
//                if(r==startposR && c == startposC){
//                    maze[r][c] = 0;
//                }
//                else if(r ==goalposR && c==goalposC ){
//                    maze[r][c] = 0;
//                }
//                else {
//                    maze[r][c] = Byte.toUnsignedInt(arr[i]);
//                }
//                i++;
//            }
//        }
//    }
//
//    //decoding each element in the byte array, each 4 bytes represent 1 element
//    public int decode(byte[] arr, int i) {
//        // Create a ByteBuffer that wraps the byte array starting at index i
//        ByteBuffer buffer = ByteBuffer.wrap(arr, i, 4);  // Wrap 4 bytes starting from index i
//        return buffer.getInt(); // Decode these bytes as an integer
//    }
//
//    //function that return the maze as byte array: start position, end position, size and the contact of the maze
//    public byte[] toByteArray() {
//        List<Byte> list = new ArrayList<>();  // Initialize list
//
//        // Convert the start position, goal position, and maze size into bytes
//        toByte(this.initState.getRowIndex(), list);
//        toByte(this.initState.getColumnIndex(), list);
//        toByte(this.goalState.getRowIndex(), list);
//        toByte(this.goalState.getColumnIndex(), list);
//        toByte(this.rows, list);
//        toByte(this.cols, list);
//
//        // Convert each maze element into bytes
//        for (int r = 0; r < this.rows; r++) {
//            for (int c = 0; c < this.cols; c++) {
//                list.add((byte) this.maze[r][c]);  // Add each element of the maze
//            }
//        }
//
//        // Now create the byte array from the list
//        byte[] result = new byte[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            result[i] = list.get(i);  // Copy elements from the list to the array
//        }
//        return result;
//    }
//
//
//    //adding each element to the list as the right amount as signed bytes from -128 to 127
//    public void toByte(int r, List<Byte> list) {
//        ByteBuffer buffer = ByteBuffer.allocate(4);  // Allocate 4 bytes for the integer
//        buffer.putInt(r);  // Put the integer in the buffer
//        // Add the individual bytes from the buffer to the list
//        for (int i = 0; i < 4; i++) {
//            list.add(buffer.get(i));  // Extract each byte from the buffer and add to list
//        }
//    }
//
//
//
//
//    public void setGoalPos(int r, int c) {
//        this.goalState = new Position(r,c);
//    }
//
//    public void setStartPos(int r, int c) {
//        this.initState = new Position(r,c);
//    }


//------------------------------------------YAKI----------------------------------------------------------------------//

    public void setGoalPos(int r, int c) {
        this.goalState = new Position(r,c);
    }

    public void setStartPos(int r, int c) {
        this.initState = new Position(r,c);
    }

    //for serialization (communication between client and server)
    public void toByte(int r,List<Byte> list){
        int counter=0;
        // in order to stay in the byte limitations
        while(r>255){
            list.add((byte) 127);
            counter++;
            r=r-255;
        }
        r=r-128;
        list.add((byte)r);
        counter++;
        while(counter<4){
            list.add((byte)-128);
            counter++;
        }
    }

    public byte[] toByteArray(){
        //save the raw data about the maze
        List<Byte> list = new ArrayList<>();
        toByte(this.initState.getRowIndex(),list);
        toByte(this.initState.getColumnIndex(),list);
        toByte(this.goalState.getRowIndex(),list);
        toByte(this.goalState.getColumnIndex(),list);
        toByte(this.rows,list);
        toByte(this.cols,list);

        // each element in the maze
        for(int r=0;r<this.rows;r++){
            for(int c=0;c<this.cols;c++){
                list.add((byte)this.maze[r][c]);
            }
        }

        //for returning a [] and not a list as requested
        byte[] result = new byte[list.size()];
        for(int i=0;i<list.size();i++){
            result[i] = list.get(i);
        }
        return result;
    }


    public int decodeElement(byte[] arr,int i){
        int wanted=0;
        int index=4;
        while(index>0) {
            wanted =wanted + arr[i] + 128;
            index--;
            i++;
        }
        return wanted;
    }

    // constructor gets a byte[] array and decoding it to maze/
    public Maze(byte[] arr){
        int initR=0;
        int initC=0;
        int goalR=0;
        int goalC=0;
        int i=0;

        // for init position row index
        initR=decodeElement(arr,i);
        // for init position col index
        initC=decodeElement(arr,4);
        setStartPos(initR,initC);
        // for goal position row index
        goalR=decodeElement(arr,8);
        // for goal position col index
        goalC=decodeElement(arr,12);
        setGoalPos(goalR,goalC);
        int rows=0;
        int cols=0;
        rows=decodeElement(arr,16);
        cols=decodeElement(arr,20);
        this.rows = rows;
        this.cols = cols;
        i=24;
        this.maze = new int[rows][cols];
        for(int r=0;r<rows;r++) {
            for (int c = 0; c < cols; c++) {
                if(r==initR && c == initC){
                    maze[r][c] = 0;
                }
                else if(r ==goalR && c==goalC ){
                    maze[r][c] = 0;
                }
                else {
                    maze[r][c] = Byte.toUnsignedInt(arr[i]);
                }
                i++;
            }
        }
    }
}
