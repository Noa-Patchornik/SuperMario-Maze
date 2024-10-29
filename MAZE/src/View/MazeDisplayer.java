package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.search.ISearchable;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameWinner = new SimpleStringProperty();
    //for the position of the player
    private int playerRow = 0;
    private int playerCol = 0;
    //get maze from one of the algorithms in part 1 of the project
    private ISearchable maze;
    public MyViewModel myViewModle = new MyViewModel(new MyModel());
    GraphicsContext graphicsContext = getGraphicsContext2D();
    private Solution solution;
    double cellHeight=0;
    double cellWidth=0;
    double canvasHeight=0;
    double canvasWidth=0;
    private int PositionR;
    private int PositionC;


    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public String getImageFileNameWinner() {
        return imageFileNameWinner.get();
    }


    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNameWinner(String imageFileNameWinner) {
        this.imageFileNameWinner.set(imageFileNameWinner);
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }


    public void drawMaze(ISearchable maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if(maze != null){
            //the size od the canvas
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            //the size of the maze
            int rows = ((SearchableMaze)maze).getOrigionMaze().getRows();
            int cols = ((SearchableMaze)maze).getOrigionMaze().getCols();
            //the size of each cell in the displayer
            this.cellHeight = canvasHeight / rows;
            this.cellWidth = canvasWidth / cols;

            //clear the canvas:
            this.graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            //draw the walls of the maze
            myViewModle.drawMazeWalls(this.maze,graphicsContext,cellHeight,cellWidth,getImageFileNameWall(),canvasHeight,canvasWidth);
            //draw the character of the maze
            //myViewModle.drawTmp(graphicsContext,cellHeight,cellWidth,((SearchableMaze)maze).getOrigionMaze().getGoalPosition().getRowIndex(),((SearchableMaze)maze).getOrigionMaze().getGoalPosition().getColumnIndex(),getImageFileNameGoal(),canvasHeight,canvasWidth);
            //myViewModle.drawTmp(graphicsContext,cellHeight,cellWidth,((SearchableMaze)maze).getOrigionMaze().getStartPosition().getRowIndex(),((SearchableMaze)maze).getOrigionMaze().getStartPosition().getColumnIndex(),getImageFileNameInit(),canvasHeight,canvasWidth);
            myViewModle.drawTmp(graphicsContext,cellHeight,cellWidth,this.PositionR,this.PositionC,getImageFileNamePlayer(),canvasHeight,canvasWidth);
        }
    }

    private String getImageFileNameInit() {
        return imageFileNamePlayer.get();
    }

    private String getImageFileNameGoal() {
        return imageFileNameWinner.get();
    }

//    private String getImageFileNameWall() {
//        return imageFileNameWall.get();
//    }
//    private String


    public void setMaze(ISearchable searchable) {
        this.maze = searchable;
        this.PositionR = ((SearchableMaze)maze).getOrigionMaze().getStartPosition().getRowIndex();
        this.PositionC = ((SearchableMaze)maze).getOrigionMaze().getStartPosition().getColumnIndex();

    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }
}
