package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchable;
import algorithms.search.Solution;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Observer;

public interface IModel {
    ISearchable generateMaze(int rows, int cols,String searchable);
    Maze getMaze();
    void updatePlayerLocation(MovementDirection direction);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);
    void solveMaze();
    Solution getSolution();
    public void drawMazeWalls(ISearchable maze, Image img, GraphicsContext graphicsContext, double cellHeight, double cellWidth, double canvasHeight, double canvasWidth);
    public void drawTmp(Image Playerimg, GraphicsContext graphicsContext, double cellHeight, double cellWidth, int r, int c, double h, double w);
}
