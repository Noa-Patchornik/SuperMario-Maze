package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private int [][] maze;
    private ISearchable searchable;
    private Maze myMaze;
    private int row_player =0;
    private int col_player =0;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSolveMaze = new SimpleStringProperty();
    StringProperty imageFileNameSolution = new SimpleStringProperty();


    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }

    public String getImageFileNameSolveMaze() {
        return imageFileNameSolveMaze.get();
    }

    public void setImageFileNameSolveMaze(String imageFileNameSolveMaze) {
        this.imageFileNameSolveMaze.set(imageFileNameSolveMaze);
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    /**
     * draw the maze, update the start position of the player
     * @param searchable
     */
    public void drawMaze(ISearchable searchable)
    {
        this.searchable = searchable;
        this.myMaze = ((SearchableMaze) searchable).getOrigionMaze();
        this.maze = this.myMaze.getMaze();
        this.row_player = this.myMaze.getStartPosition().getRowIndex();
        this.col_player = this.myMaze.getStartPosition().getColumnIndex();
        draw();
    }

    /**
     * the function that draw the walls player and the princess and the end of the maze
     */
    public void draw()
    {
        if( maze !=null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.length;
            int col = maze[0].length;
            double cellHeight = canvasHeight / row;
            double cellWidth = canvasWidth / col;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.RED);
            double w, h;
            //Draw Maze
            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no file....");
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    h = i * cellHeight;
                    w = j * cellWidth;
                    if (maze[i][j] == 1) // Wall
                    {
                        if (wallImage == null) {

                            graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                        } else {

                            graphicsContext.drawImage(wallImage, w, h, cellWidth, cellHeight);
                        }
                    }

                }
            }

            //draw the player image
            double h_player = getRow_player() * cellHeight;
            double w_player = getCol_player() * cellWidth;
            Image playerImage = null;
            try {
                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image player....");
            }
            graphicsContext.drawImage(playerImage, w_player, h_player, cellWidth, cellHeight);

            //draw the princess image
            int goalRow = this.searchable.getGoalState().getR();
            int goalCol = this.searchable.getGoalState().getC();
            double h_princes = goalRow * cellHeight;
            double w_princes = goalCol * cellWidth;
            Image princesImage = null;
            try {
                princesImage = new Image(new FileInputStream(getImageFileNameSolveMaze()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no Image for solving");
            }
            graphicsContext.drawImage(princesImage, w_princes, h_princes, cellWidth, cellHeight);

            graphicsContext.setStroke(Color.DARKSALMON); // צבע המסגרת
            graphicsContext.setLineWidth(10); // עובי המסגרת
            graphicsContext.strokeRect(0, 0, this.getWidth(), this.getHeight()); // ציור המסגרת מסביב למבוך
        }
    }

    /**
     * function that get called if the player moved, update the location of it and call the function that
     * draw the walls and player and princess
     * @param row
     * @param col
     */
    public void setPlayerPosition(int row, int col) {
        this.row_player = row;
        this.col_player = col;
        draw();

    }

    public void drawSolution(ISearchable searchable, Solution solution) {
        drawMaze(searchable);
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int row = maze.length;
        int col = maze[0].length;
        double cellHeight = canvasHeight / row;
        double cellWidth = canvasWidth / col;
        GraphicsContext graphicsContext = getGraphicsContext2D();
//        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        ArrayList<AState> solpath =  solution.getSolutionPath();
        Image solutionPathImae = null;
        try {
            solutionPathImae = new Image(new FileInputStream(getImageFileNameSolution()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image for solving");
        }

        for(int i=0; i<solpath.size(); i++){
            int r = ((MazeState) solpath.get(i)).getR();
            int c = ((MazeState) solpath.get(i)).getC();
            if((r==this.searchable.getInitState().getR() && c==this.searchable.getInitState().getC() )||
                    (r==this.searchable.getGoalState().getR() && c==this.searchable.getGoalState().getC())){
                continue;
            }
            double x = c*cellWidth;
            double y = r*cellHeight;
            graphicsContext.drawImage(solutionPathImae, x, y, cellWidth, cellHeight);
        }
    }



}
