package Model;

import Server.Configurations;
import algorithms.mazeGenerators.*;
import algorithms.search.ISearchable;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MyModel extends Observable implements IModel{

    private int [][]maze;
    private int rowChar;
    private int colChar;
    private ISearchable searchable;
    private Maze mymaze;
    private Solution solution;


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

    /**
     * update the loctaion of the player, check if it can move, so it won't go on walls and not go out of the maze length
     * @param direction
     */
    public void updateCharacterLocation(int direction)
    {
        /*
        direction = 1 -> Move Down-Left (row+1, col-1)
        direction = 2 -> Move Down (row+1,col)
        direction = 3 -> Move Down-Right (row+1,col+1)
        direction = 4 -> Move left (row, col-1)
        direction = 6 -> Move Right (row, col+1)
        direction = 7 -> Move UP-Left (row-1, col-1)
        direction = 8 -> Move Up (row-1 , col)
        direction = 9 -> Move Up-Right (row-1, col+1)
         */
        //in each case checking if the move is legal (in the bounds of the maze and not wall)
        switch (direction) {
            case 1: // Down-Left
                if (rowChar < maze.length - 1 && colChar > 0 && maze[rowChar + 1][colChar-1] != 1) {
                    rowChar++;
                    colChar--;
                }
                break;
            case 2: // Down
                if (rowChar < maze.length - 1 && maze[rowChar + 1][colChar] != 1) {
                    rowChar++;
                }
                break;
            case 3: // Down-Right
                if (rowChar < maze.length - 1 && colChar < maze[0].length - 1 && maze[rowChar + 1][colChar+1] != 1) {
                    rowChar++;
                    colChar++;
                }
                break;
            case 4: // left
                if (colChar > 0 && maze[rowChar][colChar - 1] != 1) {
                    colChar--;
                }
                break;
            case 6: // Right
                if (colChar < maze[0].length - 1 && maze[rowChar][colChar + 1] != 1) {
                    colChar++;
                }
                break;
            case 7: // UP-Left
                if (rowChar > 0 && colChar > 0 && maze[rowChar - 1][colChar-1] != 1) {
                    rowChar--;
                    colChar--;
                }
                break;
            case 8: // Up
                if (rowChar > 0 && maze[rowChar - 1][colChar] != 1) {
                    rowChar--;
                }
                break;
            case 9: // Up-Right
                if (rowChar > 0 && colChar < maze[0].length - 1 && maze[rowChar - 1][colChar + 1] != 1) {
                    rowChar--;
                    colChar++;
                }
                break;
        }
        if(rowChar== this.searchable.getGoalState().getR() && colChar == this.searchable.getGoalState().getC()){
            setChanged();
            notifyObservers("Goal State");
        }
    }

    public int getRowChar() {
        return rowChar;
    }

    public int getColChar() {
        return colChar;
    }

    /**
     * add an observer to the model, so it will get update when there is change
     * @param o
     */
    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    /**
     * solve the maze and update it
     * @param
     */
    @Override
    public Solution solveMaze(ISearchable searchable,String selectedSearching) {
        //update the configuration file with the selected value from the client
        Configurations con = Configurations.getInstance();
        con.setProp("mazeSearchingAlgorithm", selectedSearching);
        //Solving maze
        MyModelServerClient myModelServerClient= new MyModelServerClient();
        myModelServerClient.ServerSolvingMaze(searchable);
        return myModelServerClient.solution;
    }

    @Override
    public Solution getSolution() {
        return this.solution;
    }

    /**
     * generate the maze as Isearchable using the type that the client choose on the app
     * @param row
     * @param col
     * @param selectedSearchable
     * @return
     */
    public ISearchable generateRandomMaze(int row, int col,String selectedSearchable)
    {
//        AMazeGenerator typeToGenerate ;
//                this.mymaze = new Maze(row,col);
//        if(selectedSearchable!=null) {
//            typeToGenerate = switch (selectedSearchable) {
//                case "simpleMaze" -> new SimpleMazeGenerator();
//                case "emptyMaze" -> new EmptyMazeGenerator();
//                case "MyMaze" -> new MyMazeGenerator();
//                default -> new MyMazeGenerator();
//            };
//        }
//        else{
//            typeToGenerate = new MyMazeGenerator();
//        }

        //update the configuration file with the selected value from the client
        Configurations con = Configurations.getInstance();
        con.setProp("mazeGeneratingAlgorithm", selectedSearchable);
        //using the client server from part 2 to generate the maze
        MyModelServerClient myModelServerClient = new MyModelServerClient();
        myModelServerClient.ServerGenerateMaze(row,col);
        this.mymaze = myModelServerClient.mymaze;
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
