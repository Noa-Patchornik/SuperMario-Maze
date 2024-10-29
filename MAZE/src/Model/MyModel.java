package Model;

import Server.Configurations;
import algorithms.mazeGenerators.*;
import algorithms.search.ISearchable;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private ISearchable searchable;
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private int rows;
    private int cols;
    private Solution solution;

    public MyModel() {
        playerRow=0;
        playerCol=0;
    }

    //generate the maze as the requested in the app
    public ISearchable generateMaze(int rows, int cols, String Searchable){
        Configurations con = Configurations.getInstance();
        con.setProp("mazeGeneratingAlgorithm", Searchable);
        AMazeGenerator type = switch (Searchable) {
            case "simpleMaze" -> new SimpleMazeGenerator();
            case "emptyMaze" -> new EmptyMazeGenerator();
            case "MyMaze" -> new MyMazeGenerator();
            default -> new MyMazeGenerator();
        };
        this.maze = type.generate(rows, cols);
        this.rows = rows;
        this.cols = cols;
        this.searchable = new SearchableMaze(maze);
        setChanged();
        notifyObservers("maze generated");
        return this.searchable;
    }

    //returning the maze object
    public Maze getMaze() {
        return this.maze;
    }

    public int getPlayerRow(){
        return playerRow;
    }
    public int getPlayerCol(){
        return playerCol;
    }

    //add observer to this class
    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze() {
        //solve the maze
        solution = new Solution();
        setChanged();
        notifyObservers("maze solved");
    }

    @Override
    public Solution getSolution() {
        return null;
    }


    //update the position of the player on the screen
    public void updatePlayerLocation(MovementDirection direction) {
        switch (direction) {
            case UP -> {
                if (playerRow > 0)
                    movePlayer(playerRow - 1, playerCol);
            }
            case DOWN -> {
                if (playerRow < rows - 1)
                    movePlayer(playerRow + 1, playerCol);
            }
            case LEFT -> {
                if (playerCol > 0)
                    movePlayer(playerRow, playerCol - 1);
            }
            case RIGHT -> {
                if (playerCol < cols - 1)
                    movePlayer(playerRow, playerCol + 1);
            }
        }

    }

    //change the physical location and notify the observers
    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        notifyObservers("player moved");
    }

    public void drawTmp(Image Playerimg, GraphicsContext graphicsContext, double cellHeight, double cellWidth, int r, int c, double h, double w) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.BLUE);
        Image playerImage = Playerimg;
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);

    }

    //draw the walls of the maze
    public void drawMazeWalls(ISearchable maze, Image img, GraphicsContext graphicsContext, double cellHeight, double cellWidth, double canvasHeight, double canvasWidth) {
//        graphicsContext.clearRect(0,0,rows+1000,cols+1000);
        //if there is a problem with the image, color the walls of the maze in red
        graphicsContext.setFill(Color.RED);
        Maze mymaze = ((SearchableMaze)maze).getOrigionMaze();
        mymaze.print();
        for (int i = 0; i < mymaze.getRows(); i++) {
            for (int j = 0; j < mymaze.getCols(); j++) {
                if(mymaze.getCellMaze(i,j) == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(img == null) {
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                    else {
                        graphicsContext.drawImage(img, x, y, cellWidth, cellHeight);
                    }
                }
            }
        }
    }
}

