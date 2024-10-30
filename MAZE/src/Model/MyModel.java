package Model;

import algorithms.mazeGenerators.*;
import algorithms.search.ISearchable;
import algorithms.search.SearchableMaze;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MyModel extends Observable implements IModel{

    private int [][]maze;
    private int rowChar;
    private int colChar;
    private ISearchable searchable;
    private Maze mymaze;


    public MyModel() {
        maze = null;
        rowChar =0;
        colChar =0;
    }

    public void setRowChar(int rowChar) {
        this.rowChar = rowChar;
    }

    public void setColChar(int colChar) {
        this.colChar = colChar;
    }

    public void updateCharacterLocation(int direction)
    {
        /*
            direction = 1 -> Up
            direction = 2 -> Down
            direction = 3 -> Left
            direction = 4 -> Right
         */
        switch (direction) {
            case 1: // Up
                if (rowChar > 0 && maze[rowChar - 1][colChar] != 1) {
                    rowChar--;
                }
                break;
            case 2: // Down
                if (rowChar < maze.length - 1 && maze[rowChar + 1][colChar] != 1) {
                    rowChar++;
                }
                break;
            case 3: // Left
                if (colChar > 0 && maze[rowChar][colChar - 1] != 1) {
                    colChar--;
                }
                break;
            case 4: // Right
                if (colChar < maze[0].length - 1 && maze[rowChar][colChar + 1] != 1) {
                    colChar++;
                }
        }
    }

    public int getRowChar() {
        return rowChar;
    }

    public int getColChar() {
        return colChar;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze(int[][] maze) {
        //Solving maze
        setChanged();
        notifyObservers();
    }

    @Override
    public void getSolution() {
        //return this.solution;
    }


    public ISearchable generateRandomMaze(int row, int col,String selectedSearchable)
    {
        this.mymaze = new Maze(row,col);
        AMazeGenerator typeToGenerate = switch (selectedSearchable) {
            case "simpleMaze" -> new SimpleMazeGenerator();
            case "emptyMaze" -> new EmptyMazeGenerator();
            case "MyMaze" -> new MyMazeGenerator();
            default -> new MyMazeGenerator();
        };
        this.mymaze= typeToGenerate.generate(row,col);
        this.mymaze.print();
        this.searchable = new SearchableMaze(mymaze);
        this.maze = mymaze.getMaze();
        this.rowChar=this.mymaze.getStartPosition().getRowIndex();
        this.colChar = this.mymaze.getStartPosition().getColumnIndex();

        setChanged();
        return this.searchable;
    }

    public int[][] getMaze() {
        return maze;
    }
}
