package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchable;
import algorithms.search.MazeState;
import algorithms.search.SearchableMaze;
import Model.IModel;
import Model.MovementDirection;
import algorithms.search.Solution;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public ISearchable generateMaze(int rows, int cols, String selectedValue) {

        ISearchable ans;
        try {
            ans = model.generateMaze(rows, cols, selectedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ans;
    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction;
        switch (keyEvent.getCode()){
            case UP -> direction = MovementDirection.UP;
            case DOWN -> direction = MovementDirection.DOWN;
            case LEFT -> direction = MovementDirection.LEFT;
            case RIGHT -> direction = MovementDirection.RIGHT;
            default -> {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public void drawMazeWalls(ISearchable maze, GraphicsContext graphicsContext, double cellHeight, double cellWidth, String imageFileNameWall, double canvasHeight, double canvasWidth) {
        Image img = checkExistingImage(imageFileNameWall);
        if(img!=null){
            try {
                model.drawMazeWalls(maze, img, graphicsContext, cellHeight, cellWidth, canvasHeight, canvasWidth);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void drawTmp(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int r, int c, String FileName,double h,double w) {
        Image img = checkExistingImage(FileName);
        if(img!=null) {
            try {
                model.drawTmp(img, graphicsContext, cellHeight, cellWidth, r, c, h, w);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Image checkExistingImage(String FileName){
        Image img = null;
        try {
            img = new Image(new FileInputStream(FileName));
        }
        catch (Exception e){
            System.out.println("there is no picture in the given folder");

        }
        return img;
    }
}
